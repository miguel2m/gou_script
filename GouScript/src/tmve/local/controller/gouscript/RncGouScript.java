/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller.gouscript;

import com.opencsv.exceptions.CsvConstraintViolationException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmve.local.controller.Validator;
import tmve.local.controller.readcsv.ReadAdjnodeCsv;
import tmve.local.controller.readcsv.ReadIpPathCsv;
import tmve.local.controller.readcsv.ReadIppmCsv;
import tmve.local.controller.readcsv.ReadIprtCsv;
import tmve.local.controller.readcsv.ReadNodeBCsv;
import tmve.local.controller.readcsv.ReadNodeBIpCsv;
import tmve.local.controller.readcsv.ReadSctplnkCsv;
import tmve.local.main.Main;
import tmve.local.model.AdjNode;
import tmve.local.model.IpPath;
import tmve.local.model.Ippm;
import tmve.local.model.Iprt;
import tmve.local.model.Node;
import tmve.local.model.NodeB;
import tmve.local.model.NodeBIp;
import tmve.local.model.Sctplnk;
import tmve.local.model.exception.GouScriptException;
import tmve.local.model.script.RncGouScriptDAO;

/**
 *CLASE PARA CREAR LOS GOU SCRIPT DEL LADO DE LA RNC CON SU ROLLBACK
 * @author Miguelangel
 */
public class RncGouScript {

    /**
     * METODO PARA CREAR GOU SCRIPT (RNC INTEGRATE)
     * @param node_name
     * @param _rnc
     * @param _srn
     * @param _sn
     * @param _port
     * @param _vrf
     * @return GOU SCRIPT (RNC INTEGRATE)
     * @throws IOException
     * @throws CsvConstraintViolationException
     * @throws Exception 
     */
    public static String createRNCGouScript(Node node_name, 
            String _rnc, 
            short _srn, 
            short _sn, 
            short _port, 
            String _vrf){
        String salidaGouScript ="";
        try{
            //System.out.println(" 1 PASO! ");
            //Se consulta en la tabla ADJNODE el ANI del NodeB filtrado por Nombre del nodoB (U_MORON)
            List<AdjNode> adjNodes = ReadAdjnodeCsv.getAdjNode(_rnc,node_name.getNodeb_name());
            if(CollectionUtils.isEmpty(adjNodes )){
                //System.err.println("ADJNODE: NODEB "+node_name.getNodeb_name()+" NO SE ENCUENTRA EN ADJNODES \n");
                throw new GouScriptException("403"," RNC INTEGRATE There is not NODE B "+node_name.getNodeb_name()+" into "+_rnc);
                // System.exit(0);
            }
            //System.out.println("ADJNODES 2 PASO! ");
            //Se consulta en la tabla IPPM si el nodeB ID tiene IPPM
            List<Ippm> ippmNodes = ReadIppmCsv.getIppmNode(_rnc,adjNodes.get(0).getAni());
            //SE ENCUENTRA VALIDADO MAS ABAJO
            // System.out.println(" 3 PASO! ");
            // Se consulta en la tabla de IPPATH el ippath del NodeB filtrado por ANI del nodo
            List<IpPath> ipPathNodes = ReadIpPathCsv.getIpPathNode(_rnc,adjNodes.get(0).getAni());
            if(CollectionUtils.isEmpty(ipPathNodes )){
                /*System.err.println("IPPATH: EL ANI "+adjNodes.get(0).getAni()+
                                   " NO SE ENCUENTRA EN LA RNC "+_rnc+" \n");*/
                 //System.exit(0);
            }
            // System.out.println(" IPPATH 4 PASO! ");

            //Se consulta la tabla NODEB, el NODEB filtrado por nombre del nodeB, para buscar su ID-NODEB        
            List<NodeB> nodeB = ReadNodeBCsv.getNodeBId(_rnc,node_name.getNodeb_name());
            if(CollectionUtils.isEmpty(nodeB )){
                System.err.println("NODEB: EL NODEB "+node_name.getNodeb_name()+
                                   " NO SE ENCUENTRA EN LA TABLA NODEB \n");
                 //System.exit(0);
            }

            // System.out.println("NODEB 5 PASO! ");
            //Se consulta la tabla NodeBIP para conocer la red de Host del NODEB
            List<NodeBIp> nodeBIp = ReadNodeBIpCsv.getNodeBDstip(_rnc,nodeB.get(0).getNodebid());
            if(CollectionUtils.isEmpty(nodeBIp )){
                System.err.println("NODEBIP: EL NODEB ID "+nodeB.get(0).getNodebid()+
                                   " NO SE ENCUENTRA NODEBIP \n");
                 //System.exit(0);
            }
            // System.out.println("NODEB IP 6 PASO! ");
            // nodeBIp.forEach(System.out::println);
             if (nodeBIp.get(0).getNBTRANTP().equals("ATMTRANS_IP"))
                 throw new GouScriptException("400"," RNC INTEGRATE NODE B "+node_name.getNodeb_name()+" (ATM IPTRANS)");
            //Se calcula la red Network del NodeB
            String nodeNetwork =Validator.getNetwork(nodeBIp.get(0).getNBIPOAMIP(), nodeBIp.get(0).getNBIPOAMMASK());
                //System.out.println("NODE B NETWORK "+nodeNetwork+" \n");

            //    System.out.println("NODE B NETWORK 6.1 PASO! ");
            ///Se consulta la tabla SCTCPLNK para consultar el   sctplnk del NodeB filtador por NodeBID            
            List<Sctplnk> sctplnks = ReadSctplnkCsv.getNodeBSctplnk(_rnc,nodeB.get(0).getNodebid());
            if(CollectionUtils.isEmpty(sctplnks  )){
                /*System.err.println("SCTPLNK: EL NODEB ID "+nodeB.get(0).getNodebid()+
                                   " NO SE ENCUENTRA en la RNC "+_rnc+"\n");*/
                 //System.exit(0);
            }
            // System.out.println("SCTPLNK 7 PASO! ");
            //Se consulta en la tabla IPRT el nexthop de la RED del Node B
            List<Iprt> ipRtNodes = ReadIprtCsv.getIprtNodeNexthop(_rnc,nodeNetwork);
             if(CollectionUtils.isEmpty(ipRtNodes )){ //INTEGRACION DE UN NUEVO NODO AQUIII
                System.err.println("IPRT : LA RED "+nodeNetwork+
                                   " NO SE ENCUENTRA en la RNC "+_rnc+"\n");
                 //System.exit(0);
            }
            //  System.out.println("IPRT 8 PASO! ");
            //Se consulta en la tabla IPRT el nexthop de SN SRN y Puerto de la RNC
            List<Iprt> ipRtNodesToMove = ReadIprtCsv.getIprtPortNexthop(_rnc, _sn, _srn, _port);
            if(CollectionUtils.isEmpty(ipRtNodesToMove)){
                System.err.println("IPRT ROLLBACK: EL PUERTO "+_port+
                                   " del la RNC "+_rnc+" no posee NEXTHOOP\n");
                 //System.exit(0);
            }
             //System.out.println("IPRT TO MOVEs 9 PASO! ");
             salidaGouScript ="";
            //Para crear el script se debe crear una nueva istancia de RncGouScriptDAO 
            RncGouScriptDAO _RncGouScriptDAO = new RncGouScriptDAO();
            //Se verifica el nodo posee IPPM
            if(!CollectionUtils.isEmpty(ippmNodes)) 
                _RncGouScriptDAO .setIppmList(ippmNodes);

            _RncGouScriptDAO.setIprt(ipRtNodes.get(0));
            _RncGouScriptDAO.setSctplnk(sctplnks);
            _RncGouScriptDAO.setIppath(ipPathNodes);
             //Si el nodoB posee IPPM se procede a realizar el DEA IPPM y Posteriormente EL ACT IPPM
            if(!CollectionUtils.isEmpty(_RncGouScriptDAO .getIppmList()))
             //SE GENERAN LOS SCRIPT CORRESPONDIENTE A DEA IPPM ,RMV IPRT y RMV IPPATH
            salidaGouScript += _RncGouScriptDAO.deaIppm()+
                                _RncGouScriptDAO.rmvIprt()
                                +_RncGouScriptDAO.rmvIppath();
            else salidaGouScript += _RncGouScriptDAO.rmvIprt() //SE GENERAN LOS SCRIPT CORRESPONDIENTE A RMV IPRT y RMV IPPATH
                                    +_RncGouScriptDAO.rmvIppath();

            ipRtNodes.get(0).setNexthop(ipRtNodesToMove.get(0).getNexthop());
            ipRtNodes.get(0).setSrn(ipRtNodesToMove.get(0).getSrn());
            ipRtNodes.get(0).setSn(ipRtNodesToMove.get(0).getSn());
            //SE GENERA LOS SCRIPT CORRESPONDIENTE A ADD IPRT (CON EL NEXTHOP DEL PUERTO DE LA RNC)
            salidaGouScript += _RncGouScriptDAO.addIprt();
            Iterator<Sctplnk> it = sctplnks.iterator();
            while (it.hasNext()){
                Sctplnk temp = it.next();
                temp.setLOCIP1(_vrf);
            }
            //SE GENERA LOS SCRIPT PARA EL MOD SCTPLNK (SE COLOCA EN EL LOCIP1 la IP del VRF)
            salidaGouScript += _RncGouScriptDAO.modSctplnk();
            Iterator<IpPath> it2 = ipPathNodes.iterator();
            while (it2.hasNext()){
                IpPath temp = it2.next();
                temp.setIPADDR(_vrf); 
            }
            //SE GENERA LOS SCRIPT PARA EL ADD IPPATH (SE COLOCA EN EL IPADRR la IP del VRF)
            salidaGouScript +=  _RncGouScriptDAO.addIpath();
            //Si el nodoB posee IPPM se procede a realizar el DEA y Posteriormente EL ACT
            if(!CollectionUtils.isEmpty(_RncGouScriptDAO.getIppmList()))
             //SE GENERAN LOS SCRIPT CORRESPONDIENTE A ACT IPPM
            salidaGouScript += _RncGouScriptDAO.actIppm();
            
            Main.logger.info("CODE :200 RNC {} INTEGRATE GOUSCRIPT  CREADO CON EXITO",_rnc);
       /* }catch (NodebNotFoundException nodeb){
            System.out.println(nodeb.getMessage().toString());
        }catch(AtmTransException atm){
            System.out.println("ATM TANSIP "+atm.getMessage());
        
        }catch(IOException e){
            System.err.println("IOExceptio "+e.getMessage().toString());
        }catch(CsvConstraintViolationException e1){
            System.err.println("CsvConstraintViolationException "+e1.getMessage().toString());
        }catch(IllegalArgumentException e3){
            System.err.println("IllegalArgumentException "+e3.getMessage().toString());*/
        
        }catch (GouScriptException ex){
            Main.logger.error("GouScriptException {} ", ex.getMessage().toString());
            //System.out.println(ex.getMessage());
        }catch (IOException e){
            System.out.println(e);
            Main.logger.error("IOException {} ", e.getMessage().toString());
        }finally{
            return salidaGouScript;
        }
    }
    /**
     * METODO PARA CREAR GOU SCRIPT ( RNC ROLLBACK)
     * @param node_name
     * @param _rnc
     * @param _srn
     * @param _sn
     * @param _port
     * @return GOU SCRIPT (ROLLBACK)
     * @throws IOException
     * @throws CsvConstraintViolationException
     * @throws Exception 
     */
    public static String createRNCRollbackGouScript(Node node_name, 
            String _rnc, 
            short _srn, 
            short _sn, 
            short _port) {
        String salidaGouScript ="";
        try{
            //Se consulta en la tabla ADJNODE el ANI del NodeB filtrado por Nombre del nodoB (U_MORON)
            List<AdjNode> adjNodes = ReadAdjnodeCsv.getAdjNode(_rnc,node_name.getNodeb_name());
            if(CollectionUtils.isEmpty(adjNodes )){
               // System.err.println("ADJNODE: NODEB "+node_name.getNodeb_name()+" NO SE ENCUENTRA EN ADJNODES \n");
                 throw new GouScriptException("403"," RNC INTEGRATE There is not NODE B "+node_name.getNodeb_name()+" into "+_rnc);
                //System.exit(0);
            }

            //Se consulta en la tabla IPPM si el nodeB ID tiene IPPM
            List<Ippm> ippmNodes = ReadIppmCsv.getIppmNode(_rnc,adjNodes.get(0).getAni());
            //SE ENCUENTRA VALIDADO MAS ABAJO

            // Se consulta en la tabla de IPPATH el ippath del NodeB filtrado por ANI del nodo
            List<IpPath> ipPathNodes = ReadIpPathCsv.getIpPathNode(_rnc,adjNodes.get(0).getAni());
            if(CollectionUtils.isEmpty(ipPathNodes )){
                /*System.err.println("IPPATH: EL ANI "+adjNodes.get(0).getAni()+
                                   " NO SE ENCUENTRA EN LA RNC "+_rnc+" \n");*/
                 //System.exit(0);
            }
            //Se consulta la tabla NODEB, el NODEB filtrado por nombre del nodeB, para buscar su ID-NODEB        
            List<NodeB> nodeB = ReadNodeBCsv.getNodeBId(_rnc,node_name.getNodeb_name());
            if(CollectionUtils.isEmpty(nodeB )){
                System.err.println("NODEB: EL NODEB "+node_name.getNodeb_name()+
                                   " NO SE ENCUENTRA EN LA TABLA NODEB \n");
                 //System.exit(0);
            }
            //Se consulta la tabla NodeBIP para conocer la red de Host del NODEB
            List<NodeBIp> nodeBIp = ReadNodeBIpCsv.getNodeBDstip(_rnc,nodeB.get(0).getNodebid());
            if(CollectionUtils.isEmpty(nodeBIp )){
                System.err.println("NODEBIP: EL NODEB ID "+nodeB.get(0).getNodebid()+
                                   " NO SE ENCUENTRA NODEBIP \n");
                 //System.exit(0);
            }
            //nodeBIp.forEach(System.out::println);
            if (nodeBIp.get(0).getNBTRANTP().equals("ATMTRANS_IP"))
                 throw new GouScriptException("400"," RNC ROLLBACK NODE B "+node_name.getNodeb_name()+" (ATM IPTRANS)");
            //Se calcula la red Network del NodeB
            String nodeNetwork =Validator.getNetwork(nodeBIp.get(0).getNBIPOAMIP(), nodeBIp.get(0).getNBIPOAMMASK());
             //   System.out.println("NODE B RNC NETWORK "+nodeNetwork+" \n");
            //Se consulta la tabla SCTCPLNK para consultar el   sctplnk del NodeB filtador por NodeBID            
            List<Sctplnk> sctplnks = ReadSctplnkCsv.getNodeBSctplnk(_rnc,nodeB.get(0).getNodebid());
            if(CollectionUtils.isEmpty(sctplnks  )){
                System.err.println("SCTPLNK: EL NODEB ID "+nodeB.get(0).getNodebid()+
                                   " NO SE ENCUENTRA en la RNC "+_rnc+"\n");
                // System.exit(0);
            }
            //Se consulta en la tabla IPRT el nexthop de la RED del Node B
            List<Iprt> ipRtNodes = ReadIprtCsv.getIprtNodeNexthop(_rnc,nodeNetwork);
             if(CollectionUtils.isEmpty(ipRtNodes )){ //INTEGRACION DE UN NUEVO NODO
                System.err.println("IPRT : LA RED "+nodeNetwork+
                                   " NO SE ENCUENTRA en la RNC "+_rnc+"\n");
                // System.exit(0);
            }
            //Se consulta en la tabla IPRT el nexthop de SN SRN y Puerto de la RNC
            List<Iprt> ipRtNodesToRollback = ReadIprtCsv.getIprtPortNexthop(_rnc, _sn, _srn, _port);
            if(CollectionUtils.isEmpty(ipRtNodesToRollback)){
                System.err.println("IPRT ROLLBACK: EL PUERTO "+_port+
                                   " del la RNC "+_rnc+" no posee NEXTHOOP\n");
                // System.exit(0);
            }
        
            //Para crear el script se debe crear una nueva istancia de RncGouScriptDAO 
            RncGouScriptDAO _RncGouRollbackScriptDAO = new RncGouScriptDAO();
            //Se verifica el nodo posee IPPM

            if(!CollectionUtils.isEmpty(ippmNodes )) _RncGouRollbackScriptDAO .setIppmList(ippmNodes);



            //Se asigna el DSTIP del nodeB al puerto de la RNC
            ipRtNodesToRollback.get(0).setDstip(nodeNetwork);
            _RncGouRollbackScriptDAO .setIprt(ipRtNodesToRollback.get(0));
            _RncGouRollbackScriptDAO .setSctplnk(sctplnks);
            _RncGouRollbackScriptDAO .setIppath(ipPathNodes);
            //Si el nodoB posee IPPM se procede a realizar el DEA y Posteriormente EL ACT
            if(!CollectionUtils.isEmpty(_RncGouRollbackScriptDAO .getIppmList()))
             //SE GENERAN LOS SCRIPT CORRESPONDIENTE A RMV IPRT y RMV IPPATH
            salidaGouScript += _RncGouRollbackScriptDAO.deaIppm()+
                                _RncGouRollbackScriptDAO.rmvIprt()
                                +_RncGouRollbackScriptDAO.rmvIppath();
            else salidaGouScript += _RncGouRollbackScriptDAO.rmvIprt()
                                    +_RncGouRollbackScriptDAO.rmvIppath();

            _RncGouRollbackScriptDAO.setIprt(ipRtNodes.get(0));
            _RncGouRollbackScriptDAO.setSctplnk(sctplnks);
            _RncGouRollbackScriptDAO.setIppath(ipPathNodes);
            //SE GENERA LOS SCRIPT CORRESPONDIENTE A ADD IPRT (CON EL NEXTHOP DEL PUERTO DE LA RNC)
            salidaGouScript += _RncGouRollbackScriptDAO.addIprt();
            //SE GENERA LOS SCRIPT PARA EL MOD SCTPLNK (SE COLOCA EL ESTADO ANTEIOR)
            salidaGouScript += _RncGouRollbackScriptDAO.modSctplnk();
            //SE GENERA LOS SCRIPT PARA EL ADD IPPATH (SE COLOCA EL ESTADO ANTEIOR)
            salidaGouScript +=  _RncGouRollbackScriptDAO.addIpath();

            //Si el nodoB posee IPPM se procede a realizar el DEA y Posteriormente EL ACT
            if(!CollectionUtils.isEmpty(_RncGouRollbackScriptDAO .getIppmList()))
             //SE GENERAN LOS SCRIPT CORRESPONDIENTE A ACT IPPM
            salidaGouScript += _RncGouRollbackScriptDAO.actIppm();
            
            
            Main.logger.info("CODE :200 RNC {}  ROLLBACK GOUSCRIPT  CREADO CON EXITO",_rnc);
        /*}catch (NodebNotFoundException nodeb){
            System.out.println(nodeb.getMessage().toString());
        
        }catch(AtmTransException atm){
            System.out.println("ATM TANSIP "+atm.getMessage());
        }catch(IOException e){
            System.err.println("IOExceptio "+e.getMessage().toString());
        }catch(CsvConstraintViolationException e1){
            System.err.println("CsvConstraintViolationException "+e1.getMessage().toString());
        }catch(IllegalArgumentException e3){
            System.err.println("IllegalArgumentException "+e3.getMessage().toString());*/
        }catch (GouScriptException ex){
            System.out.println(ex.getMessage());
            Main.logger.error("GouScriptException {} ", ex.getMessage().toString());
        }catch (IOException e){
            System.out.println(e);
            Main.logger.error("IOException {} ", e.getMessage().toString());
        }finally{
            return salidaGouScript;
        }
    }
}
