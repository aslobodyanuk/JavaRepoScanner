package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class ProgressMonitor extends Thread{
    Controller controllerClass;
    Label progressLabel;
    LinuxMethods methodsClass;
    String currentLabel;
    DatabaseConnector dbConnector;
    boolean doMonitor = false;

    public ProgressMonitor(Label labelLink, LinuxMethods inputMethodsClass, Controller inputController, DatabaseConnector inputDB){
        progressLabel = labelLink;
        methodsClass = inputMethodsClass;
        controllerClass = inputController;
        dbConnector = inputDB;
    }

    public void run(){
        startMonitoring();
    }

    public void close(){
        doMonitor = false;
    }

    public void startMonitoring(){
        doMonitor = true;
        while (doMonitor){
            try {
                if (methodsClass.processAptListStatus.equals(currentLabel) == false){
                    currentLabel = methodsClass.processAptListStatus;
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            progressLabel.setText(currentLabel);
                            if (methodsClass.isDone == 1) {
                                controllerClass.setProgress(1);
                            }else if (methodsClass.isDone == -1){
                                controllerClass.setProgress(-1);
                            } else {
                                controllerClass.setProgress(0);
                            }
                        }
                    });
                    if (currentLabel.equals("Here will be displayed progress status.")){
                        Logger.appendLog("App start.");
                    }else {
                        Logger.appendLog(currentLabel);
                    }
                }
                if (methodsClass.isFillingDatabase == true){
                    int databaseCount = dbConnector.getPackagesCount();
                    if (databaseCount != -1){
                        if (databaseCount >= methodsClass.currentPercentValue + methodsClass.oneCoef){
                            methodsClass.currentPercentValue = databaseCount;
                            Double currentPercent = databaseCount / methodsClass.oneCoef;
                            methodsClass.currentPercent = currentPercent.intValue();
                            String percentString = "[" + methodsClass.currentPercent + "%] ";
                            Platform.runLater(new Runnable() {
                                @Override public void run() {
                                    progressLabel.setText(percentString.concat(currentLabel));
                                }
                            });
                        }
                    }
                }
                Thread.sleep(200);
            }catch (Exception e){
                Logger.appendLog(e);
            }
        }
    }
}
