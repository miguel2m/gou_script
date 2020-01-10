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
public class NodeBIp {
    @CsvBindByName (column = "FILENAME")
    private String filename;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "NODEBID")
    private int nodebid;
    @CsvBindByName (column = "NBTRANTP") /*INDICA SI EL NODO ES FULL IP, DUAL STACK o ATM*/
    private String NBTRANTP;
    @CsvBindByName (column = "NBIPOAMIP")
    private String NBIPOAMIP; /*Es el HOST IP pero debe utilizar el Network IP*/
 
    @CsvBindByName (column = "NBIPOAMMASK")
    private String NBIPOAMMASK;

    public String getNBTRANTP() {
        return NBTRANTP;
    }

    public void setNBTRANTP(String NBTRANTP) {
        this.NBTRANTP = NBTRANTP;
    }
        
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getNodebid() {
        return nodebid;
    }

    public void setNodebid(int nodebid) {
        this.nodebid = nodebid;
    }

    public String getNBIPOAMIP() {
        return NBIPOAMIP;
    }

    public void setNBIPOAMIP(String NBIPOAMIP) {
        this.NBIPOAMIP = NBIPOAMIP;
    }

    public String getNBIPOAMMASK() {
        return NBIPOAMMASK;
    }

    public void setNBIPOAMMASK(String NBIPOAMMASK) {
        this.NBIPOAMMASK = NBIPOAMMASK;
    }

    @Override
    public String toString() {
        return "NodeBIp{" + "filename=" + filename + ", nodebid=" + nodebid + ", NBIPOAMIP=" + NBIPOAMIP + ", NBIPOAMMASK=" + NBIPOAMMASK + '}';
    }
    
    
}
