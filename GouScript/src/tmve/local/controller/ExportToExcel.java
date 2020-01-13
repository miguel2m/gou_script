/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import java.util.Iterator;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




import tmve.local.model.Node;

/**
 *
 * @author Miguelangel
 */
public class ExportToExcel {
    /**
     * Metodo que exporta el script a un archivo Excel
     * @param node_name (Nombre del nodo)
     * @param output_Directory (Salida del archivo)
     * @param gouScript (El script)
     * @param gouScriptType  (Si es Integrate o Rollback)
     */
    public static void exportGouScript (XSSFWorkbook wb,
            Node node_name, 
            String output_Directory,
            List<String> gouScript,
            String gouScriptType){
        
        
        
        XSSFSheet sheet = wb.createSheet(gouScriptType);
        Iterator<String> it =  gouScript.iterator();
        Integer rowNum = 0;
        Integer colNum = 0;
        
        
        while ( it .hasNext()){            
            String temp = it.next();
            XSSFRow row = sheet.createRow(rowNum++);
            XSSFCell cell = row.createCell(colNum);
            cell.setCellValue(temp);
            
        }
        
    }
}
