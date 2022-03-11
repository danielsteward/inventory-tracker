/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package coreinventorytracker;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author danst
 */
// TODO Write Use Cases for tests.
public class PlantUnitDAOTest {
    
    public PlantUnitDAOTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getPunitList method, of class PlantUnitDAO.
     */
    @Test
    public void testGetPunitList() throws Exception {
        System.out.println("getPunitList");
        String sqlArg = "";
        PlantUnitDAO instance = null;
        List<PlantUnit> expResult = null;
        List<PlantUnit> result = instance.getPunitList(sqlArg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertUnit method, of class PlantUnitDAO.
     */
    @Test
    public void testInsertUnit() throws Exception {
        System.out.println("insertUnit");
        PlantUnit pu = null;
        PlantUnitDAO instance = null;
        instance.insertUnit(pu);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
