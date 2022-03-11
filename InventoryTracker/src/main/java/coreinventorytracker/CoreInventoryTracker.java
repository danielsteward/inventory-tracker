/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coreinventorytracker;

import inventoryutilities.DBConnectionOb;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tracker.facade.TrackerFacade;

/**
 *
 * @author danst
 */
public class CoreInventoryTracker {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
//        DBConnectionOb connOb = DBConnectionOb.getInstance();
//        Connection c = connOb.getConnection();
//        System.out.println(c.toString());
//        try {
//            System.out.println("Closing Connection");
//            c.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(CoreInventoryTracker.class.getName()).log(Level.SEVERE, null, ex);
//        }
        TrackerFacade tf = new TrackerFacade();
//        List<PlantUnit> pl = tf.getPuCollection("SELECT * FROM mainTable");
//        //pl.stream().forEach((crunchifyTemp) -> System.out.println(crunchifyTemp));
//        System.out.println(pl.toString());

          PlantUnit pu1 = new PlantUnit(1,"Ivy","FloratnisIverius","yellow",
                  PlantUnitStatus.AVAILABLEFROMSUPPLIER,5,PlantUnitSize.ELEVENTOFIFTEEN,AllocatedToCustomer.NO,"JG Smith","ASAP");
          tf.writeUnitJson(pu1);
        
//        ResultSet rs = connOb.getResultSet("SELECT * FROM mainTable WHERE commonName = 'ivy'");
//        while(rs.next()){
//            System.out.println(rs.getString(2));
        
    }
        
}
