/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.model;

import com.opencsv.bean.processor.StringProcessor;

/**
 *
 * @author Miguelangel
 */
public class ConvertEmptyOrBlankStringsToDefault implements StringProcessor{
    String defaultValue;
    @Override
    public String processString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public void setParameterString(String value) {
        defaultValue = value;
    }
    
}
