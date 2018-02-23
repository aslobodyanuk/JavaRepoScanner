package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    ProgressMonitor progressMonitor;
    Controller guiController;
    DatabaseConnector dbConnector;
    LinuxMethods linuxMethods;
    Thread monitorProgressThread;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Logger.setLogFileLocation("log.txt");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Repository Scanner");
        primaryStage.setResizable(false);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toString());
        primaryStage.setScene(scene);
        primaryStage.show();

        dbConnector = new DatabaseConnector();
        linuxMethods = new LinuxMethods();
        linuxMethods.setDBConnection(dbConnector);

        guiController = loader.getController();
        guiController.setMethods(linuxMethods);
        guiController.updateDBFields(dbConnector.dbUrl,dbConnector.dbUser,dbConnector.dbPassword);
        guiController.setDbConnector(dbConnector);

        progressMonitor = new ProgressMonitor(guiController.progressLabel,linuxMethods,guiController,dbConnector);
        monitorProgressThread = new Thread(progressMonitor);
        monitorProgressThread.start();

        dbConnector.updateTableViewFromDatabase(linuxMethods,guiController);
    }

    @Override
    public void stop(){
        try {
            progressMonitor.close();
            super.stop();
        }catch (Exception e){
            Logger.appendLog(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
