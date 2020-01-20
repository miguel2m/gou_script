/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
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
    public static int exportGouScript (XSSFWorkbook wb,
            XSSFSheet sheet,
            Node node_name, 
            String output_Directory,
            List<String> gouScript,
            //String gouScriptType,
            int rowNum){
      
        DateTimeFormatter formatter =
        DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM )
                     .withLocale( Locale.getDefault() )
                     .withZone( ZoneId.systemDefault() );
        
        String output = formatter.format( Instant.now() );
        
        //XSSFSheet sheet = wb.createSheet(gouScriptType);
        Iterator<String> it =  gouScript.iterator();
        /*Integer rowNum = 0;
        Integer colNum = 0;*/
    
        CellStyle styleHeader = wb.createCellStyle();

            styleHeader.setBorderBottom(BorderStyle.THICK);
            styleHeader.setBottomBorderColor(IndexedColors.BLACK1.getIndex());
            styleHeader.setBorderLeft(BorderStyle.THICK);
            styleHeader.setLeftBorderColor(IndexedColors.INDIGO.getIndex());
            styleHeader.setBorderRight(BorderStyle.THIN);
            styleHeader.setRightBorderColor(IndexedColors.BLACK1.getIndex());
            styleHeader.setBorderTop(BorderStyle.THICK);
            styleHeader.setTopBorderColor(IndexedColors.BLACK1.getIndex());
            styleHeader.setAlignment(HorizontalAlignment.GENERAL);
            styleHeader.setVerticalAlignment( VerticalAlignment.CENTER);

            XSSFRow rowHeader2 = sheet.createRow(rowNum);
            XSSFCell cellHeader2 = rowHeader2 .createCell(0);
            cellHeader2.setCellValue("//INICIO NODEB "+node_name.getNodeb_name()+" COMMAND DATE: "+output);
            cellHeader2.setCellStyle(styleHeader);
            
        
        rowNum ++;
        while ( it .hasNext()){            
            String temp = it.next();
            
            rowHeader2  = sheet.createRow(rowNum);
            XSSFCell cell = rowHeader2 .createCell(0);
            
           
            sheet.addMergedRegion(new CellRangeAddress(
                rowNum, //first row (0-based)
                rowNum, //last row  (0-based)
                0, //first column (0-based)
                26 //last column  (0-based)
            ));
           
            cell.setCellValue(temp);
            //cell.setCellStyle(styleHeader );
           rowNum++;
        }
        
        
        XSSFRow rowHeader3 = sheet.createRow(rowNum++);
            XSSFCell cellHeader3 = rowHeader3 .createCell(0);
            cellHeader3.setCellValue("//FIN NODEB "+node_name.getNodeb_name()+" COMMAND DATE: "+output);
            cellHeader3.setCellStyle(styleHeader);
        
        XSSFRow rowHeader4 = sheet.createRow(rowNum++);
            XSSFCell cellHeader4 = rowHeader4 .createCell(0);
            cellHeader4.setCellValue("");
           
        
        XSSFRow rowHeader5 = sheet.createRow(rowNum++);
            XSSFCell cellHeader5 = rowHeader5 .createCell(0);
            cellHeader5.setCellValue("");

            
            
       return rowNum;
    }
}
