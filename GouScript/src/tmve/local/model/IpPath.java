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
public class IpPath {
    @CsvBindByName (column = "FILENAME")
    private String filename;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "ANI")
    private int ani;
    @CsvBindByName (column = "PATHID")
    private String PATHID;
    @CsvBindByName (column = "PEERIPADDR")
    private String PEERIPADDR;
    @CsvBindByName (column = "VLANFLAG")
    private String VLANFLAG;
    @CsvBindByName (column = "CARRYFLAG")
    private String CARRYFLAG;
    @CsvBindByName (column = "PATHT")
    private String PATHT;
    @CsvBindByName (column = "PEERMASK")
    private String PEERMASK;
    @CsvBindByName (column = "TXBW")
    private String TXBW;
    @CsvBindByName (column = "RXBW")
    private String RXBW;
    @CsvBindByName (column = "PATHCHK")
    private String PATHCHK;
    @CsvBindByName (column = "ITFT")
    private String ITFT;
    @CsvBindByName (column = "TRANST")
    private String TRANST;
    @CsvBindByName (column = "TRMLOADTHINDEX")
    private String TRMLOADTHINDEX;
    @CsvBindByName (column = "REMARK")
    private String REMARK;
    @CsvBindByName (column = "IPADDR")
    private String IPADDR;

    public String getIPADDR() {
        return IPADDR;
    }

    public void setIPADDR(String IPADDR) {
        this.IPADDR = IPADDR;
    }
    
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getAni() {
        return ani;
    }

    public void setAni(int ani) {
        this.ani = ani;
    }

    public String getPATHID() {
        return PATHID;
    }

    public void setPATHID(String PATHID) {
        this.PATHID = PATHID;
    }

    public String getPEERIPADDR() {
        return PEERIPADDR;
    }

    public void setPEERIPADDR(String PEERIPADDR) {
        this.PEERIPADDR = PEERIPADDR;
    }

    public String getVLANFLAG() {
        return VLANFLAG;
    }

    public void setVLANFLAG(String VLANFLAG) {
        this.VLANFLAG = VLANFLAG;
    }

    public String getCARRYFLAG() {
        return CARRYFLAG;
    }

    public void setCARRYFLAG(String CARRYFLAG) {
        this.CARRYFLAG = CARRYFLAG;
    }

    public String getPATHT() {
        return PATHT;
    }

    public void setPATHT(String PATHT) {
        this.PATHT = PATHT;
    }

    public String getPEERMASK() {
        return PEERMASK;
    }

    public void setPEERMASK(String PEERMASK) {
        this.PEERMASK = PEERMASK;
    }

    public String getTXBW() {
        return TXBW;
    }

    public void setTXBW(String TXBW) {
        this.TXBW = TXBW;
    }

    public String getRXBW() {
        return RXBW;
    }

    public void setRXBW(String RXBW) {
        this.RXBW = RXBW;
    }

    public String getPATHCHK() {
        return PATHCHK;
    }

    public void setPATHCHK(String PATHCHK) {
        this.PATHCHK = PATHCHK;
    }

    public String getITFT() {
        return ITFT;
    }

    public void setITFT(String ITFT) {
        this.ITFT = ITFT;
    }

    public String getTRANST() {
        return TRANST;
    }

    public void setTRANST(String TRANST) {
        this.TRANST = TRANST;
    }

    public String getTRMLOADTHINDEX() {
        return TRMLOADTHINDEX;
    }

    public void setTRMLOADTHINDEX(String TRMLOADTHINDEX) {
        this.TRMLOADTHINDEX = TRMLOADTHINDEX;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    @Override
    public String toString() {
        return "IpPath{" + "filename=" + filename + ", ani=" + ani + ", PATHID=" + PATHID + ", PEERIPADDR=" + PEERIPADDR + ", VLANFLAG=" + VLANFLAG + ", CARRYFLAG=" + CARRYFLAG + ", PATHT=" + PATHT + ", PEERMASK=" + PEERMASK + ", TXBW=" + TXBW + ", RXBW=" + RXBW + ", PATHCHK=" + PATHCHK + ", ITFT=" + ITFT + ", TRANST=" + TRANST + ", TRMLOADTHINDEX=" + TRMLOADTHINDEX + ", REMARK=" + REMARK + ", IPADDR=" + IPADDR + '}';
    }

   

    
    
    
}
