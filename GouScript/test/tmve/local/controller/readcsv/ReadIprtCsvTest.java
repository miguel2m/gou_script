/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmve.local.controller.readcsv;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tmve.local.main.Main;
import tmve.local.model.Iprt;

/**
 *
 * @author P05144
 */
public class ReadIprtCsvTest {
    
    public ReadIprtCsvTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        //Inicializa la direccion de la DB (./db)
        Main.setDb_dir("./db");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getIprtPortNexthop method, of class ReadIprtCsv.
     */
    @Test
    public void testGetIprtPortNexthop() throws Exception {
        //System.out.println("getIprtPortNexthop");
        String rnc = "CCAB01"; 
        short sn = 20;
        short srn = 3;
        short port = 0;
        Iprt expResult = new Iprt();
        expResult.setDstip("10.18.41.124"); //EL DSTIP DE LA RNC
        List<Iprt> result = ReadIprtCsv.getIprtPortNexthop(rnc, sn, srn, port);
        //System.out.println("Result "+result.get(0).toString());
        assertEquals(expResult.getDstip(), result.get(0).getDstip());
        
    }

    /**
     * Test of getIprtNodeNexthop method, of class ReadIprtCsv.
     */
    @Test
    public void testGetIprtNodeNexthop() throws Exception {
        //System.out.println("getIprtNodeNexthop");
        String rnc = "CCAB01";
        String dstip = "10.154.20.228"; //RED NETWORK del NODEB U_CARICUAO EN EL IPRT DE LA RNC
        Iprt expResult = new Iprt();
        expResult.setDstip("10.154.20.228");  //RED NETWORK del NODEB U_CARICUAO
        List<Iprt> result = ReadIprtCsv.getIprtNodeNexthop(rnc, dstip);
        //System.out.println("Result "+result.get(0).toString());
        assertEquals(expResult.getDstip(), result.get(0).getDstip());
        
    }

    /**
     * Test of getIprtNodeBRtidx method, of class ReadIprtCsv.
     */
    @Test
    public void testGetIprtNodeBRtidx() throws Exception {
        //System.out.println("getIprtNodeBRtidx");
        String _nodeBName = "U_CARICUAO";
        long expResult = 110; //VALOR MAYOR (ACTUAL) de los RTIDX de U_CARICUAO
        long result = ReadIprtCsv.getIprtNodeBRtidx(_nodeBName);
        //System.out.println(" result "+result);
        assertEquals(expResult, result);
        
    }
    
}
