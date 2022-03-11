/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coreinventorytracker;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Represents a single plant or collection of plants each of which forms a single unit.
 * @author danst
 */
public class PlantUnit {


    
    private int unitNo; // not allocated until insertion into database. Primary key.
    private String commonName;
    private String latinName;
    private String flowerColour;
    private PlantUnitStatus plantUnitStatus;
    private int numberPlantsInUnit;
    private PlantUnitSize unitSize;
    private AllocatedToCustomer customerAllocation;
    private String customerName;
    private String plantByDate;
    
    public PlantUnit(){
        
    }
    
    public PlantUnit(String commonName, String latinName, String flowerColour, PlantUnitStatus plantUnitStatus,
            int numberPlantsInUnit, PlantUnitSize unitSize, AllocatedToCustomer customerAllocation, String customerName, String plantByDate) {
        this.commonName = commonName;
        this.latinName = latinName;
        this.flowerColour = flowerColour;
        this.plantUnitStatus = plantUnitStatus;
        this.numberPlantsInUnit = numberPlantsInUnit;
        this.unitSize = unitSize;
        this.customerAllocation = customerAllocation;
        this.customerName = customerName;
        this.plantByDate = plantByDate;
    }
    
    public PlantUnit(int index, String commonName, String latinName, String flowerColour, PlantUnitStatus plantUnitStatus,
            int numberPlantsInUnit, PlantUnitSize unitSize, AllocatedToCustomer customerAllocation, String customerName, String plantByDate) {
        this.unitNo = index;
        this.commonName = commonName;
        this.latinName = latinName;
        this.flowerColour = flowerColour;
        this.plantUnitStatus = plantUnitStatus;
        this.numberPlantsInUnit = numberPlantsInUnit;
        this.unitSize = unitSize;
        this.customerAllocation = customerAllocation;
        this.customerName = customerName;
        this.plantByDate = plantByDate;
    }

    public int getUnitNo() {
        return unitNo;
    }
    
    public void setUnitNo(int unitNo){
        this.unitNo = unitNo;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String name) {
        this.commonName = name;
    }
    
    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getFlowerColour() {
        return flowerColour;
    }

    public void setFlowerColour(String flowerColour) {
        this.flowerColour = flowerColour;
    }

    public PlantUnitStatus getPlantUnitStatusEnum() {
        return plantUnitStatus;
    }
    
    public String getPlantUnitStatusString(){
        return plantUnitStatus.getStatusString();
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

    public PlantUnitSize getUnitSizeEnum() {
        return unitSize;
    }
    
    public String getUnitSizeString(){
        return unitSize.getUnitSizeString();
    }

    public void setUnitSize(PlantUnitSize unitSize) {
        this.unitSize = unitSize;
    }

    public AllocatedToCustomer getCustomerAllocationEnum() {
        return customerAllocation;
    }
    
    public String getCustomerAollocationString(){
        return customerAllocation.getAllocatedToCustormerString();
    }

    public void setCustomerAllocation(AllocatedToCustomer customerAllocation) {
        this.customerAllocation = customerAllocation;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPlantByDate() {
        return plantByDate;
    }

    public void setPlantByDate(String plantByDate) {
        this.plantByDate = plantByDate;
    }
    
    @Override
    public String toString() {
        return "PlantUnit{" + "unitNo=" + unitNo + ", commonName=" + commonName + ", latinName=" + latinName + ", flowerColour=" + flowerColour + ", plantUnitStatus=" + plantUnitStatus + ", numberPlantsInUnit=" + numberPlantsInUnit + ", unitSize=" + unitSize + ", customerAllocation=" + customerAllocation + ", customerName=" + customerName + ", plantByDate=" + plantByDate + '}';
    }

        @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.unitNo;
        hash = 79 * hash + Objects.hashCode(this.latinName);
        hash = 79 * hash + Objects.hashCode(this.flowerColour);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PlantUnit other = (PlantUnit) obj;
        if (this.unitNo != other.unitNo) {
            return false;
        }
        if (!Objects.equals(this.latinName, other.latinName)) {
            return false;
        }
        return Objects.equals(this.flowerColour, other.flowerColour);
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

    public int getDetailRecordNo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

}
