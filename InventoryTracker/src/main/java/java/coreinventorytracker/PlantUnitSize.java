/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package coreinventorytracker;

/**
 *
 * @author danst
 */
public enum PlantUnitSize {
    ONETOFIVE("1 to 5"), SIXTOTEN("6 to 10"), ELEVENTOFIFTEEN("11 to 15"), SIXTEENTOTWENTY("16 to 20"), TWENTYONETOTHIRTY("21 to 30"), THIRTYONEPLUS("31 plus"), TREE("Tree");
    private final String unitSizeString;
    PlantUnitSize(String size){
        this.unitSizeString = size;
    }
    
    public String getUnitSizeString(){
        return this.unitSizeString;
    }
    
    @Override
    public String toString(){
        return unitSizeString;
    }
    
}
