/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package inventoryutilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
public class DBConnectionObTest {
    
    public DBConnectionObTest() {
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
     * Test of getInstance method, of class DBConnectionOb.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        DBConnectionOb expResult = null;
        DBConnectionOb result = DBConnectionOb.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatement method, of class DBConnectionOb.
     */
    @Test
    public void testGetStatement() throws Exception {
        System.out.println("getStatement");
        DBConnectionOb instance = null;
        Statement expResult = null;
        Statement result = instance.getStatement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResultSet method, of class DBConnectionOb.
     */
    @Test
    public void testGetResultSet() throws Exception {
        System.out.println("getResultSet");
        String sqlQuery = "";
        DBConnectionOb instance = null;
        ResultSet expResult = null;
        ResultSet result = instance.getResultSet(sqlQuery);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnection method, of class DBConnectionOb.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        DBConnectionOb instance = null;
        Connection expResult = null;
        Connection result = instance.getConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeConnection method, of class DBConnectionOb.
     */
    @Test
    public void testCloseConnection_0args() throws Exception {
        System.out.println("closeConnection");
        DBConnectionOb instance = null;
        instance.closeConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeConnection method, of class DBConnectionOb.
     */
    @Test
    public void testCloseConnection_Connection() {
        System.out.println("closeConnection");
        Connection connArg = null;
        DBConnectionOb.closeConnection(connArg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of testRowSet method, of class DBConnectionOb.
     */
    @Test
    public void testTestRowSet() throws Exception {
        System.out.println("testRowSet");
        DBConnectionOb instance = null;
        instance.testRowSet();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnectionHard method, of class DBConnectionOb.
     */
    @Test
    public void testGetConnectionHard() {
        System.out.println("getConnectionHard");
        DBConnectionOb instance = null;
        Connection expResult = null;
        Connection result = instance.getConnectionHard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
