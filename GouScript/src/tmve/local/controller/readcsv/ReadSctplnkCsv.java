/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller.readcsv;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvConstraintViolationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import tmve.local.main.Main;
import tmve.local.model.Sctplnk;

/**
 *
 * @author Miguelangel
 */
public class ReadSctplnkCsv {
    /**
     * METODO QUE RETORNA EL SCTPLNK DEL NODEB ID DEL LADO DE LA RNC
     * @param node_id
     * @return EL SCTPLNK DEL NODEB ID DEL LADO DE LA RNC
     * @throws IOException
     * @throws CsvConstraintViolationException 
     */
    public static List<Sctplnk> getNodeBSctplnk(String _rnc,int node_id)throws IOException,CsvConstraintViolationException{
        
        Path myPath = Paths.get(Main.getDb_dir()+"/SCTPLNK.csv");
        List <Sctplnk> sctplnks;
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<Sctplnk> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Sctplnk.class);
            BeanVerifier beanVerifier = new BeanVerifier() {
                @Override
                public boolean verifyBean(Object t) throws CsvConstraintViolationException {
                    Sctplnk node  = (Sctplnk)t;                    
                    return (node.getFilename().contains(_rnc)&&
                            node.getNodebid() == node_id ); //To change body of generated lambdas, choose Tools | Templates.
                }
            };
            
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(Sctplnk.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withVerifier(beanVerifier)
                    .build();
            
            sctplnks= csvToBean.parse();

            
        }
        return sctplnks;
        
    }
    /**
     * METODO QUE RETORNA EL SCTPLNK DEL LADO DEL NODEB
     * @param node_name
     * @return SCTPLNK DEL LADO DEL NODEB
     * @throws IOException
     * @throws CsvConstraintViolationException 
     */
    public static List<Sctplnk> getNodeBSctplnkNodeIntegrate(String node_name)throws IOException,CsvConstraintViolationException{
        
        Path myPath = Paths.get(Main.getDb_dir()+"/SCTPLNK.csv");
        List <Sctplnk> sctplnks;
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<Sctplnk> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Sctplnk.class);
            BeanVerifier beanVerifier = new BeanVerifier() {
                @Override
                public boolean verifyBean(Object t) throws CsvConstraintViolationException {
                    Sctplnk node  = (Sctplnk)t;                    
                    return (node.getFilename().matches("(?s).*\\b"+node_name+"\\b.*")); //contains(node_name) */); //To change body of generated lambdas, choose Tools | Templates.
                }
            };
            
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(Sctplnk.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withVerifier(beanVerifier)
                    .build();
            
            sctplnks= csvToBean.parse();

            
        }
        return sctplnks;
        
    }
}
