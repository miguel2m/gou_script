/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.model.script;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tmve.local.model.IpPath;
import tmve.local.model.Ippm;
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
    private List <Ippm> ippmList;

    public List<Ippm> getIppmList() {
        return ippmList;
    }

    public void setIppmList(List<Ippm> ippmList) {
        this.ippmList = ippmList;
    }

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
     * SI EL NODEB POSEE IPPM SE AGREGA AL SCRIPT (DEA) 
     * @return SCRIPT de DEA para IPPM para la RNC INTEGRATE O ROLLBACK
     */
    public  List<String> deaIppm(){
        List<String> script = new ArrayList<>();;
        Iterator<Ippm> it = ippmList.iterator();
        while (it.hasNext()){
            Ippm temp = it.next();
            script.add( "DEA IPPM: ANI="+temp.getAni()+
                          ",PATHID="+temp.getPathId() +";");
            
           
        }
        
        return script;
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
                ",FORCEEXECUTE=YES;";   
    }
    /**
     * SE ELIMINA EL IPPATH ACTUAL DEL NODOB
     * @return SCRIPT RMV IPPATH para la RNC INTEGRATE O ROLLBACK
     */
    public  List<String> rmvIppath(){
        List<String> script = new ArrayList<>();;
        Iterator<IpPath> it = ippaths.iterator();
        while (it.hasNext()){
            IpPath temp = it.next();
            script .add( "RMV IPPATH: ANI="+temp.getAni()+
                          ",PATHID="+temp.getPATHID()+";");
            
           
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
                ",PRIORITY=HIGH;";
    }
    /**
     * LOCIP1= IP VRF
     * @return MOD SCTPLNK Script para la RNC INTEGRATE O ROLLBACK 
     */
    public List<String> modSctplnk(){
        List<String> script = new ArrayList<>();
        Iterator<Sctplnk> it = sctplnks.iterator();
        while (it.hasNext()){
            Sctplnk temp = it.next();
            script .add( "MOD SCTPLNK:SRN="+temp.getSrn()+
                ",SN="+temp.getSn()+
                ",SCTPLNKN="+temp.getSCTPLNKN()+
                ",LOCIP1=\""+temp.getLOCIP1()+"\";");
           
        }
        return script;
    }
    /**
     * IPADDR= IP VRF
     * @return ADD IPPATH SCRIPT PARA LA RNC INTEGRATE O ROLLBACK
     */
    public List<String> addIpath(){
        List<String> script = new ArrayList<>();
        Iterator<IpPath> it = ippaths.iterator();
        while (it.hasNext()){
           IpPath temp = it.next();
           script.add("ADD IPPATH:ANI="+temp.getAni()+
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
               ",REMARK=\""+temp.getREMARK()+"\";");
           
        }
        return script;
    }
    
    /**
     * SI EL NODEB POSEE IPPM SE AGREGA AL SCRIPT (ACT) 
     * @return SCRIPT de ACT para IPPM para la RNC INTEGRATE O ROLLBACK
     */
    public List<String> actIppm(){
        List<String> script = new ArrayList<>();
        Iterator<Ippm> it = ippmList.iterator();
        while (it.hasNext()){
           Ippm temp = it.next();
           script.add("ACT IPPM:ANI="+temp.getAni()+
               ",PATHID="+temp.getPathId()+
               ",ISQOSPATH="+temp.getIsqosPath()+
               ",PMPRD="+temp.getPmPrd()+
               ",LOSTPKTDETECTSW="+temp.getLostPktDetectSw()+";");
        }
        return script;
    }
}
