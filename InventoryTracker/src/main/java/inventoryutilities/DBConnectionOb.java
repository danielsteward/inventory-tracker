package inventoryutilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

/*
 * Singleton class providing a Conncection to the DB.
 * The Connection object is a member of the class.
 */
/**
 *
 * @author danst
 */
public class DBConnectionOb {

    private Properties props; // Using Java Properties. Could use JSON or XML but this works here.
    private Connection conn; // Limit to a single Connection to keep it simple.
    private static DBConnectionOb instance;
    private final String dbms;
    private final String driver;
    private final String dbName;
    private final String username;
    private final String password;
    private final String serverName;
    private final int portNumber;
    private Statement stmt;

    //private constructor for the Singlton
    private DBConnectionOb() {
        try {
            this.fetchProperties("/connectionProperites.properties");
        } catch (IOException ex) {
            Logger.getLogger(DBConnectionOb.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dbms = this.props.getProperty("dbms");
        this.driver = this.props.getProperty("driver");
        this.dbName = this.props.getProperty("database_name");
        this.username = this.props.getProperty("username");
        this.password = this.props.getProperty("password");
        this.serverName = this.props.getProperty("server_name");
        this.portNumber = Integer.parseInt(this.props.getProperty("port_number"));
        
        System.out.println("props are " + props.toString());
//        System.out.println("Connection is "+ conn.toString());
    }

    public static DBConnectionOb getInstance() {
        if (instance == null) {
            System.out.println("Creating new instance.");
            instance = new DBConnectionOb();
        } else try {
            if (instance.getConnection().isClosed()) {
                instance = new DBConnectionOb();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionOb.class.getName()).log(Level.SEVERE, null, ex);
        }

        return instance;
    }
    
    // Returns a Statement.
    public Statement getStatement(){
        this.conn = getConnection();
        try {
            this.stmt = conn.createStatement();// This Statement is closed in PlantUnitDAO.insertUnit(PlantUnit pu) etc but does it need sorting here?
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionOb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.stmt;
    }
    
    // Returns
    
    public ResultSet getResultSet(String sqlQuery) throws SQLException{
        this.conn = getConnection();
        PreparedStatement statement = conn.prepareStatement(sqlQuery);
        ResultSet rs = statement.executeQuery(sqlQuery);
        return rs;
    }

    public Connection getConnection() {// Could be private????????
        //Connection conn = null;
        // Close the Connncection using try with resources but it needs to be returned and used so maybe do it when called?
        try {
            this.conn = DriverManager.getConnection("jdbc:" + dbms + "://" + serverName + ":" + portNumber + "/" + dbName,
                    username, password);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionOb.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("This Connection in DBConnOb.getConnection() is "+ this.conn.toString());
        return conn;
    }
    
    public void closeConnection() throws SQLException{
        System.out.println("Releasing all open resources ...");
    try {
      if (conn != null) {
        conn.close();
        conn = null;
      }
    } catch (SQLException ex) {
      Logger.getLogger(DBConnectionOb.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    public static void closeConnection(Connection connArg) {
    System.out.println("Releasing all open resources ...");
    try {
      if (connArg != null) {
        connArg.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(DBConnectionOb.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

    private void fetchProperties(String propsFileName) throws IOException {
        try ( InputStream input = getClass().getResourceAsStream(propsFileName)) {
            this.props = new Properties();
            props.load(input);
        } catch (Exception ex) {
            Logger.getLogger(DBConnectionOb.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Properties setting went wrong in fetchProperites.");
        }
    }
    
    public void testRowSet() throws SQLException{
        RowSetFactory factory = RowSetProvider.newFactory();
    }

    // For testing.
    public Connection getConnectionHard() {
        //Connection conn = null;
        int portNo = 3306;
        try {

            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn
                    = DriverManager.getConnection("jdbc:mysql://gandsdb.getandspend.com:" + portNo + "/devicetracker_01",
                            "danst", "ketchUp1850");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionOb.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conn;
    }
    


}
