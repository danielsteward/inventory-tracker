/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package coreinventorytracker;

/**
 *
 * @author danst
 */
public enum PlantUnitStatus {
    UNAVAILABLE ("Unavailable"), AVAILABLEFROMSUPPLIER("Available From Supplier"), ORDERED("Ordered"), WAREHOUSE("Warehouse"), INTRANSIT("In transit"),
    WITHCLIENT("With client"), OTHER("Other");
    private final String statusString;
    
    PlantUnitStatus(String statusString){
        this.statusString = statusString;
    }
    
    public String getStatusString(){
        return this.statusString;
    }
    
    @Override
    public String toString(){
        return statusString;
    }
    
}
