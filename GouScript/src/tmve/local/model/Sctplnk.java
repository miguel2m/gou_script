/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.processor.PreAssignmentProcessor;

/**
 *
 * @author Miguelangel
 */
public class Sctplnk {
    @CsvBindByName (column = "FILENAME")
    private String filename;
    @CsvBindByName (column = "LOCIP1")
    private String LOCIP1;
    @CsvBindByName (column = "LOCIP")
    private String LOCIP; //HACER MACTH DEL LADO DEL NODO CON EL PEERIP DE LA RNC
    @CsvBindByName (column = "SCTPLNKN")
    private String SCTPLNKN;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "SN")
    private short sn;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "SRN")
    private short srn;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "NODEBID")
    private int nodebid;
    @CsvBindByName (column = "PEERIP")
    private String peerIp;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "SCTPNO")
    private long sctpNo;

    public String getLOCIP() {
        return LOCIP;
    }

    public void setLOCIP(String LOCIP) {
        this.LOCIP = LOCIP;
    }

    public String getPeerIp() {
        return peerIp;
    }

    public void setPeerIp(String peerIp) {
        this.peerIp = peerIp;
    }

    public long getSctpNo() {
        return sctpNo;
    }

    public void setSctpNo(long sctpNo) {
        this.sctpNo = sctpNo;
    }
    
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLOCIP1() {
        return LOCIP1;
    }

    public void setLOCIP1(String LOCIP1) {
        this.LOCIP1 = LOCIP1;
    }

    public String getSCTPLNKN() {
        return SCTPLNKN;
    }

    public void setSCTPLNKN(String SCTPLNKN) {
        this.SCTPLNKN = SCTPLNKN;
    }

    public short getSn() {
        return sn;
    }

    public void setSn(byte sn) {
        this.sn = sn;
    }

    public short getSrn() {
        return srn;
    }

    public void setSrn(byte srn) {
        this.srn = srn;
    }

    public int getNodebid() {
        return nodebid;
    }

    public void setNodebid(int nodebid) {
        this.nodebid = nodebid;
    }

    @Override
    public String toString() {
        return "Sctplnk{" + "filename=" + filename + ", LOCIP1=" + LOCIP1 + ", LOCIP=" + LOCIP + ", SCTPLNKN=" + SCTPLNKN + ", sn=" + sn + ", srn=" + srn + ", nodebid=" + nodebid + ", peerIp=" + peerIp + ", sctpNo=" + sctpNo + '}';
    }

    
}
