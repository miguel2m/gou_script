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
import tmve.local.model.NodeB;

/**
 *
 * @author Miguelangel
 */
public class ReadNodeBCsv {
    public static List<NodeB> getNodeBId(String node_name)throws IOException,CsvConstraintViolationException{
        
        Path myPath = Paths.get(Main.getDb_dir()+"/NODEB.csv");
        List <NodeB> nodeB;
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<NodeB> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(NodeB.class);
            BeanVerifier beanVerifier = new BeanVerifier() {
                @Override
                public boolean verifyBean(Object t) throws CsvConstraintViolationException {
                    NodeB node  = (NodeB)t;                    
                    return (node.getNodebname().equals(node_name) ); //To change body of generated lambdas, choose Tools | Templates.
                }
            };
            
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(NodeB.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withVerifier(beanVerifier)
                    .build();
            
            nodeB= csvToBean.parse();

            
        }
        return nodeB;
        
    }
}
