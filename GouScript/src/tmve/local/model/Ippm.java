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
 * @author P05144
 */
public class Ippm {
    @CsvBindByName (column = "FILENAME")
    private String filename;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "ANI")
    private int ani;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "PATHID")
    private short pathId;
    @CsvBindByName (column = "ISQOSPATH")
    private String isqosPath;
    @CsvBindByName (column = "LOSTPKTDETECTSW")
    private String lostPktDetectSw;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "PMPRD")
    private short pmPrd;

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

    public short getPathId() {
        return pathId;
    }

    public void setPathId(short pathId) {
        this.pathId = pathId;
    }

    public String getIsqosPath() {
        return isqosPath;
    }

    public void setIsqosPath(String isqosPath) {
        this.isqosPath = isqosPath;
    }

    public String getLostPktDetectSw() {
        return lostPktDetectSw;
    }

    public void setLostPktDetectSw(String lostPktDetectSw) {
        this.lostPktDetectSw = lostPktDetectSw;
    }

    public short getPmPrd() {
        return pmPrd;
    }

    public void setPmPrd(short pmPrd) {
        this.pmPrd = pmPrd;
    }

    @Override
    public String toString() {
        return "Ippm{" + "filename=" + filename + ", ani=" + ani + ", pathId=" + pathId + ", isqosPath=" + isqosPath + ", lostPktDetectSw=" + lostPktDetectSw + ", pmPrd=" + pmPrd + '}';
    }
    
    
}
