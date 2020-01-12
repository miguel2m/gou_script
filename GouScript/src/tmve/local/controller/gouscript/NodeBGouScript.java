/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller.gouscript;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import tmve.local.controller.readcsv.ReadAdjnodeCsv;
import tmve.local.controller.readcsv.ReadIpPathCsv;
import tmve.local.controller.readcsv.ReadIprtCsv;
import tmve.local.controller.readcsv.ReadSctplnkCsv;
import tmve.local.main.Main;
import tmve.local.model.AdjNode;
import tmve.local.model.IpPath;
import tmve.local.model.Iprt;
import tmve.local.model.Node;
import tmve.local.model.Sctplnk;
import tmve.local.model.exception.GouScriptException;
import tmve.local.model.script.NodeGouScriptDAO;

/**
 *
 * @author P05144
 */
public class NodeBGouScript {
    
    /**
     * METODO PARA CREAR GOU SCRIPT (NODEB INTEGRATE)
     * @param node_name
     * @param _vrf
     * @param _rnc
     * @return GOU SCRIPT (NODEB INTEGRATE) 
     */
    public static String createNodeBGouScript(Node node_name, String _vrf,String _rnc){
        String result ="";
        try{
            boolean flag = false; //LA RED DE LA RNC NO SE ENCUENTRA EN EL IPRT DEL NODOB
            //Se consulta en la tabla ADJNODE el ANI del NodeB filtrado por Nombre del nodoB (U_MORON)
            List<AdjNode> adjNodes = ReadAdjnodeCsv.getAdjNode(_rnc,node_name.getNodeb_name());
            if(CollectionUtils.isEmpty(adjNodes )){
                //System.err.println("ADJNODE: NODEB "+node_name.getNodeb_name()+" NO SE ENCUENTRA EN ADJNODES \n");
                throw new GouScriptException("403","There is not NODE B "+node_name.getNodeb_name()+" into "+_rnc);
                //System.exit(0);
            }
            // Se consulta en la tabla de IPPATH el ippath del NodeB filtrado por ANI del nodo (PARA OBTENER EL IPADDR DEL NODO EN LA RNC)
            List<IpPath> ipPathNodesRNC = ReadIpPathCsv.getIpPathNode(_rnc,adjNodes.get(0).getAni());
            if(CollectionUtils.isEmpty(ipPathNodesRNC )){
                /*System.err.println("IPPATH: EL ANI "+adjNodes.get(0).getAni()+
                                   " NO SE ENCUENTRA EN LA RNC "+_rnc+" \n");*/
                 //System.exit(0);*
            }
             //Se consulta la tabla SCTCPLNK para consultar el   sctplnk del NodeB filtador por Nombre del NODEB      
            List<Sctplnk> sctplnks = ReadSctplnkCsv.getNodeBSctplnkNodeIntegrate(
                    node_name.getNodeb_name(),
                    ipPathNodesRNC.get(0).getPEERIPADDR());
             if(CollectionUtils.isEmpty(sctplnks  )){
                /*System.err.println("SCTPLNK: EL NODEB ID "+node_name.getNodeb_name()+
                                   " NO SE ENCUENTRA en la tabla SCTPLNK "+_rnc+"\n");*/
                throw new GouScriptException("402"," There is not NODE B "+node_name.getNodeb_name()+" load into DATA BASE");
                 //System.exit(0);
            }
            // sctplnks .forEach(System.out::println);
            //Se consulta en la tabla IPRT el nexthop de la RED del Node B
            List<Iprt> ipRtNodes = ReadIprtCsv.getIprtNodeNexthop(
                    node_name.getNodeb_name(),
                    ipPathNodesRNC.get(0).getIPADDR()
            );
            if(CollectionUtils.isEmpty(ipRtNodes  )){
               /*System.err.println("IPRT: EL NODEB "+node_name.getNodeb_name()+
                                   " CON NETWORK "+ ipPathNodesRNC.get(0).getIPADDR()+
                                    " NO SE ENCUENTRA en la tabla IPRT \n");
                */
                List<Iprt> ipRtNodesTemp = ReadIprtCsv.getIprtNode(
                    node_name.getNodeb_name()
                );
                ipRtNodesTemp.get(0).setDstip(ipPathNodesRNC.get(0).getIPADDR());
                ipRtNodesTemp.get(0).setRtIdx((short) (ReadIprtCsv.getIprtNodeBRtidx(
                         node_name.getNodeb_name())+1));
                ipRtNodes.add(ipRtNodesTemp.get(0));
                //ipRtNodes.set(0, e);
                flag = true;
                
                 //System.exit(0);
            }

            NodeGouScriptDAO nodeGouScriptDAO = new NodeGouScriptDAO();

            nodeGouScriptDAO.setIprt(ipRtNodes.get(0));
            nodeGouScriptDAO.setSctplnk(sctplnks);
            // Se consulta en la tabla de IPPATH el ippath del NodeB filtrado por el nombre del nodo (PARA OBTENER EL SN DEL NODOB)
            List<IpPath> ipPathNodeBs = ReadIpPathCsv.getIpPathNodeName(
                    node_name.getNodeb_name(),
                    ipPathNodesRNC.get(0).getPEERIPADDR());
            if(CollectionUtils.isEmpty(ipPathNodeBs )){
                System.err.println("IPPATH: EL NODEB "+node_name.getNodeb_name()+
                                   " NO SE ENCUENTRA IPPATH \n");
                 ///System.exit(0);
            }
            nodeGouScriptDAO.setIppath(ipPathNodeBs);


                //Se CREA LOS SCRIPT DE LIST
                result += nodeGouScriptDAO.lstCommand();
                //Se coloca en el DSTIP LA VRFIP
                 nodeGouScriptDAO.getIprt().setDstip(_vrf);

                 //SE GUARDA EL VALOR ACTUAL DE LA RTIDX
                 short tempRtIdx = nodeGouScriptDAO.getIprt().getRtIdx();
                 //SE COLOCA EL RTDIX MAYOR +1 de los RTDIX del NODEB de la IPRT (TABLA DE ENRUTAMIENTO)
                 nodeGouScriptDAO.getIprt().setRtIdx((short) (ReadIprtCsv.getIprtNodeBRtidx(
                         node_name.getNodeb_name())+1));
                 //SE CREA EL SCRIPT DE ADD IPRT
                result += nodeGouScriptDAO.addIprt();

              //EN EL COMANDO MOD SCTPLINK  SE COLOCA LA LA VRF EN EL PARAMETRO PEERIP  
            Iterator<Sctplnk> it = nodeGouScriptDAO.getSctplnk().iterator();
            while (it.hasNext()){
                Sctplnk temp = it.next();
                temp.setPeerIp(_vrf);
            }
                //SE CREA EL COMANDO MOD SCTPLNK
                result += nodeGouScriptDAO.modSctplnk();
            //EN EL COMANDO MOD IPPATH  SE COLOCA LA LA VRF EN EL PARAMETRO PEERIP     
            Iterator<IpPath> it2 = nodeGouScriptDAO.getIppath().iterator();
            while (it2.hasNext()){
                IpPath temp = it2.next();
                temp.setPeerIp(_vrf); 
            }
                //SE CREA EL COAMNDO MOD IPRT
                result += nodeGouScriptDAO.addIpath();
                // SE COLOCA EL RTIDX ACTUAL DEL NODOB
                nodeGouScriptDAO.getIprt().setRtIdx(tempRtIdx);
                //SE CREA EL COMANDO RMV IPRT 
                result += nodeGouScriptDAO.rmvIprt();
                //Se CREA LOS SCRIPT DE LIST
                result += nodeGouScriptDAO.lstCommand();
                /*SE CREA EL COMANDO PING*/
                //PARA CREA EL COAMNDO PING SE NECESITA LA IPPATH Y EL IPRT
                nodeGouScriptDAO.setIppath( ipPathNodesRNC); //IPPATH de la RNC con el NodeB
                nodeGouScriptDAO.getIppath().get(0).setSn(nodeGouScriptDAO.getIprt().getSn());//SE coloca la SN del NODO B
                nodeGouScriptDAO.getIprt().setDstip(_vrf); //IPRT de la RNC con el DSTIP del NodeB
                //SE CREA EL COAMNDO PING
                result+=  nodeGouScriptDAO.ping();
                
                if(flag)
                     throw new GouScriptException("401",
                             "LA NETWORK (DSTIP=VRF) "+ipPathNodesRNC.get(0).getIPADDR()+
                             " DE LA RNC "+_rnc+
                             " NO SE ENCUENTRA EN LA TABLA IPRT DEL NODE B "+node_name.getNodeb_name());
                Main.logger.info("CODE :200 NodeB {} INTEGRATE GOUSCRIPT  CREADO CON EXITO",node_name.getNodeb_name());
        /*}catch(IOException e){
            System.err.println("IOExceptio "+e.getMessage().toString());
        }catch(CsvConstraintViolationException e1){
            System.err.println("CsvConstraintViolationException "+e1.getMessage().toString());
        }catch(IllegalArgumentException e3){
            System.err.println("IllegalArgumentException "+e3.getMessage().toString());*/
       /* }catch (GouScriptException ex){
            System.out.println(ex.getMessage());
        }catch (IOException e){
            System.out.println(e);*/
        }catch (GouScriptException ex){
            //System.out.println(ex.getMessage());
            Main.logger.error("GouScriptException {} ", ex.getMessage());
        }catch (IOException e){
            //System.out.println(e);
            Main.logger.error("IOException {} ", e.getMessage().toString());
        }finally{
            return result;
        }
    }
    /**
     * METODO PARA CREAR GOU SCRIPT ( NODEB ROLLBACK)
     * @param _rnc
     * @param node_name
     * @return GOU SCRIPT (NODEB ROLLBACK) 
     */
    public static String createNodeBGouScriptRollback(String _rnc,Node node_name){
        String result ="";
        try{
            boolean flag = false;
            //Se consulta en la tabla ADJNODE el ANI del NodeB filtrado por Nombre del nodoB (U_MORON)
            List<AdjNode> adjNodes = ReadAdjnodeCsv.getAdjNode(_rnc,node_name.getNodeb_name());
            if(CollectionUtils.isEmpty(adjNodes )){
                //System.err.println("ADJNODE: NODEB "+node_name.getNodeb_name()+" NO SE ENCUENTRA EN ADJNODES \n");
                throw new GouScriptException("403","There is not NODE B "+node_name.getNodeb_name()+" load into "+_rnc);
                 //System.exit(0);
            }
            // Se consulta en la tabla de IPPATH el ippath del NodeB filtrado por ANI del nodo 
            //(PARA OBTENER EL IPADDR DEL NODO EN LA RNC)
            List<IpPath> ipPathNodesRNC = ReadIpPathCsv.getIpPathNode(_rnc,adjNodes.get(0).getAni());
            if(CollectionUtils.isEmpty(ipPathNodesRNC )){
                /*System.err.println("IPPATH: EL ANI "+adjNodes.get(0).getAni()+
                                   " NO SE ENCUENTRA EN LA RNC "+_rnc+" \n");*/
                 //System.exit(0);
            }
            //Se consulta la tabla SCTCPLNK para consultar el   sctplnk del NodeB filtador por Nombre del NODEB      
            List<Sctplnk> sctplnks = ReadSctplnkCsv.getNodeBSctplnkNodeIntegrate(
                    node_name.getNodeb_name(),
                    ipPathNodesRNC.get(0).getPEERIPADDR()
            );
             if(CollectionUtils.isEmpty(sctplnks  )){
               /* System.err.println("SCTPLNK: EL NODEB ID "+node_name.getNodeb_name()+
                                   " NO SE ENCUENTRA en la tabla SCTPLNK "+_rnc+"\n");*/
                throw new GouScriptException("402"," There is not NODE B "+node_name.getNodeb_name()+" load into DATA BASE");
                 //System.exit(0);
            }
            //  sctplnks .forEach(System.out::println);
            //Se consulta en la tabla IPRT el nexthop de la RED del Node B
            List<Iprt> ipRtNodes = ReadIprtCsv.getIprtNodeNexthop(
                    node_name.getNodeb_name(),
                    ipPathNodesRNC.get(0).getIPADDR()
            );
            if(CollectionUtils.isEmpty(ipRtNodes  )){
                /*System.err.println("IPRT: EL NODEB "+node_name.getNodeb_name()+
                                   " CON NETWORK "+ ipPathNodesRNC.get(0).getIPADDR()+
                                    " NO SE ENCUENTRA en la tabla IPRT \n");
                */
                List<Iprt> ipRtNodesTemp = ReadIprtCsv.getIprtNode(
                    node_name.getNodeb_name()
                );
                ipRtNodesTemp.get(0).setDstip(ipPathNodesRNC.get(0).getIPADDR());
                ipRtNodesTemp.get(0).setRtIdx((short) (ReadIprtCsv.getIprtNodeBRtidx(
                         node_name.getNodeb_name())+1));
                ipRtNodes.add(ipRtNodesTemp.get(0));
                //ipRtNodes.set(0, e);
                flag = true;
                
                 //System.exit(0);
            } 
           //ipRtNodes.forEach(System.out::println);
            NodeGouScriptDAO nodeGouScriptDAO = new NodeGouScriptDAO();

            nodeGouScriptDAO.setIprt(ipRtNodes.get(0));
            nodeGouScriptDAO.setSctplnk(sctplnks);
            // Se consulta en la tabla de IPPATH el ippath del NodeB filtrado por el nombre del nodo (PARA OBTENER EL SN DEL NODOB)
            List<IpPath> ipPathNodeBs = ReadIpPathCsv.getIpPathNodeName(
                    node_name.getNodeb_name(),
                    ipPathNodesRNC.get(0).getPEERIPADDR());
            if(CollectionUtils.isEmpty(ipPathNodeBs )){
                System.err.println("IPPATH: EL NODEB "+node_name.getNodeb_name()+
                                   " NO SE ENCUENTRA IPPATH \n");
                 //System.exit(0);
            }
            nodeGouScriptDAO.setIppath(ipPathNodeBs);


                //Se
                result += nodeGouScriptDAO.lstCommand();


                result += nodeGouScriptDAO.addIprt();

                result += nodeGouScriptDAO.modSctplnk();

                result += nodeGouScriptDAO.addIpath();
                nodeGouScriptDAO.getIprt().setRtIdx((short) (ReadIprtCsv.getIprtNodeBRtidx(
                         node_name.getNodeb_name())+1));
                result += nodeGouScriptDAO.rmvIprt();
                result += nodeGouScriptDAO.lstCommand();
                nodeGouScriptDAO.setIppath( ipPathNodesRNC);
                nodeGouScriptDAO.getIppath().get(0).setSn(nodeGouScriptDAO.getIprt().getSn());

                result+=  nodeGouScriptDAO.ping();

            if(flag)
                throw new GouScriptException("401",
                        "LA NETWORK (DSTIP=VRF) "+ipPathNodesRNC.get(0).getIPADDR()+
                        " DE LA RNC "+_rnc+
                        " NO SE ENCUENTRA EN LA TABLA IPRT DEL NODE B "+node_name.getNodeb_name());
            Main.logger.info("CODE :200 NodeB  {}  ROLLBACK GOUSCRIPT  CREADO CON EXITO",node_name.getNodeb_name());
        /*}catch(IOException e){
            System.err.println("IOExceptio "+e.getMessage().toString());
        }catch(CsvConstraintViolationException e1){
            System.err.println("CsvConstraintViolationException "+e1.getMessage().toString());
        }catch(IllegalArgumentException e3){
            System.err.println("IllegalArgumentException "+e3.getMessage().toString());*/
        }catch (GouScriptException ex){
           // System.out.println(ex.getMessage());
            Main.logger.error("GouScriptException {} ", ex.getMessage().toString());
        }catch (IOException e){
            //System.out.println(e);
            Main.logger.error("IOException {} ", e.getMessage().toString());
        }finally{
            return result;
        }
    }
}
