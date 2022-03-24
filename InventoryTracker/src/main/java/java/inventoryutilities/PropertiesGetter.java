/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryutilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danst
 */
public class PropertiesGetter {
    
    private static Properties props;
    
    public static Properties getProps(String fileName) {
        
        try {
            setProperties(fileName);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesGetter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return props;
    }
    
    //constructor???

    private static void setProperties(String fileName) throws FileNotFoundException, IOException {
        props = new Properties();
        FileInputStream fis = new FileInputStream(fileName);
        props.loadFromXML(fis);
    }   
    
}
