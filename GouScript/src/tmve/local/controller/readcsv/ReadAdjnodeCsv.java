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
import tmve.local.model.AdjNode;

/**
 *
 * @author Miguelangel
 */
public class ReadAdjnodeCsv {
    
    public static List<AdjNode> getAdjNode(String _rnc,String nodeName)throws IOException,CsvConstraintViolationException{
        
        Path myPath = Paths.get(Main.getDb_dir()+"/ADJNODE.csv");
        List <AdjNode> aniNodes;
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<AdjNode> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(AdjNode.class);
            BeanVerifier beanVerifier = (BeanVerifier) (Object t) -> {
                AdjNode node  = (AdjNode)t;
                return (node.getFilename().contains(_rnc) &&
                        node.getName().equals(nodeName)); //To change body of generated lambdas, choose Tools | Templates.
            };
            
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(AdjNode.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withVerifier(beanVerifier)
                    .build();
            
            aniNodes= csvToBean.parse();

            
        }
        return aniNodes;
        
    }
}
