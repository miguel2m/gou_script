/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.processor.PreAssignmentProcessor;
import com.opencsv.bean.validators.MustMatchRegexExpression;
import com.opencsv.bean.validators.PreAssignmentValidator;

/**
 *
 * @author Miguelangel
 */
public class Iprt {
    @CsvBindByName (column = "FILENAME")
    private String filename;
    @CsvBindByName (column = "DSTIP")
    private String dstip;
    @CsvBindByName (column = "DSTMASK")
    private String dstmask;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "NO NODEB NEXTHOOP -- PLEASE CHECK")
    @CsvBindByName (column = "NEXTHOP")
    private String nexthop;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "SN")
    private short sn;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "SRN")
    private short srn;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "NEXTPN")
    private short nextpn;
    @CsvBindByName (column = "REMARK")
    private String remark;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "RTIDX")
    private short rtIdx;
    @CsvBindByName (column = "SBT")
    private String sbt;
    @CsvBindByName (column = "RTTYPE")
    private String rtType;

    public short getRtIdx() {
        return rtIdx;
    }

    public void setRtIdx(short rtIdx) {
        this.rtIdx = rtIdx;
    }

    public String getSbt() {
        return sbt;
    }

    public void setSbt(String sbt) {
        this.sbt = sbt;
    }

    public String getRtType() {
        return rtType;
    }

    public void setRtType(String rtType) {
        this.rtType = rtType;
    }
    
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDstip() {
        return dstip;
    }

    public void setDstip(String dstip) {
        this.dstip = dstip;
    }

    public String getDstmask() {
        return dstmask;
    }

    public void setDstmask(String dstmask) {
        this.dstmask = dstmask;
    }

    public String getNexthop() {
        return nexthop;
    }

    public void setNexthop(String nexthop) {
        this.nexthop = nexthop;
    }

    public short getSn() {
        return sn;
    }

    public void setSn(short sn) {
        this.sn = sn;
    }

    public short getSrn() {
        return srn;
    }

    public void setSrn(short srn) {
        this.srn = srn;
    }

    public short getNextpn() {
        return nextpn;
    }

    public void setNextpn(short nextpn) {
        this.nextpn = nextpn;
    }

    @Override
    public String toString() {
        return "Iprt{" + "filename=" + filename + ", dstip=" + dstip + ", dstmask=" + dstmask + ", nexthop=" + nexthop + ", sn=" + sn + ", srn=" + srn + ", nextpn=" + nextpn + ", remark=" + remark + ", rtIdx=" + rtIdx + ", sbt=" + sbt + ", rtType=" + rtType + '}';
    }
 
}
