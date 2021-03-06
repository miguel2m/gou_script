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
import java.util.Comparator;
import java.util.List;
import tmve.local.main.Main;
import tmve.local.model.Iprt;

/**
 *
 * @author Miguelangel
 */
public class ReadIprtCsv {
    
    /**
     * BUSCA EL NEXTHOP POR SN,SRN Y PUERTO
     * @param rnc
     * @param sn
     * @param srn
     * @param port
     * @return IRP (NEXTHOP)
     * @throws IOException
     * @throws CsvConstraintViolationException 
     */
    public static List<Iprt> getIprtPortNexthop(String rnc,short sn,short srn,short port)throws IOException,CsvConstraintViolationException{
        
        Path myPath = Paths.get(Main.getDb_dir()+"/IPRT.csv");
        List <Iprt> iprtNodes;
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<Iprt> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Iprt.class);
            BeanVerifier beanVerifier = (BeanVerifier) (Object t) -> {
                Iprt node  = (Iprt)t;
                return (node.getFilename().contains(rnc) &&
                        node.getNextpn() == port&&
                        node.getSn() == sn &&
                        node.getSrn() == srn ); //To change body of generated lambdas, choose Tools | Templates.
            };
            
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(Iprt.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withVerifier(beanVerifier)
                    .build();
            
            iprtNodes= csvToBean.parse();

            
        }
        return iprtNodes;
        
    }
    
    /**
     * BUSCA EL NEXTHOP DEL NODEB
     * @param rnc
     * @param dstip
     * @return NEXTHOP DEL NODEB
     * @throws IOException
     * @throws CsvConstraintViolationException 
     */
    public static List<Iprt> getIprtNodeNexthop(String rnc,String dstip)
            throws IOException,CsvConstraintViolationException{
        
        Path myPath = Paths.get(Main.getDb_dir()+"/IPRT.csv");
        List <Iprt> iprtNodes;
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<Iprt> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Iprt.class);
            BeanVerifier beanVerifier = (BeanVerifier) (Object t) -> {
                Iprt node  = (Iprt)t;
                return (node.getFilename().contains(rnc) &&
                        node.getDstip().equals(dstip)); //To change body of generated lambdas, choose Tools | Templates.
            };
            
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(Iprt.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withVerifier(beanVerifier)
                    .build();
            
            iprtNodes= csvToBean.parse();
            
         
            
        }
        return iprtNodes;
        
    }
    
    /**
     * BUSCA EL RTIDX DEL NODEB Y CALCULA EL MAYOR
     * @param _nodeBName
     * @return NEXTHOP DEL NODEB
     * @throws IOException
     * @throws CsvConstraintViolationException 
     */
    public static short getIprtNodeBRtidx(String _nodeBName)
            throws IOException,CsvConstraintViolationException{
        
        Path myPath = Paths.get(Main.getDb_dir()+"/IPRT.csv");
        List <Iprt> iprtNodes;
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<Iprt> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Iprt.class);
            BeanVerifier beanVerifier = (BeanVerifier) (Object t) -> {
                Iprt node  = (Iprt)t;
                return (node.getFilename().contains(_nodeBName)); //To change body of generated lambdas, choose Tools | Templates.
            };
            
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(Iprt.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withVerifier(beanVerifier)
                    .build();
            
            iprtNodes= csvToBean.parse();

            
        }
        return iprtNodes.stream()
                            .max( Comparator.comparing( Iprt::getRtIdx ) )
                            .get().getRtIdx();
        
        
    }
    
    /**
     * EN CASO DE QUE EL NETWORK RNC NO SE ENCUENTRE EN LA IPRT DEL NODO
     * @param rnc
     * @return IPRT DEL NODEB
     * @throws IOException
     * @throws CsvConstraintViolationException 
     */
    public static List<Iprt> getIprtNode(String rnc)
            throws IOException,CsvConstraintViolationException{
        
        Path myPath = Paths.get(Main.getDb_dir()+"/IPRT.csv");
        List <Iprt> iprtNodes;
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<Iprt> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Iprt.class);
            BeanVerifier beanVerifier = (BeanVerifier) (Object t) -> {
                Iprt node  = (Iprt)t;
                return (node.getFilename().contains(rnc)); //To change body of generated lambdas, choose Tools | Templates.
            };
            
            CsvToBean csvToBean = new CsvToBeanBuilder(br)
                    .withType(Iprt.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withVerifier(beanVerifier)
                    .build();
            
            iprtNodes= csvToBean.parse();
            
         
            
        }
        return iprtNodes;
        
    }
    
}
