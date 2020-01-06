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
public class NodeB {
    @CsvBindByName (column = "FILENAME")
    private String filename;
    @PreAssignmentProcessor(processor = ConvertEmptyOrBlankStringsToDefault.class, paramString = "-1")
    @CsvBindByName (column = "NODEBID")
    private int nodebid;
    @CsvBindByName (column = "NODEBNAME")
    private String nodebname;

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

    public String getNodebname() {
        return nodebname;
    }

    public void setNodebname(String nodebname) {
        this.nodebname = nodebname;
    }

    @Override
    public String toString() {
        return "NodeB{" + "filename=" + filename + ", nodebid=" + nodebid + ", nodebname=" + nodebname + '}';
    }
    
    
    
}
