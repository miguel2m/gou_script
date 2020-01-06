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
        return "Sctplnk{" + "filename=" + filename + ", LOCIP1=" + LOCIP1 + ", SCTPLNKN=" + SCTPLNKN + ", sn=" + sn + ", srn=" + srn + ", nodebid=" + nodebid + '}';
    }
    
    
}
