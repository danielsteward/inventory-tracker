/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coreinventorytracker;

import inventoryutilities.DBConnectionOb;
import inventoryutilities.DateTimeUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A unit DAO allows us to isolate the application/business layer from the persistence layer
 * @author danst
 */
public class PlantUnitDAO {

    private DBConnectionOb connOb;

    public PlantUnitDAO(DBConnectionOb connObArg) {
        this.connOb = connObArg;
    }
// Returns an ArrayList of PlantUnits from DB.
    public List<PlantUnit> getPunitList(String sqlArg) throws SQLException {
        List<PlantUnit> pUnits = new ArrayList<>();
        ResultSet rs = connOb.getResultSet(sqlArg);

        while (rs.next()) {
            PlantUnit pUnit = new PlantUnit();
            pUnit.setUnitNo(rs.getInt("unitNo"));
            pUnit.setCommonName(rs.getString("commonName"));
            pUnit.setLatinName(rs.getString("latinName"));
            pUnit.setFlowerColour(rs.getString("flowerColour"));
            pUnit.setPlantUnitStatus(PlantUnitStatus.valueOf(rs.getString("plantUnitStatus")));
            pUnit.setNumberPlantsInUnit(rs.getInt("numberPlantsInUnit"));
            pUnit.setUnitSize(PlantUnitSize.valueOf(rs.getString("unitSize")));
            pUnit.setCustomerAllocation(AllocatedToCustomer.valueOf(rs.getString("customerAllocation")));
            pUnit.setCustomerName(rs.getString("customerName"));
            String plantBy  = rs.getString("plantByDate");
            //LocalDateTime plantByDate = DateTimeUtil.createLDT(plantBy);
            pUnit.setPlantByDate(plantBy);// Need something to use as unknown if using LDT.
            pUnits.add(pUnit);
        }

        return pUnits;
    }
    
    // Insert a single unit into DB.
    public void insertUnit(PlantUnit pu) throws SQLException{
        //PlantUnit pu = pu;
        Statement stmt = connOb.getStatement();
        stmt.executeUpdate("insert into mainTable " +
                         "values(" + pu.getCommonName() + "," + pu.getLatinName()+ "," + pu.getFlowerColour()+ 
                "," + pu.getPlantUnitStatusString()+ "," + pu.getNumberPlantsInUnit()+ "," + pu.getUnitSizeString()+ 
                "," + pu.getCustomerAollocationString()+ "," + pu.getCustomerName()+ "," + pu.getPlantByDate());
        stmt.close();
    }
    
}
