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
import tmve.local.model.AdjNode;

/**
 *
 * @author P05144
 */
public class ReadAdjnodeCsvTest {
    
    public ReadAdjnodeCsvTest() {
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
     * Test of getAdjNode method, of class ReadAdjnodeCsv.
     */
    @Test
    public void testGetAdjNode() throws Exception {
        //System.out.println("getAdjNode");
        String nodeName = "U_CARICUAO";
        short expResult = 1430; //ANI de U_CARICUAO
        List<AdjNode> result = ReadAdjnodeCsv.getAdjNode(nodeName);
        //System.out.println("ANI "+nodeName+" = "+result.get(0).getAni());
        assertEquals(expResult, result.get(0).getAni());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
