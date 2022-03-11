/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coreinventorytracker;

import inventoryutilities.DBConnectionOb;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danst
 */
public class DataController {
    PlantUnitDAO puDAO;
    DBConnectionOb connOb;
    
    public DataController(){
        this.connOb = DBConnectionOb.getInstance();
        this.puDAO = new PlantUnitDAO(connOb);
    }
    
    public List getPuList(String sqlArg){
        List<PlantUnit> puList = new ArrayList<>();
        try {
            puList = puDAO.getPunitList(sqlArg);
        } catch (SQLException ex) {
            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return puList;
    }

    //Write a single plant unit to the DB
    public void insertUnitToDB(PlantUnit pu) throws SQLException{
        puDAO.insertUnit(pu);
        
    }
    

    
    
}
