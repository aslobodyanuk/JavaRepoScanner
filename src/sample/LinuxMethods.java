package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class LinuxMethods extends Thread{
    DatabaseConnector dbConnection;
    String processAptListStatus = "Here will be displayed progress status.";
    int isDone = 0;
    boolean isFillingDatabase = false;
    int ammountOfPackets;
    double oneCoef;
    int currentPercentValue;
    int currentPercent;

    public void run(){
        executeFullProcess();
    }

    public void setDBConnection(DatabaseConnector dbConnector){
        dbConnection = dbConnector;
    }

    public void executeFullProcess(){
        isDone = -1;
        createAndFillAptNamesFile();
        generateAptListFile();
        addAptListToDatabase();
        isDone = 1;
    }

    public void createAndFillAptNamesFile(){
        try {
            processAptListStatus = "Executing apt list.";
            ArrayList<String> aptList = execCommand("apt list");
            processAptListStatus = "Getting clear packet names.";
            aptList = parseClearPacketNames(aptList);
            ammountOfPackets = aptList.size();
            oneCoef = ammountOfPackets / 100;
            currentPercent = 0;
            processAptListStatus = "Generating apt request string.";
            String aptNames = generateAptRequestString(aptList);
            processAptListStatus = "Writing apt string to file > aptString.txt.";
            writeFileString(aptNames, Paths.get("aptString.txt"));
            processAptListStatus = "Created and filled aptString file.";
        } catch (Exception e){
            Logger.appendLog(e);
        }
    }

    private void writeFileString(String input, Path inputPath){
        try {
            Charset charset = Charset.forName("UTF-8");
            BufferedWriter writer = Files.newBufferedWriter(inputPath, charset, StandardOpenOption.CREATE);
            writer.write(input,0,input.length());
            writer.close();
        }catch (Exception e){
            Logger.appendLog(e);
        }
    }

    private String generateAptRequestString(ArrayList<String> inputAptNames){
        String result = "";
        for (String value : inputAptNames){
            result = result.concat(value.concat(" "));
        }
        return result;
    }

    private ArrayList<String> parseClearPacketNames(ArrayList<String> aptInputList){
        try {
            ArrayList<String> output = new ArrayList<>();
            for (String value : aptInputList){
                String tempName = getPacketName(value);
                if (tempName != null)
                    output.add(tempName);
            }
            return output;
        }catch (Exception e) {
            Logger.appendLog(e);
            return null;
        }
    }

    private String getPacketName(String input){
        int indexOfSlash = input.indexOf('/');
        if (indexOfSlash == -1)
            return null;
        return input.substring(0,indexOfSlash);
    }

    ///////////////////////////

    public void generateAptListFile(){
        try {
            processAptListStatus = "Executing apt show script. (may take a long time) ";
            execCommandNoOutput("sh getAptList.sh");
            waitForFile("done.javacheck");
            processAptListStatus = "Created full apt repository info file > aptList.txt.";
        }catch (Exception e){
            Logger.appendLog(e);
        }
    }

    private void waitForFile(String inputPath){
        File checkFile = new File(inputPath);
        while (!checkFile.exists()){}
        checkFile.delete();
    }

    private String readFileString(Path path){
        Charset charset = Charset.forName("UTF-8");
        System.out.println(path.equals(null));
        String result = "";
        String line = null;
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            while ((line = reader.readLine()) != null) {
                result = result.concat(line);
            }
            System.out.println(result);
            return result;
        } catch (Exception e) {
            Logger.appendLog(e);
            return null;
        }
    }

    private ArrayList<String> execCommand(String commandString){
        String read = null;
        ArrayList<String> output = new ArrayList<>();
        try {
            Process p = Runtime.getRuntime().exec(commandString);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
            while ((read = stdInput.readLine()) != null) {
                output.add(read);
            }
            return output;
        }
        catch (Exception e) {
            Logger.appendLog(e);
            return null;
        }
    }

    private void execCommandNoOutput(String commandString){
        try {
            Process p = Runtime.getRuntime().exec(commandString);
        }
        catch (Exception e) {
            Logger.appendLog(e);
        }
    }

    /////////////////////////////

    public void addAptListToDatabase(){
        isFillingDatabase = true;
        currentPercent = 0;
        processAptListStatus = "Filling database with table data. (may take a long time) ";
        Path path = Paths.get("aptList.txt");
        Charset charset = Charset.forName("UTF-8");
        dbConnection.createTable();
        dbConnection.clearTable();
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            DatabaseConnector db = new DatabaseConnector();
            ArrayList<String> list = new ArrayList<>();
            String line = null;

            while ((line = reader.readLine()) != null) {
                list.add(line);
                if (line.equals("")){
                    PackageInformation packageInfo = new PackageInformation();
                    packageInfo.processFullInfo(list);
                    db.insertData(packageInfo);
                    list = new ArrayList<>();
                }
            }
            isFillingDatabase = false;
            processAptListStatus = "Done filling database.";
        } catch (Exception e) {
            Logger.appendLog(e);
        }
    }
}
