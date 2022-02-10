/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coreinventorytracker;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Represents a single plant or collecion of plants each of which forms a single unit.
 * @author danst
 */
public class PlantUnit {
    
    private int unitNo; // not allocated until insertion into database. Primary key.
    private String name;
    private String flowerColour;
    private PlantUnitStatus plantUnitStatus;
    private int numberPlantsInUnit;
    private Enum size;
    private Enum customerAllocation;
    private String customerName;
    private Date plantByDate;

    public int getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(int unitNo) {
        this.unitNo = unitNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlowerColour() {
        return flowerColour;
    }

    public void setFlowerColour(String flowerColour) {
        this.flowerColour = flowerColour;
    }

    public PlantUnitStatus getPlantUnitStatus() {
        return plantUnitStatus;
    }

    public void setPlantUnitStatus(PlantUnitStatus plantUnitStatus) {
        this.plantUnitStatus = plantUnitStatus;
    }

    public int getNumberPlantsInUnit() {
        return numberPlantsInUnit;
    }

    public void setNumberPlantsInUnit(int numberPlantsInUnit) {
        this.numberPlantsInUnit = numberPlantsInUnit;
    }

    public Enum getSize() {
        return size;
    }

    public void setSize(Enum size) {
        this.size = size;
    }

    public Enum getCustomerAllocation() {
        return customerAllocation;
    }

    public void setCustomerAllocation(Enum customerAllocation) {
        this.customerAllocation = customerAllocation;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getPlantByDate() {
        return plantByDate;
    }

    public void setPlantByDate(Date plantByDate) {
        this.plantByDate = plantByDate;
    }

    

    public boolean isDataFormatOK() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void testDataFormatUF() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setDataFormatOK(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getDetailRecordNo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getDateTimeSt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setDateTime(LocalDateTime createLDT) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getPedSerialNo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getErrorMessage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setPedIncUFRecordNo(String serial, String termID, String store, String make, String model, String status, String dtSt, int detailRecordNo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setErrorMessage(String tempErrorMess) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
