/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.model;

import com.opencsv.bean.CsvBindByPosition;
import java.util.Objects;

/**
 *
 * @author Miguelangel
 */
public class Node {
    @CsvBindByPosition(position = 0)
    private String nodeb_name;

    public String getNodeb_name() {
        return nodeb_name;
    }

    public void setNodeb_name(String nodeb_name) {
        this.nodeb_name = nodeb_name;
    }

    @Override
    public String toString() {
        return "Node{" + "nodeb_name=" + nodeb_name + '}';
    }

   

    
}
