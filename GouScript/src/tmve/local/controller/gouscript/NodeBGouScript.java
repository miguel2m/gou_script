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
import tmve.local.controller.Validator;
import tmve.local.controller.readcsv.ReadAdjnodeCsv;
import tmve.local.controller.readcsv.ReadIpPathCsv;
import tmve.local.controller.readcsv.ReadIprtCsv;
import tmve.local.controller.readcsv.ReadNodeBCsv;
import tmve.local.controller.readcsv.ReadNodeBIpCsv;
import tmve.local.controller.readcsv.ReadSctplnkCsv;
import tmve.local.model.AdjNode;
import tmve.local.model.IpPath;
import tmve.local.model.Iprt;
import tmve.local.model.Node;
import tmve.local.model.NodeB;
import tmve.local.model.NodeBIp;
import tmve.local.model.Sctplnk;
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
     * @return GOU SCRIPT (NODEB INTEGRATE)
     * @throws IOException
     * @throws CsvConstraintViolationException
     * @throws Exception 
     */
    public static String createNodeBGouScript(Node node_name, String _vrf,String _rnc)throws 
            IOException, 
            CsvConstraintViolationException, 
            Exception{
        //Se consulta en la tabla ADJNODE el ANI del NodeB filtrado por Nombre del nodoB (U_MORON)
        List<AdjNode> adjNodes = ReadAdjnodeCsv.getAdjNode(node_name.getNodeb_name());
        // Se consulta en la tabla de IPPATH el ippath del NodeB filtrado por ANI del nodo (PARA OBTENER EL IPADDR DEL NODO EN LA RNC)
        List<IpPath> ipPathNodesRNC = ReadIpPathCsv.getIpPathNode(_rnc,adjNodes.get(0).getAni());
        //Se consulta la tabla SCTCPLNK para consultar el   sctplnk del NodeB filtador por Nombre del NODEB      
        List<Sctplnk> sctplnks = ReadSctplnkCsv.getNodeBSctplnkNodeIntegrate(node_name.getNodeb_name());
        //Se consulta en la tabla IPRT el nexthop de la RED del Node B
        List<Iprt> ipRtNodes = ReadIprtCsv.getIprtNodeNexthop(
                node_name.getNodeb_name(),
                ipPathNodesRNC.get(0).getIPADDR()
        );
        
        NodeGouScriptDAO nodeGouScriptDAO = new NodeGouScriptDAO();
        
        nodeGouScriptDAO.setIprt(ipRtNodes.get(0));
        nodeGouScriptDAO.setSctplnk(sctplnks);
        // Se consulta en la tabla de IPPATH el ippath del NodeB filtrado por el nombre del nodo (PARA OBTENER EL SN DEL NODOB)
        List<IpPath> ipPathNodeBs = ReadIpPathCsv.getIpPathNodeName(node_name.getNodeb_name());
        nodeGouScriptDAO.setIppath(ipPathNodeBs);
        
        String result ="";
            
            result += nodeGouScriptDAO.lstCommand();
             nodeGouScriptDAO.getIprt().setDstip(_vrf);
             short tempRtIdx = nodeGouScriptDAO.getIprt().getRtIdx();
             nodeGouScriptDAO.getIprt().setRtIdx((short) (ReadIprtCsv.getIprtNodeBRtidx(
                     node_name.getNodeb_name())+1));
             
            result += nodeGouScriptDAO.addIprt();
            
            
        Iterator<Sctplnk> it = nodeGouScriptDAO.getSctplnk().iterator();
        while (it.hasNext()){
            Sctplnk temp = it.next();
            temp.setPeerIp(_vrf);
        }
            result += nodeGouScriptDAO.modSctplnk();
            
        Iterator<IpPath> it2 = nodeGouScriptDAO.getIppath().iterator();
        while (it2.hasNext()){
            IpPath temp = it2.next();
            temp.setPeerIp(_vrf); 
        }
            result += nodeGouScriptDAO.addIpath();
            nodeGouScriptDAO.getIprt().setRtIdx(tempRtIdx);
            result += nodeGouScriptDAO.rmvIprt();
            result += nodeGouScriptDAO.lstCommand();
            nodeGouScriptDAO.setIppath( ipPathNodesRNC);
            nodeGouScriptDAO.getIppath().get(0).setSn(nodeGouScriptDAO.getIprt().getSn());
            nodeGouScriptDAO.getIprt().setDstip(_vrf);
            result+=  nodeGouScriptDAO.ping();
            
        return result;
    }
    /**
     * METODO PARA CREAR GOU SCRIPT ( NODEB ROLLBACK)
     * @param node_name
     * @return GOU SCRIPT (NODEB ROLLBACK)
     * @throws IOException
     * @throws CsvConstraintViolationException
     * @throws Exception 
     */
    public static String createNodeBGouScriptRollback(String _rnc,Node node_name)throws 
            IOException, 
            CsvConstraintViolationException, 
            Exception{
        //Se consulta en la tabla ADJNODE el ANI del NodeB filtrado por Nombre del nodoB (U_MORON)
        List<AdjNode> adjNodes = ReadAdjnodeCsv.getAdjNode(node_name.getNodeb_name());
        // Se consulta en la tabla de IPPATH el ippath del NodeB filtrado por ANI del nodo (PARA OBTENER EL IPADDR DEL NODO EN LA RNC)
        List<IpPath> ipPathNodesRNC = ReadIpPathCsv.getIpPathNode(_rnc,adjNodes.get(0).getAni());
        
        //Se consulta la tabla SCTCPLNK para consultar el   sctplnk del NodeB filtador por Nombre del NODEB      
        List<Sctplnk> sctplnks = ReadSctplnkCsv.getNodeBSctplnkNodeIntegrate(node_name.getNodeb_name());
        //Se consulta en la tabla IPRT el nexthop de la RED del Node B
        List<Iprt> ipRtNodes = ReadIprtCsv.getIprtNodeNexthop(
                node_name.getNodeb_name(),
                ipPathNodesRNC.get(0).getIPADDR()
        );
       
        NodeGouScriptDAO nodeGouScriptDAO = new NodeGouScriptDAO();
        
        nodeGouScriptDAO.setIprt(ipRtNodes.get(0));
        nodeGouScriptDAO.setSctplnk(sctplnks);
        // Se consulta en la tabla de IPPATH el ippath del NodeB filtrado por el nombre del nodo (PARA OBTENER EL SN DEL NODOB)
        List<IpPath> ipPathNodeBs = ReadIpPathCsv.getIpPathNodeName(node_name.getNodeb_name());
        nodeGouScriptDAO.setIppath(ipPathNodeBs);
        
        String result ="";
            //
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
            
        return result;
    }
}
