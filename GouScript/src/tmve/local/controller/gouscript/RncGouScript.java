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
import tmve.local.model.script.RncGouScriptDAO;

/**
 *
 * @author Miguelangel
 */
public class RncGouScript {
    public static String createRNCGouScript(Node node_name, 
            String _rnc, 
            short _srn, 
            short _sn, 
            short _port, 
            String _vrf) throws 
            IOException, 
            CsvConstraintViolationException, 
            Exception{
        List<AdjNode> adjNodes = ReadAdjnodeCsv.getAdjNode(node_name.getNodeb_name());
            adjNodes.forEach(System.out::println);
      
            List<IpPath> ipPathNodes = ReadIpPathCsv.getIpPathNode(adjNodes.get(0).getAni());
   
            ipPathNodes.forEach(System.out::println);
                
        List<NodeB> nodeB = ReadNodeBCsv.getNodeBId(node_name.getNodeb_name());
            nodeB.forEach(System.out::println);
        
        List<NodeBIp> nodeBIp = ReadNodeBIpCsv.getNodeBDstip(nodeB.get(0).getNodebid());
            nodeBIp.forEach(System.out::println);
        String nodeNetwork =Validator.getNetwork(nodeBIp.get(0).getNBIPOAMIP(), nodeBIp.get(0).getNBIPOAMMASK());
            System.out.println("NODE B NETWORK "+nodeNetwork+" \n");
                 
       List<Sctplnk> sctplnks = ReadSctplnkCsv.getNodeBSctplnk(nodeB.get(0).getNodebid());
            sctplnks.forEach(System.out::println);
                 
        List<Iprt> ipRtNodes = ReadIprtCsv.getIprtNodeNexthop(_rnc,nodeNetwork,node_name.getNodeb_name());
            //ipRtNodes.forEach(System.out::println);
        
        List<Iprt> ipRtNodesToMove = ReadIprtCsv.getIprtPortNexthop(_rnc, _sn, _srn, _port);
           // ipRtNodesToMove.forEach(System.out::println);
        
        RncGouScriptDAO _RncGouScriptDAO = new RncGouScriptDAO();
        _RncGouScriptDAO.setIprt(ipRtNodes.get(0));
        _RncGouScriptDAO.setSctplnk(sctplnks);
        _RncGouScriptDAO.setIppath(ipPathNodes);
        System.out.println("GOU RNC INTEGRATE SCRIPT: \n"+
                _RncGouScriptDAO.rmvIprt()+
                _RncGouScriptDAO.rmvIppath()
        );
        ipRtNodes.get(0).setNexthop(ipRtNodesToMove.get(0).getNexthop());
        ipRtNodes.get(0).setSrn(ipRtNodesToMove.get(0).getSrn());
        ipRtNodes.get(0).setSn(ipRtNodesToMove.get(0).getSn());
        System.out.println(
                _RncGouScriptDAO.addIprt()
          
        );
        
        Iterator<Sctplnk> it = sctplnks.iterator();
        while (it.hasNext()){
            Sctplnk temp = it.next();
            temp.setLOCIP1(_vrf);
  
        }
        System.out.println(
                _RncGouScriptDAO.modSctplnk()
          
        );
        
        Iterator<IpPath> it2 = ipPathNodes.iterator();
        while (it2.hasNext()){
            IpPath temp = it2.next();
            temp.setIPADDR(_vrf);
            
           
        }
        
        System.out.println(
                _RncGouScriptDAO.addIpath()
          
        );
        
         return null;
    }
}
