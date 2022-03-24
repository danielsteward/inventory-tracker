/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package updatefile;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import inventoryutilities.DateTimeUtil;
import inventoryutilities.InvUtilities;
import inventoryutilities.PropertiesGetter;

/**
 *
 * @author devadmin
 */
public class UpdateFileReport
{
    private BufferedWriter outputStream;
    //DEVICEUtilities pu;
    private Files file;
    private PrintWriter pw;
    private Path reportName;
    private Path reportDestination;
    private Path reportDestinationFull;
    private Properties props;
    private Logger logger;
    private FileHandler fh;
    /**
     * Constructor creates new file including the update file sequence number
     * in file name.
     */
    public UpdateFileReport(InvUtilities puArg)// throws IOException, FileNotFoundException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        try {
            LocalDateTime timeNow = LocalDateTime.now();
            String timeNowString = DateTimeUtil.stripNonDigits(timeNow.toString());
            logger = Logger.getLogger("UFLog");
            fh = new FileHandler(timeNowString + "_ufLogFileReport.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            InvUtilities pu = puArg;
            props = PropertiesGetter.getProps("reports_destination_paths.xml");
            reportDestination = Paths.get(props.getProperty("update_file_reports_destination"));
            //String fileName = pu.getUpdateFileNameAndPath();
            String s = ("./updateFileReport" + pu.getSeqNoFromHeader() + pu.getSubmitterFromHeader()
                    + ".csv");//creates name for update file report.
            outputStream = new BufferedWriter(new FileWriter(s));
            pw = new PrintWriter(outputStream, true);
            reportName = Paths.get(s);//creates new file for report. If the file does not exist, it is created. If the file exists, it is opened for appending.
            //logger.info("In ufReport constructor. The uf is called "+ reportName.toString());
            
        } catch (IOException ex) {
            StackTraceElement[] ste = ex.getStackTrace();
            logger.log(Level.SEVERE, "exception in UpdateFileReport constructor is {0}", ste);
        }
    }
    
    /**
     * Writes text to file.
     */
    public void writeToFile(String st) throws IOException
    {
        pw.write(st);
        pw.println();
        pw.flush();
        //pw.close();
    }
    public void closeRR(){
        pw.close();
    }
    
    public void moveUFReport() {
        try{
         
        reportDestinationFull = reportDestination.resolve(reportName);
        //logger.info("IN moveUFReprt and the report destination is "+reportDestinationFull.toString());
        Files.move(reportName, reportDestinationFull, REPLACE_EXISTING);
        //logger.info("Moving "+reportName.toString() +" to " + reportDestinationFull.toString() );
        }
        catch(Exception ex){
            StackTraceElement[] ste = ex.getStackTrace();
            logger.log(Level.SEVERE, "catch in moveUFReport stack trace is  {0}", ste);
        }
    }
}
