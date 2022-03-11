/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package updatefile;

import coreinventorytracker.PlantUnit;
import coreinventorytracker.PlantUnit;
import coreinventorytracker.PlantUnitSize;
import coreinventorytracker.PlantUnitStatus;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.SQLException;
import java.time.LocalDateTime;
import inventoryutilities.InvUtilities;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import inventoryutilities.DateTimeUtil;

/**
 *
 * @author devadmin
 The class that processes the incoming update files.
 Has a PEDUtiltities, DEVICE and UpdateFileReport instances.
 */
public class UpdateFileProcessor
{
    private UpdateFileReport rr;
    private PlantUnit plantUnit;
    private InvUtilities utils;
    private String updateFileHeaderDate;
    private Path archivePathName;
    private Path processingPathName;
    private Logger logger;
    private FileHandler fh;
    private String exceptionMessage;
    private String resultMessage;
    private boolean detailRecordNumberOK;
    private String tempSerialNo;
    private String tempDRN;
    private String tempErrorMess;

    public UpdateFileProcessor(InvUtilities pu) throws IOException, SQLException, FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        plantUnit = new PlantUnit();
        this.utils = pu;
        InvUtilities.setUfOrGui(true);// sets the static variable of DEVICEUtilities to indicate the amend has come from a UF.
        LocalDateTime timeNow = LocalDateTime.now();
        String timeNowString = DateTimeUtil.stripNonDigits(timeNow.toString());
        logger = Logger.getLogger("ufLog");
        fh = new FileHandler(timeNowString + "_ufprocessorLogFile.log");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.info("Started UFProcessor");
    }
    /**
     * Checks the integrity of the file and if ok initiates runUpdateFile(). Otherwise reports on the problem.
     */
    public void updateFileIntegrityCheck()    {
        resultMessage = " Unknown error.";
        exceptionMessage = null;
        boolean errorBeforeUFChecks = true;
         rr = new UpdateFileReport(utils);//creates an initial version of the report that will be overwritten unless an exception is thrown.
         try{
        rr.writeToFile("There was an unknown problem with the update file. Please check and resubmit.");
        rr.closeRR();
        rr.moveUFReport();
         }catch(Exception ex){
             Logger.getLogger(UpdateFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
             logger.log(Level.SEVERE, "Exception 1 in startUpdateFile was {0}", ex.getMessage());
             rr.closeRR();
         }
        resultMessage = " Unknown error.";
        exceptionMessage = null;
        try {//System.out.println("Starting uf check in updateFileIntegrityCheck");
            if(utils.checkUFSubmitter()){//if the submitter exists.
                errorBeforeUFChecks = false;
                rr = new UpdateFileReport(utils);//overwrites the original. The argument ensures the same instance is being used.
            resultMessage = utils.updateFileCheck();//this sets the boolean ufok in PU indicating if the uf integrity is ok or not.
            boolean ufErrorYN = utils.isUfok();
            if (ufErrorYN) {//if the uf integrity checks out ok.
                String submitter = utils.getSubmitterFromHeader();
                rr.writeToFile("Update file integrity checks ok for update file from "
                        + submitter + " with sequence number " + utils.getSeqNoFromHeader()+".");
                utils.setCurrentUser(submitter);//sets user name to the submitter.
                //System.out.println("Checks on UF ok - starting runUpdateFile()");
                this.runUpdateFile();
            } else {
                String fileName = utils.getUpdateFileNameAndPath();//prints problems to report.
                //logger.log(Level.INFO, "In the else of startUpdateFile. There was a problem with the file {0}", fileName);
                rr.writeToFile("There was a problem with the file " + fileName + ". " + resultMessage);
                rr.closeRR();
                this.moveToArchive();
                rr.moveUFReport();//System.out.println("Moving unprocessed uf report.");
            }
            }
            else{
                String fileName = utils.getUpdateFileNameAndPath();//prints problems to report.
                rr = new UpdateFileReport(utils);//overwrites the original. The argument ensures the same instance is being used.
                rr.writeToFile("The submitter for update file " + fileName +" cannot be found in the database. Please check and resubmit."); 
            rr.closeRR();
            rr.moveUFReport();
            errorBeforeUFChecks = false;
            }
        }
        catch(IOException ex){
            logger.log(Level.SEVERE, "Exception 2 in startUpdateFile was {0}", ex.getMessage());
                    }
        finally{
            if(errorBeforeUFChecks == true){
                try{
            rr.writeToFile("There was an unknown error or the error was "+exceptionMessage+ " in the update file. Please check."); 
            rr.closeRR();
            rr.moveUFReport();System.out.println("Moving unprocessed uf report.");
                }catch(Exception ex){
                    logger.log(Level.SEVERE, "Exception in finally of startUpdateFile was {0}", ex.getMessage());
                }
            }
        }
    }
    /**
     * Handles the update file reading in and additions/amends to the DB.
     */
    public void runUpdateFile() throws IOException    {
        String serialNo;
        DetailRecordNoControl drnc = new DetailRecordNoControl();
        Path processDir = PathSupplier.PATHNAMESINSTANCE.getDirProcess();
        String fileName = processDir.toString();//UpdateFilePathFeed.getUfStringPathProcessing();
        FileInputStream fs = null;
        BufferedReader br = null;
        int noSuccessfulAmends = 0;
        int noUnsuccessfulAmends = 0;
        int noOfDups = 0;
        try {
            String userName = utils.getSubmitterFromHeader();//gets submitter name from the ufo.
            fs = new FileInputStream(fileName);
            br = new BufferedReader(new InputStreamReader(fs));
            br.readLine();//reads the first line of the uf which is the header so the header is not read in the main while loop.
            int noOfLinesToRead = utils.getUFONoPedEntries();//get the number of PEDs to read. Gets from UFO in utils.
            int count = 0;//to count the lines being read. Starts at 0 so first plantUnit will be line 1. Excludes footer.
            while (count < noOfLinesToRead) {//will not read footer.
                
                String line = br.readLine();
                //create DEVICE with Strings only to check data format.
                PlantUnit thePlant = this.pedFromUpdateFile(line);//create the plantUnit with attributes from update file. Strings only at this point. Basic checks performed.
                if(thePlant.isDataFormatOK()){//Initial foramt check. If the data format is ok...it may have been set false when the DEVICE is created by a problem with the serial no. etc
                thePlant.testDataFormatUF();//call the DEVICE's data format checker.
                if(!utils.checkSerialNoCase(thePlant.getUnitNo())){
                    thePlant.setDataFormatOK(false);}
                }
                if(thePlant.isDataFormatOK()){//if the format of the data is ok replace some String values with objects and continue processing.
                    drnc.setCurrentDRN(thePlant.getDetailRecordNo());
                    drnc.checkDRN();//checks the sequence nos. are incrementing correctly.
                    thePlant.setCommonName("the name");
                    //thePlant.setMake(DEVICEMakeEnum.valueOf(thePlant.getMakeSt()));
                    //thePlant.setModel(DEVICEModelEnum.valueOf(thePlant.getModelSt()));
                    //thePlant.setPedStatus(DEVICEStatusEnum.valueOf(thePlant.getPedStatusSt()));
                    //System.out.println("The Ped should be fully set now as "+thePlant.toString());
                    serialNo = thePlant.getCommonName();
                if (utils.checkPEDFromDB(serialNo) == false) {//if DEVICE new (no record found) add DEVICE to DB.
                    utils.addPEDToDB(thePlant);// System.out.println("adding " + serialNo + " to DB.");
                    rr.writeToFile("Detail record number " + thePlant.getDetailRecordNo() + " with serial no."
                                    + thePlant.getPedSerialNo() + " was added to the DB sucessfully.");
                    noSuccessfulAmends++;
                } else {//amend existing DEVICE details.
                    //gets the date of the DEVICE from the UF and the DB and compares them.
                    //boolean bool = this.checkDates(thePlant.getDateTime(), utils.getPEDDateTimeFromDB(serialNo));
                    boolean bool = true;
                    if (bool == true) {//check dates.
                        if (utils.amendPEDEntry(thePlant) == true) {//writes amends to the DB.
                            noSuccessfulAmends++;
                            rr.writeToFile("Detail record number " + thePlant.getDetailRecordNo() + " with serial no."
                                    + thePlant.getPedSerialNo() + " was processed sucessfully.");
                        }
                        else {
                            rr.writeToFile("Detail record number " + thePlant.getDetailRecordNo() + " with serial no."
                                    + thePlant.getPedSerialNo() + " was a duplicate entry or no data was changed.");
                            noOfDups++;
                        }
                    } else {
                        rr.writeToFile("There was a problem with the date/time for " + thePlant.getPedSerialNo()
                                + ". The detail record number of this PED is " + thePlant.getDetailRecordNo());
                        noUnsuccessfulAmends++;
                    }
                }
                }
                else{
                    //System.out.println("There was a problem with the DEVICE; writing an error message to the report.");
                    rr.writeToFile(thePlant.getErrorMessage());
                    noUnsuccessfulAmends++;
                }
                count++;
                drnc.setLastDRN(thePlant.getDetailRecordNo());
            }
            rr.writeToFile(noSuccessfulAmends + " entries were submitted successfully.");
            rr.writeToFile(noUnsuccessfulAmends + " entries were submitted unsuccessfully.");
            rr.writeToFile(noOfDups + " duplicate/no change entries were included in the file");
            int total = noSuccessfulAmends + noUnsuccessfulAmends + noOfDups;
            rr.writeToFile(total + " entries were processed in total.");
            if(drnc.drnIncOK == false){//if there was a problem with the increment of the detail record numbers.
                rr.writeToFile(drnc.errorMess);
            }
            String newSequenceNo = utils.getSeqNoFromHeader();
            utils.setSeqCurrentUpdate(newSequenceNo, userName);
            //utils.setNewUFDate(utils.getUFDate(), userName);
            //System.out.println("Finished processing the update file " + fileName);
            logger.log(Level.INFO, "Finished processing the update file  {0}", fileName);
        } catch (IOException ex) {
            rr.writeToFile("There was a problem with the Update File directories or with"
                    + " the Update File. A " + ex.getMessage()+ " was thrown in runUpdateFile() for "+ fileName);
            Logger.getLogger(UpdateFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception NullPointerException) {
            rr.writeToFile("There was a problem with"
                    + " the Update File. A " + NullPointerException.getMessage()+ " was thrown in runUpdateFile() for "+ fileName);
            Logger.getLogger(UpdateFileProcessor.class.getName()).log(Level.SEVERE, null, NullPointerException);
        } finally {
            rr.closeRR();
            br.close();
            fs.close();
            this.moveToArchive();//Moves uf to the archive dir.
            rr.moveUFReport();System.out.println("Moving uf report.");
            logger.info("Finishing runUpdateFile for "+ fileName);
            //System.out.println("Finishing runUpdateFile for "+ fileName + " which should be the same as " + UpdateFilePathFeed.getDirPathProcessing() );
        }
    }
    /**
     * Moves processed UF to the archive directory.
     */
    private void moveToArchive(){//get the current Path of the uf getProcessingPathName() and move to the archive Path(getArchivePathName()).
        //logger.log(Level.INFO, "In moveToArchive moving  {0}", UpdateFilePathFeed.getDirPathProcessing());
        try {
            Files.move(PathSupplier.PATHNAMESINSTANCE.getDirArchive(), archivePathName, REPLACE_EXISTING);
        } catch (IOException ex) {
            StackTraceElement[] s = ex.getStackTrace();
            logger.log(Level.SEVERE, "IOEx in moveToArchive {0}", s);
        }
    }
    /**
     * Checks the date of the DEVICE update and the status date from the DB & future dates.
     */
    private boolean checkDates(LocalDateTime dtFromUF, String dtFromDB)// throws IOException, SQLException
    {
        boolean bool = false;
        LocalDateTime ltdPED = dtFromUF;//creates a LocalTimeDate from the update file strings of date and time.
        LocalDateTime ltdDB = DateTimeUtil.createLDT(dtFromDB);//creates a LocalTimeDate from the DB entry for that DEVICE.
        //if the date from the update file is later or the same as that in the DB & not in the future.
        if((ltdPED.isAfter(ltdDB)&&ltdPED.isBefore(LocalDateTime.now()))|| ltdPED.isEqual(ltdDB)){
            bool = true;
        }
        return bool;
    }
/**
     * Creates a DEVICE from the update file; just initialises Strings so the data format can be checked.
     */
    public PlantUnit pedFromUpdateFile(String s) throws FileNotFoundException, IOException
    {
        PlantUnit aPlant = new PlantUnit();
        String dtSt;
        LocalDateTime ldt = null;
        if(this.checkUFentryOK(s)){//if check commas and fields are ok else set dataFormatOK to false and error string.
        try{
                Scanner scanner = new Scanner(s);
                scanner.useDelimiter(",");
                String serial = scanner.next();
                String termID = scanner.next();
                String store = scanner.next();
                String make = scanner.next();
                String model = scanner.next();
                String status = scanner.next();
                String date = scanner.next();
                String time = scanner.next();
                dtSt = date + time;
                int detailRecordNo = Integer.parseInt(scanner.next());
                aPlant.setPedIncUFRecordNo(serial, termID, store, make, model, status, dtSt, detailRecordNo);
                aPlant.setDataFormatOK(true);
            } catch (Exception ex) {
                Logger.getLogger(UpdateFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
                logger.log(Level.SEVERE, "Exception in UFP pedFromUpdateFile was {0}", ex.getMessage());
            }
        } else {
            aPlant.setDataFormatOK(false);
            aPlant.setErrorMessage(tempErrorMess);
        }
        return aPlant;
    }
    /**
     * Counts number of commas and Strings in an uf ped entry line and if the
     * entry ends with a comma. If the checks are ok it returns true otherwise it sets a
     * serial number and a DRN to set a ped with enuf info for the report.
     * @return
     */
    private boolean checkUFentryOK(String s) {
        int noOfCommas = 0;
        boolean b = true;
        tempErrorMess = "There was a problem with the format of a PED entry. ";
        try{
        if (!s.startsWith(",")) {//if the listing has a serail no.
            char[] aChars = s.toCharArray();
            for (char c : aChars) {//counts no. of commas.
                if (c == ',') {
                    noOfCommas++;
                }
                }
                if (noOfCommas != 8 || s.endsWith(",")) {//if the line does not contain enuf commas or ends with a comma.
                    b = false;
                    int positionOfFirstComma = s.indexOf(",");//find the position of the first comma.
                    tempSerialNo = s.substring(0, positionOfFirstComma - 1);//get the serial no from the beginning of the string to the first comma.
                    tempErrorMess += "The system has attempted to identify the serial number and"
                            + " has returned " + tempSerialNo + ". ";
                    if (s.endsWith(",")) {//if the listing has no detail record number.
                        tempErrorMess += "There was no detail record number allocated. ";//gets a DRN for DEVICE object.
                    } else {
                        int posOfLastComma = s.lastIndexOf(",");
                        tempDRN = s.substring(posOfLastComma + 1);//gets a DRN for DEVICE object.
                        if (this.isNumeric(tempDRN)) {//if the string identified is digits.
                            tempErrorMess = tempErrorMess + " The detail record number is " + tempDRN + ". ";
                        } else {
                            tempErrorMess = tempErrorMess + "The detail record number could not be identified. ";
                        }
                    }
                }
            } else if (s.startsWith(",")) {//else if the listing has no serial no.
                b = false;
                if (s.endsWith(",")) {//if the listing has neither serial number nor DRN.
                    tempErrorMess = "There was a listing with neither a serial number or a detail record number. ";//gets a DRN for DEVICE object.
                } else {//gets a DRN to use in reporting.
                    int posOfLastComma = s.lastIndexOf(",");
                    tempDRN = s.substring(posOfLastComma + 1);//gets a DRN for DEVICE object.
                    if (this.isNumeric(tempDRN)) {//if the string identified is digits.
                        tempErrorMess = tempErrorMess + "The PED listing with detail recored number " + tempDRN + " had no serial number. ";
                    } else {
                        tempErrorMess += "The detail record number could not be identified. ";
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UpdateFileProcessor.class.getName()).log(Level.SEVERE, null, ex);
            logger.log(Level.SEVERE, "Exception in checkUFentryOK was {0}", ex.getMessage());
        }
        return b;
    }
    /**
     * Helper method tests if a string is composed of numbers.
     * @return 
     */
    private boolean isNumeric(String nos){
        return (nos.matches("[0-9]+"));
    }
    
    public Path getArchivePathName() {
        return archivePathName;
    }

    public void setArchivePathName(Path archivePathName) {
        this.archivePathName = archivePathName;
    }

    public Path getProcessingPathName() {
        return processingPathName;
    }

    public void setProcessingPathName(Path processingPathName) {
        this.processingPathName = processingPathName;
    }
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
    /**
     * Inner class representing a detail record number problem.
     */
    private class DetailRecordNoControl{
        String errorMess;
        private int lastDRN;//the last DRN that followed the sequence.
        private int currentDRN;
        private int lastDRNaddedToErrorMess;
        boolean drnIncOK;
        
        DetailRecordNoControl(){
            drnIncOK = true;
            lastDRN = 0;
            errorMess = "There was a problem with the increment of the detail record numbers at or near number/s ";
        }
        private void checkDRN(){
            if(!(this.lastDRN ==currentDRN-1)){//if the seqence is not correct.
                if(!(lastDRN==lastDRNaddedToErrorMess)){//if it is refering to the same DRN or one next to it then no need to flag up.
                errorMess += "; " + Integer.toString(lastDRN);
                lastDRNaddedToErrorMess = lastDRN;//stores the last DRN that was involved in an error.
                drnIncOK = false;//sets this boolean so that if there is 
                }
            }
        }
        /**
         * Sets the DetailRecordNoControl
         */
        private void setLastDRN(int drn){
            lastDRN = drn;
        }
        /**
         * Sets the current DRN.
         */
        private void setCurrentDRN(int cdrn){
                currentDRN = cdrn;
        }
    }
}