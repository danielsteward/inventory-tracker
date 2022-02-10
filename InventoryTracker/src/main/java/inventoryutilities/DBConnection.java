package inventoryutilities;


import java.sql.Connection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danst
 */
public class DBConnection {
    
    
    
    
    public DBConnection(String propertiesFileName){
    super();
    this.setProperties(propertiesFileName);
    
}
 
    private void setProperties(String fileName){
        //do the rest,,,,
    }
    
    public Connection getConnection(){
        Connection conn = null;
        //do the rest......
        
        
        return conn;
    }
    
    
    
    
    
    
}
