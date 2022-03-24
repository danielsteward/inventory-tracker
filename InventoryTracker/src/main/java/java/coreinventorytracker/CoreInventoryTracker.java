/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coreinventorytracker;

import inventoryutilities.DBConnectionOb;
import java.io.IOException;
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
    public static void main(String[] args) throws SQLException, IOException {
//        "Blue Bell","BelliusEndius","Red and yellow",""UNAVAILABLE",
//        998,"TWENTYONETOTHIRTY","YES","Ivan the Terrible","When ready"
//        PlantUnit puOne = new PlantUnit("Blue Bell", "BelliusEndius", "Red and yellow", PlantUnitStatus.AVAILABLEFROMSUPPLIER,
//                998, PlantUnitSize.ONETOFIVE, AllocatedToCustomer.UNKNOWN, "Ivan the Terrible", "When ready");
//"2022-03-18 11:13:50.0"
//2022-03-20 08:03:00
        DBConnectionOb connOb = DBConnectionOb.getInstance();
        PlantUnitDAO puDao = new PlantUnitDAO(connOb);
        TrackerFacade tf = new TrackerFacade();
        List<PlantUnit> puList = puDao.getPunitList("SELECT * FROM mainTable where unitNo = 2");
        tf.writeJson(puList);
        
//        puDao.editDBEntry(18, "numberPlantsInUnit", "222");
//      editDBEntry(int unitNo, String theAttribute, String changedValue, String updateLastofUnit) 
//        boolean b = puDao.editDBEntry(19, "numberPlantsInUnit", "121215", "2022-03-20 07:57:18");
//        System.out.println("The result of the edit is "+b);

//
//        TrackerFacade tf = new TrackerFacade();
//        tf.insertUnitToDB(puOne);
//        List<PlantUnit> puList = tf.getPuList("SELECT * FROM mainTable");
//        puList.stream().forEach((tempPu) -> System.out.println(tempPu));
//        tf.writeJson(puList);
        //System.out.println(puList.toString());
//        PlantUnit p = tf.jsonToUnitObject();
//        System.out.println("Plant from json is " + p.toString());
//        tf.insertUnitsToDB(puList);
//          PlantUnit pu1 = new PlantUnit(1,"zzzz","FloratnisIverius","yellow",
//                  PlantUnitStatus.AVAILABLEFROMSUPPLIER,5,PlantUnitSize.ELEVENTOFIFTEEN,AllocatedToCustomer.NO,"JG Smith","ASAP");
//          tf.writeJson(puList);


            
    }
        
}
