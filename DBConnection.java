/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coreinventorytracker;

import inventoryutilities.InvExceptions;
import inventoryutilities.InvUtilities;
import inventoryutilities.PropertiesGetter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danst
 */
public class DBConnection {
    
    public String dbms;
    public String jarFile;
    public String dbName;
    public String username;
    public String password;
    public String urlString;
    private String driver;
    private String serverName;
    private int portNumber;
    private Properties props;
    private InvExceptions utility;
    private static Connection conn;

    public DBConnection() {
        //super();
        //utility = new PEDExceptions();
        conn = null;
        //set connection properties.
        props = PropertiesGetter.getProps("connection_properties.xml"); //catch(FileNotFoundException | IOException | InvalidPropertiesFormatException){
        this.dbms = this.props.getProperty("dbms");
        this.driver = this.props.getProperty("driver");
        this.dbName = this.props.getProperty("database_name");
        this.username = this.props.getProperty("username");
        this.password = this.props.getProperty("password");
        this.serverName = this.props.getProperty("server_name");
        this.portNumber = Integer.parseInt(this.props.getProperty("port_number"));
        System.out.println(dbms+driver+dbName+username+password+serverName+portNumber);
            
        }



    /**
     * Makes a connection with the DB without username and password hard coded as these are
     * in the properties xml document.
     *
     * @throws java.sql.SQLException
     */
    public Connection getConnection() {

        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://mysql.a3computersupport.co.uk/march18ped?"
                    + "user=danst&password=ketchUp1850");
            System.out.println(conn.toString());
        } catch (SQLException ex) {
            System.out.println("Exception in DBConnection.getConnection() is " + ex.getMessage());
            
        } catch (ClassNotFoundException cnf) {

        }
        System.out.println("Successful connection");
        return conn;
    }
    /*
    public Connection getConnection(){
        
        return conn;
    }
*/
    /**
     * Makes a connection with the DB with username and password.
     */
    public Connection getConnection(String username,
            String password) throws SQLException {
        Connection conn = null;
        props.setProperty("user", username);
        props.setProperty("password", password);
        conn
                = DriverManager.getConnection("jdbc:" + this.props.getProperty("dbms") + "://" + this.props.getProperty("server_name")
                        + ":" + Integer.parseInt(this.props.getProperty("port_number")) + "/",
                        props);
        //conn.setCatalog(this.dbName);//Used to connect to a particular table.
        System.out.println("This is in getConnection and the connection is " + conn.toString());
        return conn;
    }

    public void closeConnection(Connection connArg) {
        //System.out.println("Trying to release all open resources ...");

        try {
            if (connArg != null) {
            	System.out.println("connection is not null so trying to close " + connArg.toString());
                connArg.close();
                //connArg = null;
                System.out.println("Connection closed");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle + "this is the exception in closeConnection");//have another look at the printSQLException
        }
        finally {
        	System.out.println("this is the finally but has it closed???/");
        }
    }
    
}
