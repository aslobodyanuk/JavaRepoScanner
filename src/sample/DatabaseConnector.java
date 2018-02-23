package sample;

import javafx.application.Platform;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnector {
    Connection dbConnection = null;
    String dbUrl = "jdbc:mysql://post.mobicom.ua:3306/sandboxtwo?useSSL=false";
    String dbUser = "sanduser";
    String dbPassword = "sand0852";
    boolean isCreated = false;

    public void updateDatabaseConnectionValues(String url,String username,String password){
        try {
            dbUrl = url;
            dbUser = username;
            dbPassword = password;
        }catch (Exception e){
            Logger.appendLog(e);
        }
    }

    public void insertData(PackageInformation pi){
        try {
            if (isCreated == false){
                dbConnection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
                isCreated = true;
            }
            boolean isConnected = dbConnection.isValid(1000);
            if (!isConnected){
                dbConnection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
            }
                PreparedStatement prepState = dbConnection.prepareStatement("INSERT INTO packageinfo values (default,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                prepState.setString(1,pi.packageName);
                prepState.setString(2,pi.version);
                prepState.setString(3,pi.priority);
                prepState.setString(4,pi.section);
                prepState.setString(5,pi.maintainer);
                prepState.setString(6,pi.installSize);
                prepState.setString(7,pi.downloadSize);
                prepState.setString(8,pi.depends);
                prepState.setString(9,pi.breaks);
                prepState.setString(10,pi.replaces);
                prepState.setString(11,pi.homepage);
                prepState.setString(12,pi.tag);
                prepState.setString(13,pi.aptSources);
                prepState.setString(14,pi.recommends);
                prepState.setString(15,pi.provides);
                prepState.setString(16,pi.sourceName);
                prepState.setString(17,pi.preDepends);
                prepState.setString(18,pi.suggests);
                prepState.setString(19,pi.description);
                prepState.executeUpdate();
        }catch (Exception e) {
            Logger.appendLog(e);
        }
    }

    public void clearTable(){
        try {
            if (isCreated == false){
                dbConnection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
                isCreated = true;
            }
            boolean isConnected = dbConnection.isValid(1000);
            if (!isConnected){
                dbConnection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
            }
            Statement dbStatement = dbConnection.createStatement();
            dbStatement.executeUpdate("TRUNCATE packageinfo");
        } catch (Exception e){
            Logger.appendLog(e);
        }
    }

    public int getPackagesCount(){
        try {
            if (isCreated == false){
                dbConnection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
                isCreated = true;
            }
            boolean isConnected = dbConnection.isValid(1000);
            if (!isConnected){
                dbConnection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
            }
            Statement dbStatement = dbConnection.createStatement();
            ResultSet dbResultSet = dbStatement.executeQuery("SELECT COUNT(ID) FROM packageinfo");
            if (dbResultSet.next()){
                String resultString = dbResultSet.getString(1);
                int result = Integer.parseInt(resultString);
                return result;
            }
        } catch (Exception e){
            Logger.appendLog(e);
        }
        return -1;
    }

    public void createTable(){
        try {
            if (isCreated == false){
                dbConnection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
                isCreated = true;
            }
            boolean isConnected = dbConnection.isValid(1000);
            if (!isConnected){
                dbConnection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
            }
            Statement dbStatement = dbConnection.createStatement();
            dbStatement.executeUpdate("CREATE TABLE IF NOT EXISTS packageinfo" +
                    "(" +
                    "id INT AUTO_INCREMENT NOT NULL," +
                    "packageName VARCHAR(200)," +
                    "version VARCHAR(200)," +
                    "priority VARCHAR(200)," +
                    "section VARCHAR(200)," +
                    "maintainer TEXT," +
                    "installSize VARCHAR(200)," +
                    "downloadSize VARCHAR(200)," +
                    "depends TEXT," +
                    "breaks TEXT," +
                    "replaces TEXT," +
                    "homepage VARCHAR(300)," +
                    "tag TEXT," +
                    "aptSources VARCHAR(300)," +
                    "recommends TEXT," +
                    "provides TEXT," +
                    "sourceName VARCHAR(300)," +
                    "preDepends TEXT," +
                    "suggests TEXT," +
                    "description TEXT," +
                    "PRIMARY KEY (id)" +
                    ")");
        } catch (Exception e){
            Logger.appendLog(e);
        }
    }

    public ArrayList<PackageInformation> readFromTable(){
        try {
            if (isCreated == false){
                dbConnection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
                isCreated = true;
            }
            boolean isConnected = dbConnection.isValid(1000);
            if (!isConnected) {
                dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
            Statement dbStatement = dbConnection.createStatement();
            String requestString = "SELECT * FROM packageinfo";

            ArrayList<PackageInformation> outputList = new ArrayList<>();
            ResultSet dbResultSet = dbStatement.executeQuery(requestString);
            ResultSetMetaData rsmd = dbResultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (dbResultSet.next()){
                PackageInformation tempPI = new PackageInformation();
                for (int counter = 1;counter <= columnsNumber;counter++){
                    String tempString = dbResultSet.getString(counter);
                    tempPI.setValue(counter,tempString);
                }
                outputList.add(tempPI);
            }
            return outputList;
        }catch (Exception e){
            Logger.appendLog(e);
        }
        return null;
    }

    public void close(){
        try {
            isCreated = false;
            if (dbConnection != null) {
                dbConnection.close();
            }
        } catch (Exception e) {
            Logger.appendLog(e);
        }
    }

    public void updateTableViewFromDatabase(LinuxMethods methods, Controller control){
        try {
            if (isCreated == false){
                dbConnection = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
                isCreated = true;
            }
            boolean isConnected = dbConnection.isValid(1000);
            if (!isConnected) {
                dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
            Thread getData = new Thread(){
                public void run(){
                    methods.isDone = -1;
                    methods.processAptListStatus = "Reading information from database.";
                    ArrayList<PackageInformation> tempDataList = readFromTable();
                    methods.processAptListStatus = "Filling app table with data.";
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            control.setTableData(tempDataList);
                            methods.processAptListStatus = "Done reading database.";
                            methods.isDone = 1;
                        }
                    });
                }
            };
            getData.start();
        } catch (Exception e) {
            Logger.appendLog(e);
        }
    }
}
