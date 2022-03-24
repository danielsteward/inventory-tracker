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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date.*;

/**
 * A unit DAO allows us to isolate the application/business layer from the persistence layer
 * @author danst
 */
public class PlantUnitDAO {

    private final DBConnectionOb connOb;

    public PlantUnitDAO(DBConnectionOb connObArg) {
        this.connOb = connObArg;
    }


    /**
     * Returns an ArrayList of PlantUnits from DB.
     * The attribute timeOfLastEdit is converted to a String to allow writing to and from json easier.
     * @param sqlArg
     * @return
     * @throws SQLException 
     */
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
            pUnit.setPlantByDate(rs.getString("plantByDate"));
            String timeOfLtEdit = rs.getTimestamp("timeOfLastEdit").toString();
            pUnit.setTimeOfLastEdit(timeOfLtEdit);
            pUnits.add(pUnit);
        }
        connOb.closeConnection();
        return pUnits;
    }
    
    /**
     * Returns an ArrayList of PlantUnits from DB without the unit no.
     * The attribute timeOfLastEdit is converted to a String to allow writing to and from json easier.
     * @param sqlArg
     * @return
     * @throws SQLException 
     */
    public List<PlantUnit> getPunitListSansIdNo(String sqlArg) throws SQLException {
        List<PlantUnit> pUnits = new ArrayList<>();
        ResultSet rs = connOb.getResultSet(sqlArg);

        while (rs.next()) {
            PlantUnit pUnit = new PlantUnit();
            pUnit.setCommonName(rs.getString("commonName"));
            pUnit.setLatinName(rs.getString("latinName"));
            pUnit.setFlowerColour(rs.getString("flowerColour"));
            pUnit.setPlantUnitStatus(PlantUnitStatus.valueOf(rs.getString("plantUnitStatus")));
            pUnit.setNumberPlantsInUnit(rs.getInt("numberPlantsInUnit"));
            pUnit.setUnitSize(PlantUnitSize.valueOf(rs.getString("unitSize")));
            pUnit.setCustomerAllocation(AllocatedToCustomer.valueOf(rs.getString("customerAllocation")));
            pUnit.setCustomerName(rs.getString("customerName"));
            pUnit.setPlantByDate(rs.getString("plantByDate"));
            String timeOfLtEdit = rs.getTimestamp("timeOfLastEdit").toString();
            pUnit.setTimeOfLastEdit(timeOfLtEdit);
            pUnits.add(pUnit);
        }

        return pUnits;
    }
    /**
     * Creates a single unit from the DB.
     * @param sqlArg
     * @return
     * @throws SQLException 
     */
    public PlantUnit getSingleEntry(String sqlArg) throws SQLException{
        PlantUnit pu = new PlantUnit();
        List<PlantUnit> puList = this.getPunitList(sqlArg);
        pu = puList.get(0);
        return pu;
    }
    
    /**
     * Returns a RS with a single unit from the DB in the form of a ResultSet.
     */
    public ResultSet getUnitRsFromDB(String sqlArg) throws SQLException {
        ResultSet rs = connOb.getResultSet(sqlArg);
        return rs;
    }
    
    /**
     * Inserts a Collection (could be a single unit) into DB.
     * @param puList
     * @throws SQLException 
     */
        public void insertUnitsToDB(List<PlantUnit> puList) throws SQLException{
    String sqlInsert = "INSERT INTO mainTable (commonName,latinName,flowerColour,plantUnitStatus,numberPlantsInUnit,"
            + "unitSize,customerAllocation,customerName,plantByDate) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connOb.getConnection().prepareStatement(sqlInsert)) {// Statement does not need closing if in try-with-resouces.
            //puList.stream().forEach((tempPu) -> System.out.println(tempPu));
            for(PlantUnit pu : puList){
            pstmt.setString(1, pu.getCommonName());
            pstmt.setString(2, pu.getLatinName());
            pstmt.setString(3, pu.getFlowerColour());
            pstmt.setString(4, pu.getPlantUnitStatus().name());
            pstmt.setInt(5, pu.getNumberPlantsInUnit());
            pstmt.setString(6, pu.getUnitSize().name());
            pstmt.setString(7, pu.getCustomerAllocation().name());
            pstmt.setString(8, pu.getCustomerName());
            pstmt.setString(9, pu.getPlantByDate());
            pstmt.addBatch();
            }
            pstmt.executeBatch();
            
        } catch (SQLException ex) {
            Logger.getLogger(PlantUnitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(connOb.getConnection().toString());
        // Close Connection which then closes PreparedStatement and any ResultSets etc.
        connOb.closeConnection();
    }

    /**
     * Insert a single unit into DB. Uses a PreparedStatement.
     *
     * @param pu
     * @throws SQLException
     */
    public void insertUnit(PlantUnit pu) throws SQLException {
        String sqlInsert = "INSERT INTO mainTable (commonName,latinName,flowerColour,plantUnitStatus,numberPlantsInUnit,"
                + "unitSize,customerAllocation,customerName,plantByDate) VALUES (?,?,?,?,?,?,?,?,?)";
        try ( PreparedStatement pstmt = connOb.getConnection().prepareStatement(sqlInsert)) {// Statement does not need closing if in try-with-resouces.
            System.out.println(connOb.getConnection().toString());
            pstmt.setString(1, pu.getCommonName());
            pstmt.setString(2, pu.getLatinName());
            pstmt.setString(3, pu.getFlowerColour());
            pstmt.setString(4, pu.getPlantUnitStatus().name());
            pstmt.setInt(5, pu.getNumberPlantsInUnit());
            pstmt.setString(6, pu.getUnitSize().name());
            pstmt.setString(7, pu.getCustomerAllocation().name());
            pstmt.setString(8, pu.getCustomerName());
            pstmt.setString(9, pu.getPlantByDate());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PlantUnitDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Close Connection which then closes PreparedStatement and any ResultSets etc.
        connOb.closeConnection();
    }
    
    /**
     * Edit an entry in the DB if Timestamps are equal.
     */
    public boolean editDBEntry(int unitNo, String theField, String changedValue, String updateLastofUnit) throws SQLException{
        // NEEDS A RETURN SUCCESS FAILURE SYSTEM.
        boolean editSuccess = false;
        Connection conn = connOb.getConnection();// Get a Connection for the check and for the update to save overhead.
        Statement stmt = conn.createStatement();
        if(this.compareTimestamps(unitNo, stmt, updateLastofUnit)) {// if(the Timestamp of the unit != the newly got Timestamp then reject edit.
        if(! theField.equals("noPlantsInUnit")){
            this.editStringEntry(conn, unitNo, theField, changedValue);
            editSuccess = true;
    } else{
            this.editIntEntry(conn, unitNo, theField, changedValue);
            editSuccess = true;
        }
        // TODO If the edit was not a success...
        }
        conn.close();
        System.out.println("bool editSuccess is " + editSuccess);
        return editSuccess;// If success then return message. Else error message.
    }
    
    /**
     * Edits a String entry in the DB.
     */
    private void editStringEntry(Connection conn, int unitNo, String theField, String changedValue) throws SQLException {
        String unitNoSt = String.valueOf(unitNo);
        String updateString = "update mainTable set " + theField + " = ? where unitNo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateString)) {
            pstmt.setString(2, unitNoSt);
            pstmt.setString(1, changedValue);
            int editResult = pstmt.executeUpdate();
            System.out.println("int editResult is " + editResult);
        }
    }
    
    /**
     * Edits an int entry in the DB.
     */
    private void editIntEntry(Connection conn, int unitNo, String theField, String changedValue) throws SQLException{
        String unitNoSt = String.valueOf(unitNo);
        String updateString = "update mainTable set "+theField+" = ? where unitNo = ?";
        try (PreparedStatement pstmt = connOb.getConnection().prepareStatement(updateString)){
            pstmt.setString(2,unitNoSt);
            pstmt.setInt(1, Integer.valueOf(changedValue));
            int editResult = pstmt.executeUpdate();
            System.out.println("int editResult is " + editResult);
        }
    }
        
    
    /**
     * Compared the Timestamp of the unit that is to be edited and the current Timestamp.
     * If they are not equal then return false. Otherwise return true.
     * @param unitNo
     * @param stmt
     * @param updateLastofUnit
     * @return
     * @throws SQLException 
     */
    private boolean compareTimestamps(int unitNo, Statement stmt, String updateLastofUnit) throws SQLException{
        boolean b = false;
        Timestamp lastUpdateOfEditUnit = Timestamp.valueOf(updateLastofUnit);
        String sqlQuery = "Select timeOfLastEdit FROM mainTable WHERE unitNo = " + unitNo;
        ResultSet rs = this.getUnitRsFromDB(sqlQuery);
        rs.next();
        Timestamp tsRealTime = rs.getTimestamp("timeOfLastEdit");// get the timestamp from result set.
        System.out.println("This is the realtime Timestamp " + tsRealTime.toString());
        System.out.println("This is the old Timestamp " + updateLastofUnit);
        if(tsRealTime.equals(lastUpdateOfEditUnit)){
            b = true;
        }
        return b;
    }

}// End of class.
