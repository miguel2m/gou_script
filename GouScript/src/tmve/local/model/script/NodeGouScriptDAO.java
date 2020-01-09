/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.model.script;

import java.util.Iterator;
import java.util.List;
import tmve.local.model.IpPath;
import tmve.local.model.Iprt;
import tmve.local.model.Sctplnk;


/**
 *
 * @author Miguelangel
 */
public class NodeGouScriptDAO {
    private Iprt iprt;
    private List<IpPath> ippaths;
    private List<Sctplnk> sctplnks;

    public Iprt getIprt() {
        return iprt;
    }

    public void setIprt(Iprt iprt) {
        this.iprt = iprt;
    }

    public List<IpPath> getIppath() {
        return ippaths;
    }

    public void setIppath(List<IpPath> ippaths) {
        this.ippaths = ippaths;
    }

    public List<Sctplnk> getSctplnk() {
        return sctplnks;
    }

    public void setSctplnk(List<Sctplnk> sctplnks) {
        this.sctplnks = sctplnks;
    }
    /**
     * METODO QUE RETORNA LST DE LOS COAMNDO (IPRT,IPPATH,STCPLNK,OMCH)
     * @return LST DEL LADO DEL NODOB
     */
    public String lstCommand(){
        return "LST IPRT:;\n"+
               "LST IPPATH:;\n"+
               "LST SCTPLNK:;\n"+
               "LST OMCH:;\n";
    }
    /**
     *  METODO DEL COMANDO ADD IRT DE LADO DEL NODOB (DSTIP = IP VRF)
     * @return Add IPRT Script para NODOB INTEGRATE O ROLLBACK
     */
    public String addIprt(){
        return "ADD IPRT:RTIDX="+iprt.getRtIdx()+
                ",SN="+iprt.getSn()+
                ",SBT="+iprt.getSbt()+
                ",DSTIP=\""+iprt.getDstip()+"\""+
                ",DSTMASK=\""+iprt.getDstmask()+"\""+
                ",RTTYPE="+iprt.getRtType()+
                ",NEXTHOP=\""+iprt.getNexthop()+"\";";
    }
    
    
    /**
     * METODO DEL COMANDO  MOD SCTPLNK DEL LADO DEL NODOB PEERIP = IP VRF
     * @return MOD SCTPLNK Script para NODEB INTEGRATE O ROLLBACK 
     */
    public String modSctplnk(){
        String script = "";
        Iterator<Sctplnk> it = sctplnks.iterator();
        while (it.hasNext()){
            Sctplnk temp = it.next();
            script += "MOD SCTPLNK:SCTPNO="+temp.getSctpNo()+
                ",PEERIP=\""+temp.getPeerIp()+"\";\n";
           
        }
        return script;
    }
    
    /**
     * PEERIP = IP VRF
     * @return MOD IPPATH SCRIPT PARA EL NODEB INTEGRATE O ROLLBACK
     */
    public String addIpath(){
        String script = "";
        Iterator<IpPath> it = ippaths.iterator();
        while (it.hasNext()){
           IpPath temp = it.next();
           script +="MOD IPPATH:PATHID="+temp.getPATHID()+
                    ",SN="+temp.getSn()+
                    ",PEERIP=\""+temp.getPeerIp()+"\";";        
        }
        return script;
    }
    
}
