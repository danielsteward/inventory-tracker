/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tracker.facade;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import coreinventorytracker.AllocatedToCustomer;
import coreinventorytracker.DataController;
import coreinventorytracker.PlantUnit;
import coreinventorytracker.PlantUnitSize;
import coreinventorytracker.PlantUnitStatus;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danst
 */
public class TrackerFacade {

    private DataController dc;
    private JsonFactory jFactory;
    private JsonGenerator jg;
    private JsonParser jp;
    
    // InputStream input = getClass().getResourceAsStream(propsFileName)

    public TrackerFacade() {
        this.dc = new DataController();
        //jFactory = JsonFactory.builder().enable(JsonReadFeature.ALLOW_JAVA_COMMENTS).build();
        jFactory = new JsonFactory();
        try {
            jg = jFactory.createGenerator(new File("result.json"), JsonEncoding.UTF8);
            //jp = jFactory.createParser(new File("input.json"));
        } catch (IOException ex) {
            Logger.getLogger(TrackerFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Write a single plant unit to the DB
    public void insertUnitToDB(PlantUnit pu){
        // TODO Get this from method that creates plant objects from json.
        pu = new PlantUnit("Green Tea","teaus greenus","vert",PlantUnitStatus.AVAILABLEFROMSUPPLIER,5,
                PlantUnitSize.TWENTYONETOTHIRTY,AllocatedToCustomer.YES,"J Ballard","When spring comes");
        
    }
        // String commonName, String latinName, String flowerColour, PlantUnitStatus plantUnitStatus,
    // int numberPlantsInUnit, PlantUnitSize unitSize, AllocatedToCustomer customerAllocation,
    // String customerName, String plantByDate)
     // Writes a single PlantUnit to the Json file.
    public void writeUnitJson(PlantUnit pu){
        try {
            jg.writeStartObject();
            jg.writeNumberField("unitNo", pu.getUnitNo());
            jg.writeStringField("commonName", pu.getCommonName());
            jg.writeStringField("latinName", pu.getLatinName());
            jg.writeStringField("flowerColour", pu.getFlowerColour());
            jg.writeStringField("plantUnitStatus", pu.getPlantUnitStatusEnum().toString());
            jg.writeNumberField("numberPlantsInUnit", pu.getNumberPlantsInUnit());
            jg.writeStringField("customerAllocation", pu.getCustomerAllocationEnum().toString());
            jg.writeStringField("customerName", pu.getCustomerName());
            jg.writeStringField("plantByDate", pu.getPlantByDate());
            jg.writeEndObject();
            jg.close();
        } catch (IOException ex) {
            Logger.getLogger(TrackerFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readJson() throws IOException{
        // Create a plant unit.
        // Set pu attributes from json
        // Put pu into Collection
        // Loop back.
        
    }
    

    public List getPuCollection(String sqlArg) {
        List<PlantUnit> puList = new ArrayList<>();
        puList = dc.getPuList(sqlArg);
        return puList;
    }
    
}
