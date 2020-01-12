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
import tmve.local.model.Ippm;

/**
 *
 * @author P05144
 */
public class ReadIppmCsv {
    /**
     * IPPM METHOD
     * @param _rnc
     * @param aniNodeName
     * @return IPPM DEL NODE NAME EN LA RNC
     * @throws IOException
     * @throws CsvConstraintViolationException 
     */
    public static List<Ippm> getIppmNode(String _rnc,int aniNodeName)throws IOException,CsvConstraintViolationException{
        
        Path myPath = Paths.get(Main.getDb_dir()+"/IPPM.csv");
        List <Ippm> ipPmNodes ;
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<Ippm> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Ippm.class);
            BeanVerifier beanVerifier = (BeanVerifier) (Object t) -> {
                Ippm node  = (Ippm )t;
                return (node.getAni() == aniNodeName &&
                        node.getFilename().contains(_rnc)); //To change body of generated lambdas, choose Tools | Templates.
            };
            
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(Ippm.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withVerifier(beanVerifier)
                    .build();
          
            ipPmNodes = csvToBean.parse() ;

            
        }
        return ipPmNodes;
        
    }
}
