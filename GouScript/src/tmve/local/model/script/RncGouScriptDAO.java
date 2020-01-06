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
public class RncGouScriptDAO {
    private Iprt iprt;
    private List <IpPath> ippaths;
    private List <Sctplnk> sctplnks;

    

    public Iprt getIprt() {
        return iprt;
    }

    public void setIprt(Iprt iprt) {
        this.iprt = iprt;
    }

    public List<IpPath> getIppath() {
        return ippaths;
    }

    public void setIppath(List<IpPath> ippath) {
        this.ippaths = ippath;
    }

    public List<Sctplnk> getSctplnk() {
        return sctplnks;
    }

    public void setSctplnk(List<Sctplnk> sctplnk) {
        this.sctplnks = sctplnk;
    }

    /**
     * SE ELIMINA EL NEXTHOP DE LA RNC ACTUAL DEL SRN y SN
     * @return SCRIPT de RMV para IPRT para la RNC INTEGRATE O ROLLBACK
     */
    public String rmvIprt(){
         return "RMV IPRT:SRN="+iprt.getSrn()+
                ",SN="+iprt.getSn()+
                ",DSTIP=\""+iprt.getDstip()+"\""+
                ",DSTMASK=\""+iprt.getDstmask()+"\""+
                ",NEXTHOP=\""+iprt.getNexthop()+"\""+
                ",FORCEEXECUTE=YES;\n";   
    }
    /**
     * SE ELIMINA EL IPPATH ACTUAL DEL NODOB
     * @return SCRIPT RMV IPPATH para la RNC INTEGRATE O ROLLBACK
     */
    public  String rmvIppath(){
        String script = "";
        Iterator<IpPath> it = ippaths.iterator();
        while (it.hasNext()){
            IpPath temp = it.next();
            script += "RMV IPPATH: ANI="+temp.getAni()+
                          ",PATHID="+temp.getPATHID()+";\n";
            
           
        }
        
        return script;
    }
    /**
     * SE DEBE BUSCAR LA RED DSTIP EN LA IP DEL NODOB,
     * NORMALMENTE SE MODIFICA EL NEXTHOP,SRN,SN
     * @return Add IPRT Script para la RNC INTEGRATE O ROLLBACK
     */
    public String addIprt(){
        return "ADD IPRT:DSTIP=\""+iprt.getDstip()+"\""+
                ",DSTMASK=\""+iprt.getDstmask()+"\""+
                ",NEXTHOP=\""+iprt.getNexthop()+"\""+
                ",SRN="+iprt.getSrn()+
                ",SN="+iprt.getSn()+
                ",REMARK=\""+iprt.getRemark()+"\""+
                ",PRIORITY=HIGH;\n";
    }
    /**
     * LOCIP1= IP VRF
     * @return MOD SCTPLNK Script para la RNC INTEGRATE O ROLLBACK 
     */
    public String modSctplnk(){
        String script = "";
        Iterator<Sctplnk> it = sctplnks.iterator();
        while (it.hasNext()){
            Sctplnk temp = it.next();
            script += "MOD SCTPLNK:SRN="+temp.getSrn()+
                ",SN="+temp.getSn()+
                ",SCTPLNKN="+temp.getSCTPLNKN()+
                ",LOCIP1=\""+temp.getLOCIP1()+"\";\n";
           
        }
        return script;
    }
    /**
     * IPADDR= IP VRF
     * @return ADD IPPATH SCRIPT PARA LA RNC INTEGRATE O ROLLBACK
     */
    public String addIpath(){
        String script = "";
        Iterator<IpPath> it = ippaths.iterator();
        while (it.hasNext()){
           IpPath temp = it.next();
           script +="ADD IPPATH:ANI="+temp.getAni()+
               ",PATHID="+temp.getPATHID()+
               ",IPADDR=\""+temp.getIPADDR()+"\""+
               ",PEERIPADDR=\""+temp.getPEERIPADDR()+"\""+
               ",VLANFLAG="+temp.getVLANFLAG()+
               ",CARRYFLAG="+temp.getCARRYFLAG()+
               ",PATHT="+temp.getPATHT()+
               ",PEERMASK=\""+temp.getPEERMASK()+"\""+
               ",TXBW="+temp.getTXBW()+
               ",RXBW="+temp.getRXBW()+
               ",PATHCHK="+temp.getPATHCHK()+
               ",ITFT="+temp.getITFT()+
               ",TRANST="+temp.getTRANST()+
               ",TRMLOADTHINDEX="+temp.getTRMLOADTHINDEX()+
               ",REMARK=\""+temp.getREMARK()+"\";\n";
           
        }
        return script;
    }
}
