package sample;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    static TextArea textControl;
    static Path filePath;
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static void setLogFileLocation(String path){
        filePath = Paths.get(path);
    }

    public static void setTextAreaLink(TextArea inputControl){
        textControl = inputControl;
    }

    public static void appendLog(String message){
        message = getDateTime().concat(" ".concat(message));
        String finalMessage = message;
        Platform.runLater(new Runnable() {
            @Override public void run(){
            if (textControl.getText().equals(null) || textControl.getText().equals("")){
            textControl.setText(finalMessage);
        }else {
            textControl.setText(textControl.getText() + "\n" + finalMessage);
        }}});

        writeToFile(message);
    }

    public static void appendLog(Exception inputException){
        StringWriter errors = new StringWriter();
        inputException.printStackTrace(new PrintWriter(errors));
        String message = errors.toString();

        message = getDateTime().concat(" ".concat(message));
        String messageToBox = getDateTime().concat(" ".concat(inputException.getMessage()));
        Platform.runLater(new Runnable() {
            @Override public void run() {
                if (textControl.getText().equals(null) || textControl.getText().equals("")) {
                    textControl.setText(messageToBox);
                } else {
                    textControl.setText(textControl.getText() + "\n" + messageToBox);
                }
            }
        });
        writeToFile(message);
    }

    public static void appendLogNoWrite(Exception inputException){
//        StringWriter errors = new StringWriter();
//        inputException.printStackTrace(new PrintWriter(errors));
//        String message = errors.toString();

        String messageToBox = getDateTime().concat(" ".concat(inputException.getMessage()));
        Platform.runLater(new Runnable() {
            @Override public void run() {
                if (textControl.getText().equals(null) || textControl.getText().equals("")) {
                    textControl.setText(messageToBox);
                } else {
                    textControl.setText(textControl.getText() + "\n" + messageToBox);
                }
            }});
    }

    public static void writeToFile(String data){
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            writer.write("\n" + data, 0, data.length());
        } catch (Exception e) {
            appendLogNoWrite(e);
        }
    }

    public static String getDateTime(){
        LocalDateTime dateTime = LocalDateTime.now();
        return "[" + dtf.format(dateTime) + "]";
    }
}
