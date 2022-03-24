/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package coreinventorytracker;

/**
 *
 * @author danst
 */
public enum AllocatedToCustomer {
    
    YES("Yes"), NO("No"), UNKNOWN("Unknown");
    private final String allocatedToCustomerString;
    
    AllocatedToCustomer(String allocated){
        this.allocatedToCustomerString = allocated;
    }
    
    public String getAllocatedToCustormerString(){
        return this.allocatedToCustomerString;
    }
    
    @Override
    public String toString(){
        return allocatedToCustomerString;
    }
}
