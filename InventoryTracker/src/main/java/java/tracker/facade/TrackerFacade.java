/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tracker.facade;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private JsonGenerator jsonWriter;
    private JsonParser jsonParser;
    
    // InputStream input = getClass().getResourceAsStream(propsFileName)

    public TrackerFacade() {
        this.dc = new DataController();
        //jFactory = JsonFactory.builder().enable(JsonReadFeature.ALLOW_JAVA_COMMENTS).build();
        jFactory = new JsonFactory();
        try {
            jsonWriter = jFactory.createGenerator(new File("fromJava.json"), JsonEncoding.UTF8);
            jsonParser = jFactory.createParser(new File("toJava.json"));
        } catch (IOException ex) {
            Logger.getLogger(TrackerFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertUnitsToDB(List<PlantUnit> puList){
        dc.insertUnitsToDB(puList);
    }
    
    //Write a single plant unit to the DB
    public void insertUnitToDB(PlantUnit pu) throws SQLException{
        dc.insertUnitToDB(pu);
    }

    public List<PlantUnit> getPunitListSansIdNo(String sqlArg) throws SQLException {
        List<PlantUnit> puList = dc.getPunitListSansIdNo(sqlArg);
        return puList;
    }

    /**
     * Writes a Collection of PlantUnit(s) to the Json file.
     * @param puList
     * @throws IOException 
     */
    public void writeJson(List<PlantUnit> puList) throws IOException {
        for (PlantUnit pu : puList) {
            jsonWriter.writeStartObject();
            jsonWriter.writeNumberField("unitNo", pu.getUnitNo());
            jsonWriter.writeStringField("commonName", pu.getCommonName());
            jsonWriter.writeStringField("latinName", pu.getLatinName());
            jsonWriter.writeStringField("flowerColour", pu.getFlowerColour());
            jsonWriter.writeStringField("plantUnitStatus", pu.getPlantUnitStatus().name());
            jsonWriter.writeNumberField("numberPlantsInUnit", pu.getNumberPlantsInUnit());
            jsonWriter.writeStringField("customerAllocation", pu.getCustomerAllocation().name());
            jsonWriter.writeStringField("customerName", pu.getCustomerName());
            jsonWriter.writeStringField("plantByDate", pu.getPlantByDate());
            jsonWriter.writeStringField("timeOfLastEdit", pu.getTimeOfLastEdit());
            jsonWriter.writeEndObject();
        }

        jsonWriter.close();

    }
    
    public PlantUnit jsonToUnitObject() throws IOException {
        PlantUnit pu = new PlantUnit();
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("toJava.json");
        pu = objectMapper.readValue(file, PlantUnit.class);
        return pu;
    }

    /**
     * This can read from a Json file but not much use as it is difficult to create Objects.
     * The iteration needs if else if etc etc to identify the different fields.
     * Using the Object Mapper is far better. More overhead but much more efficient coding.
     * @return
     * @throws IOException 
     */
    public PlantUnit readJson() throws IOException {
        PlantUnit pu = new PlantUnit();
        // Sanity check: verify that we got "Json Object":
        if (jsonParser.nextToken() != JsonToken.START_OBJECT) {
            throw new IOException("Expected data to start with an Object");
        }
        // Iterate over object fields:
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {// WORKING OUT HOW TO ITERATE THROUGH THE JSON
            pu.setCommonName(jsonParser.getText());
            // Let's move to value
            jsonParser.nextToken();
            jsonParser.close(); // important to close both parser and underlying File reader
    }
        return pu;
    }
    
/**
 * 
 * @param sqlArg
 * @return 
 */
    public List getPuList(String sqlArg) {
        List<PlantUnit> puList = new ArrayList<>();
        puList = dc.getPuList(sqlArg);
        return puList;
    }
    
}
