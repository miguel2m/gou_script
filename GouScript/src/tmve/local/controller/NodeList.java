/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvConstraintViolationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import static java.util.Comparator.comparing;
import java.util.List;
import java.util.TreeSet;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import tmve.local.model.Node;

/**
 *
 * @author Miguelangel
 */
public class NodeList {
    
    public static List<Node> getNodeBList(String fileName)throws IOException,CsvConstraintViolationException{
        
        Path myPath = Paths.get(fileName);
        List<Node> nodes;
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
            strategy.setType(Node.class);
            String[] fields = {"nodeb_name"};
            strategy.setColumnMapping(fields);
            BeanVerifier beanVerifier = new BeanVerifier() {
                @Override
                public boolean verifyBean(Object t) throws CsvConstraintViolationException {
                    Node node  = (Node)t;                    
                    return !node.getNodeb_name().isEmpty(); //To change body of generated lambdas, choose Tools | Templates.
                }
            };
            
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(Node.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withVerifier(beanVerifier)
                    .build();
            
            nodes= (List<Node>) csvToBean.parse()
                                         .stream()
                                         .collect(
                                                 collectingAndThen(
                                                         toCollection(() -> 
                                                                 new TreeSet<>(comparing(Node::getNodeb_name)))
                                                         ,ArrayList::new)
                                         );

            
        }
        return nodes;
        
    }
}
