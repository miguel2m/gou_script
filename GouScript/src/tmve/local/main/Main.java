/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.main;


import ch.qos.logback.classic.util.ContextInitializer;
import com.opencsv.exceptions.CsvConstraintViolationException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tmve.local.controller.ExecutionTime;
import tmve.local.controller.NodeList;
import tmve.local.controller.Validator;
import tmve.local.controller.gouscript.NodeBGouScript;
import tmve.local.controller.gouscript.RncGouScript;
import tmve.local.model.Node;


/**
 *
 * @author Miguelangel
 */
public class Main {
    public static Logger logger ;
    
    
    private static String _db_dir=null;
    /**
     * The file to be parsed.
     *
     * @since 1.0.0
     * @version 1.0.0
     */
    private String dataFile;
    /**
     * Set name of file to parser.
     *
     * @since 1.0.0
     * @version 1.0.0
     * @param String filename
     */
    public void setFileName(String filename) {
        this.dataFile = filename;
    }

    public static String getDb_dir() {
        return _db_dir;
    }

    public static void setDb_dir(String _db_dir) {
        Main._db_dir = _db_dir;
    }
    
    
    
        /**
     * Tracks Managed Object attributes to write to file. This is dictated by
     * the first instance of the MO found.
     *
     * @TODO: Handle this better.
     *
     * @since 1.0.0
     */
    private Map<Integer, LinkedHashMap> moRow = new LinkedHashMap<Integer, LinkedHashMap>();
    /**
     * File or directory
     */
    private String dataSource;
    /*public void processFileOrDirectory()
            throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException, IOException {
        //this.dataFILe;
        Path file = Paths.get(this.dataSource);
        boolean isRegularExecutableFile = Files.isRegularFile(file)
                & Files.isReadable(file);

        if (isRegularExecutableFile) {
            this.setFileName(this.dataSource);
             CSVReader reader = new CSVReader(new FileReader(this.dataSource));
            String[] nextLine;
            Integer rowNum = 0;
            nextLine = reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                //Row row = sheet.createRow(rowNum++);
                LinkedHashMap<String, String> moCol= new LinkedHashMap<String, String>();
                
                moCol.put("FILENAME",nextLine[0]);
                moCol.put("DATETIME",nextLine[1]);
                moCol.put("NE_TECHNOLOGY",nextLine[2]);
                moCol.put("NE_VENDOR",nextLine[3]);
                moCol.put("NE_VERSION",nextLine[4]);
                moCol.put("NE_TYPE",nextLine[5]);
                moCol.put("CN",nextLine[6]);
                moCol.put("IP",nextLine[7]);
                moCol.put("PN",nextLine[8]);
                moCol.put("PT",nextLine[9]);
                moCol.put("SBT",nextLine[10]);
                moCol.put("SN",nextLine[11]);
                moCol.put("SRN",nextLine[12]);
                moCol.put("VRFIDX",nextLine[13]);
                moCol.put("CTRLMODE",nextLine[14]);
                moCol.put("MASK",nextLine[15]);
                moRow.put(rowNum++, moCol);
                
            }
            
            imprimirMap ();
        }
    }*/
    
     public void imprimirMap (){
            
        // Imprimimos el Map con un Iterador que ya hemos instanciado anteriormente
        Iterator<Integer> it = moRow.keySet().iterator();
            while(it.hasNext()){
              Integer key = it.next();
              System.out.println("Clave: " + key + " -> Valor: " + moRow.get(key).toString()+" \n");
            }
            
            
            System.out.println("Test: " + moRow.get(77).get("IP").toString()+" \n");

        }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CsvConstraintViolationException  {
        
        //logger.error("Example log from {}", Main.class.getSimpleName());
        
        Options options = new Options();
        CommandLine cmd = null;
        ExecutionTime executionTime = new ExecutionTime(); //Execution Time
        String outputDirectory = null; // Output directory  
        String inputFile = null; //Input file
        Boolean showHelpMessage = false;
        Boolean showVersion = false;
        
        short _srn=-1 /*SUB RACK NUMBER*/,
             _sn=-1 /*Slock Number*/,
              _p=-1 /*Case port is a number*/;
        String _port/*Port NUmber or NextHoop IP to change*/,
               _vrfIp = null,// VRF IP to link RNC 'n Nodes
               _rnc = null; //Main script = new Main();
        
        try {
           
            
            options.addOption( "v", "version", false, "display version" );
            options.addOption( Option.builder("db")
                    .longOpt( "input-db-directory" )
                    .desc( "DB path directory ")
                    .hasArg()
                    .argName( "INPUT_DB_DIRECTORY" ).build());
            options.addOption( Option.builder("i")
                    .longOpt( "input-file" )
                    .desc( "(NODEB list) please enter input CSV file ")
                    .hasArg()
                    .argName( "INPUT_FILE" ).build());
            options.addOption(Option.builder("rnc")
                    .longOpt( "rnc" )
                    .desc( "(RNC) to create GOU Script")
                    .hasArg()
                    .argName( "RNC" ).build());
            options.addOption(Option.builder("srn")
                    .longOpt( "srn" )
                    .desc( "(SRN) sub rack number to change")
                    .hasArg()
                    .argName( "SRN" ).build());
            options.addOption(Option.builder("sn")
                    .longOpt( "sn" )
                    .desc( "(SR) Slock Number to change")
                    .hasArg()
                    .argName( "SN" ).build());
            options.addOption(Option.builder("p")
                    .longOpt( "p" )
                    .desc( "(PORT) Number or (NEXTHOOP IP)  to change")
                    .hasArg()
                    .argName( "P" ).build());
            options.addOption(Option.builder("vrf")
                    .longOpt( "vrf" )
                    .desc( "(VRF-IP)  to routing NODES")
                    .hasArg()
                    .argName( "VRF" ).build());
            options.addOption(Option.builder("o")
                    .longOpt( "output-directory" )
                    .desc( "output directory name")
                    .hasArg()
                    .argName( "OUTPUT_DIRECTORY" ).build());
            options.addOption( "h", "help", false, "show help" );
            
            //Parse command line arguments
            CommandLineParser parser = new DefaultParser();
            cmd = parser.parse( options, args);
            
            if( cmd.hasOption("h")){
                showHelpMessage = true;
            }

            if( cmd.hasOption("v")){
                showVersion = true;
            }
            if(cmd.hasOption("db")){
               
              _db_dir= cmd.getOptionValue("db");
              System.out.println("db "+_db_dir);
                //script.dataSource=inputFile;
                
            }
            if(cmd.hasOption('i')){
                inputFile = cmd.getOptionValue("i");
                //script.dataSource=inputFile;
                System.out.println("Input file "+inputFile);
            }
            if(cmd.hasOption("rnc")){
                
              _rnc= cmd.getOptionValue("rnc").toUpperCase();
              System.out.println("RNC "+_rnc);
                //script.dataSource=inputFile;
                
            }
            if(cmd.hasOption("srn")){
                _srn = Short.parseShort(cmd.getOptionValue("srn")) ;
              //if(Validator.isValidNumber(cmd.getOptionValue("srn")) >=0){
                   // _srn = Validator.isValidNumber(cmd.getOptionValue("srn"));
                 //   System.out.println("SRN "+_srn);
              //}else{
              //    System.out.println("SRN is not valid number (0..255)");
              //    System.exit(1);
              //}
                //script.dataSource=inputFile;
                
            }
            if(cmd.hasOption("sn")){
                _sn = Short.parseShort(cmd.getOptionValue("sn"));
              /*if(Validator.isValidNumber(cmd.getOptionValue("sn")) >=0){
                    _sn = Validator.isValidNumber(cmd.getOptionValue("sn"));
                    System.out.println("SN "+_sn);
              }else{
                  System.out.println("SN is not valid number (0..255)");
                  System.exit(1);
              }*/
            }
            if(cmd.hasOption('p')){
                _p = Short.parseShort( cmd.getOptionValue('p'));
              //String temp = cmd.getOptionValue('p');
              /*if(Validator.isValidNumber(temp) >=0){
                    _p = Short.parseShort(temp);
                    System.out.println("Port "+_p);
              }else{
                  if(Validator.isValidIp(temp)){
                      _port = temp;
                      System.out.println("Port "+_port);
                  }else{
                    System.out.println("PORT is not valid number (0..255) or IP address");
                    System.exit(1);
                  }
              }*/
              
                
            }
            if(cmd.hasOption("vrf")){
                _vrfIp=cmd.getOptionValue("vrf");
                /*
                if(Validator.isValidIp(cmd.getOptionValue("vrf"))){
                    _vrfIp = cmd.getOptionValue("vrf");
                    System.out.println("VRF IP "+_vrfIp);
                }else{
                    System.out.println("VRF is not a valid IP address");
                    System.exit(1);
                }
                //script.dataSource=inputFile;
                */
            }
            if(cmd.hasOption('o')){
                outputDirectory = cmd.getOptionValue("o");
                System.out.println("Output directory "+outputDirectory);
            }
            
            
            
            
        }catch (NumberFormatException ex) {
            System.out.println("Please enter valid SN or SRN  "+ex.getMessage().toString());
         //   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex.getMessage().toString());
            System.exit(1);
        }catch(IllegalArgumentException e){
           System.out.println("AQUI "+e.getMessage().toString());
           System.exit(1);
        }catch (ParseException ex) {
            System.out.println(" "+ex.getMessage().toString());
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex.getMessage().toString());
            System.exit(1);
        }
            
            System.setProperty("LOG_DIR", outputDirectory );
            System.setProperty("LOG_FILE", _rnc );
            logger = LoggerFactory.getLogger(Main.class);
            
            if(showVersion == true ){
                System.out.println("1.0.0");
                System.out.println("Copyright (c) Telefonica Venezuela"+executionTime.getDate());
                System.exit(0);
            }
            
            //show help
            if( showHelpMessage == true || 
                inputFile == null || _rnc == null || _db_dir ==null ||
                ( outputDirectory == null ) ){
                     HelpFormatter formatter = new HelpFormatter();
                     String header = "Execute GOU Scripts\n\n";
                     String footer = "\n";
                     footer += "Examples: \n";
                     footer += "java -jar gouscript-1.0.jar -db ./DB_FOLDER -i NODES-LIST.csv -srn 1 -sn 2 -p 1 -vrf 10.x.x.254 -o ./OUT_FOLDER\n";
                     footer += "\nCopyright (c)"+executionTime.getDate()+" Telefonica Veneuzela";
                     formatter.printHelp( "java -jar gouscript-1.0.jar -h", header, options, footer );
                     System.exit(0);
            }
           if (!Validator.isFile(inputFile)) {
                System.out.println("ERROR: Cannot read input FILE " + inputFile);
                System.exit(0);
           } else {
            if (!Validator.isDirectory(_db_dir)) {
                logger.error(" Cannot read DB folder " + _db_dir);
                System.out.println("ERROR: Cannot read DB folder " + _db_dir);
                System.exit(0);
            }
           }
            //Confirm that the output directory is a directory and has write 
            //privileges
            if(outputDirectory != null ){
                File fOutputDir = new File(outputDirectory);
                if (!fOutputDir.isDirectory() ) {
                    System.err.println("ERROR: The specified output directory is not a directory!.");
                    System.exit(1);
                }

                if (!fOutputDir.canWrite()) {
                    System.err.println("ERROR: Cannot write to output directory (deny folder permission)!");
                    System.exit(1);
                }
            }
            //System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, outputDirectory);

            
            //logger.info("Example log from {}", Main.class.getSimpleName());
        try {
             List<Node> nodes = NodeList.getNodeBList(inputFile);
             nodes.forEach(System.out::println);
             Iterator<Node> it = nodes.iterator();
             
            do{
                
                Node temp =it.next();
               
                    System.out.println("----"+temp.getNodeb_name()+" RNC Integrate----");
                    System.out.println( RncGouScript.createRNCGouScript(temp, _rnc,_srn,_sn,_p,_vrfIp));
                    System.out.println("----"+temp.getNodeb_name()+" NODEB Integrate----");
                    System.out.println( NodeBGouScript.createNodeBGouScript(temp, _vrfIp,_rnc));
                    System.out.println("----"+temp.getNodeb_name()+" RNC Rollback----");
                    System.out.println( RncGouScript.createRNCRollbackGouScript(temp, _rnc,_srn,_sn,_p));
                    System.out.println("----"+temp.getNodeb_name()+" NODEB Rollback----");
                     System.out.println( NodeBGouScript.createNodeBGouScriptRollback(_rnc,temp));
                     System.out.println("\n");
                 //System.out.println("SCRIPT RNC \n"+RncGouScript.createRNCGouScript(temp, _rnc));
                
            }while (it.hasNext());
            
            //script.processFileOrDirectory();
       /* }catch (IllegalArgumentException e1){
             System.out.println(" "+e1.getMessage().toString()+" "+e1.getCause());*/
        }catch (CsvConstraintViolationException e2) {
             System.out.println(" "+e2.getMessage().toString());
        }catch (IOException e) {
             System.out.println(" "+e.getMessage().toString());
        }
            //System.out.println("Newtork: "+Validator.getNetwork("10.18.50.82", "255.255.255.252"));
            System.out.println(executionTime.getExecutionTime());
    }
    
}
