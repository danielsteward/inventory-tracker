/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coreinventorytracker;

import java.sql.Connection;

/**
 *
 * @author danst
 */
public class CoreInventoryTracker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean b = "999999".matches("[0-9]+");
        System.out.println(b);
        
        DBConnection conc = new DBConnection();
        Connection c = conc.getConnection();
        System.out.println("this is the main and the connection is " + conc.toString());
        conc.closeConnection(c);
        if(c!= null) {
        	System.out.println("this is in main and the connection doesn't equal null");
        }
        else {
        	System.out.println("This is maina and it loooks like the connection does equal null");
        }
    }
    
}
