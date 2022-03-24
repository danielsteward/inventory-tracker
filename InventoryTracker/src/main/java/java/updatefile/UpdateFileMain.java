/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package updatefile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danst
 */
public class UpdateFileMain {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Path p = PathSupplier.PATHNAMESINSTANCE.getDirIn();
        
        System.out.println(p.toString());
        
        Polling po = Polling.POLLINGOB;
        
        po.moveNewUpdateFiles();

        

    }
    
}
