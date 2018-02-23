package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class Controller {
    @FXML ProgressIndicator pi1;
    @FXML ProgressIndicator pi2;
    @FXML Label progressLabel;
    @FXML TableView table;
    @FXML TextArea logTextArea;

    @FXML TextField dbConnectionField;
    @FXML TextField dbUserField;
    @FXML PasswordField dbPassField;

    @FXML CheckBox columnCB1;
    @FXML CheckBox columnCB2;
    @FXML CheckBox columnCB3;
    @FXML CheckBox columnCB4;
    @FXML CheckBox columnCB5;
    @FXML CheckBox columnCB6;
    @FXML CheckBox columnCB7;
    @FXML CheckBox columnCB8;
    @FXML CheckBox columnCB9;
    @FXML CheckBox columnCB10;
    @FXML CheckBox columnCB11;
    @FXML CheckBox columnCB12;
    @FXML CheckBox columnCB13;
    @FXML CheckBox columnCB14;
    @FXML CheckBox columnCB15;
    @FXML CheckBox columnCB16;
    @FXML CheckBox columnCB17;
    @FXML CheckBox columnCB18;
    @FXML CheckBox columnCB19;
    @FXML CheckBox columnCB20;

    ObservableList<PackageInformation> tableData;
    DatabaseConnector dbConnector;
    LinuxMethods linuxMethods;

    Thread linuxMethodsThread;

    @FXML
    public void initialize(){
        setProgress(0);
        Logger.setTextAreaLink(logTextArea);
    }

    public void buttonUpdateFromDatabaseClick(ActionEvent e){
        tableData = FXCollections.observableArrayList();
        table.getItems().clear();
        dbConnector.updateTableViewFromDatabase(linuxMethods,this);
    }

    public void setMethods(LinuxMethods inputMethods){
        try {
            linuxMethods = inputMethods;
        }catch (Exception e){
            Logger.appendLog(e);
        }
    }

    public void buttonCreateDBClick(){
        try {
            linuxMethodsThread = new Thread(linuxMethods);
            linuxMethodsThread.start();
        }catch (Exception e){
            Logger.appendLog(e);
        }
    }

    public void setProgress(int inputState){
        try {
            if (inputState == 1){
                pi2.setProgress(1);
                pi2.setVisible(true);
                pi1.setVisible(false);
            } else if (inputState == -1){
                pi1.setVisible(true);
                pi2.setVisible(false);
            } else if (inputState == 0){
                pi2.setProgress(0);
                pi2.setVisible(true);
                pi1.setVisible(false);
            }
        }catch (Exception e){
            Logger.appendLog(e);
        }
    }

    public void setDbConnector(DatabaseConnector input){
        try {
            dbConnector = input;
        }catch (Exception e){
            Logger.appendLog(e);
        }
    }

    public void buttonUpdateDBClick(ActionEvent e){
        try {
            dbConnector.close();
            dbConnector.updateDatabaseConnectionValues(dbConnectionField.getText(),dbUserField.getText(),dbPassField.getText());
        }catch (Exception ex){
            Logger.appendLog(ex);
        }
    }

    public void updateDBFields(String url,String user,String pass){
        try {
            dbConnectionField.setText(url);
            dbUserField.setText(user);
            dbPassField.setText(pass);
        }catch (Exception e){
            Logger.appendLog(e);
        }
    }

    public void buttonUpdateTableClick(ActionEvent e){
        updateTableData();
    }

    public void buttonDeselectAllClick(ActionEvent e){
        try {
            columnCB1.setSelected(false);
            columnCB2.setSelected(false);
            columnCB3.setSelected(false);
            columnCB4.setSelected(false);
            columnCB5.setSelected(false);
            columnCB6.setSelected(false);
            columnCB7.setSelected(false);
            columnCB8.setSelected(false);
            columnCB9.setSelected(false);
            columnCB10.setSelected(false);
            columnCB11.setSelected(false);
            columnCB12.setSelected(false);
            columnCB13.setSelected(false);
            columnCB14.setSelected(false);
            columnCB15.setSelected(false);
            columnCB16.setSelected(false);
            columnCB17.setSelected(false);
            columnCB18.setSelected(false);
            columnCB19.setSelected(false);
            columnCB20.setSelected(false);
        }catch (Exception ex){
            Logger.appendLog(ex);
        }
    }

    public void buttonSelectAllClick(ActionEvent e){
        try {
            columnCB1.setSelected(true);
            columnCB2.setSelected(true);
            columnCB3.setSelected(true);
            columnCB4.setSelected(true);
            columnCB5.setSelected(true);
            columnCB6.setSelected(true);
            columnCB7.setSelected(true);
            columnCB8.setSelected(true);
            columnCB9.setSelected(true);
            columnCB10.setSelected(true);
            columnCB11.setSelected(true);
            columnCB12.setSelected(true);
            columnCB13.setSelected(true);
            columnCB14.setSelected(true);
            columnCB15.setSelected(true);
            columnCB16.setSelected(true);
            columnCB17.setSelected(true);
            columnCB18.setSelected(true);
            columnCB19.setSelected(true);
            columnCB20.setSelected(true);
        }catch (Exception ex){
            Logger.appendLog(ex);
        }
    }

    public boolean[] analizeColumnCheckBoxes(){
        try {
            boolean[] output = new boolean[20];
            output[0] = columnCB1.isSelected();
            output[1] = columnCB2.isSelected();
            output[2] = columnCB3.isSelected();
            output[3] = columnCB4.isSelected();
            output[4] = columnCB5.isSelected();
            output[5] = columnCB6.isSelected();
            output[6] = columnCB7.isSelected();
            output[7] = columnCB8.isSelected();
            output[8] = columnCB9.isSelected();
            output[9] = columnCB10.isSelected();
            output[10] = columnCB11.isSelected();
            output[11] = columnCB12.isSelected();
            output[12] = columnCB13.isSelected();
            output[13] = columnCB14.isSelected();
            output[14] = columnCB15.isSelected();
            output[15] = columnCB16.isSelected();
            output[16] = columnCB17.isSelected();
            output[17] = columnCB18.isSelected();
            output[18] = columnCB19.isSelected();
            output[19] = columnCB20.isSelected();
            return output;
        }catch (Exception e){
            Logger.appendLog(e);
        }
        return null;
    }

    public void setTableData(ArrayList<PackageInformation> inputList){
        try {
            tableData = FXCollections.observableArrayList(inputList);
            ArrayList<TableColumn> columnsList = new ArrayList<>();

            TableColumn idColumn = new TableColumn("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("id"));

            TableColumn packageNameColumn = new TableColumn("Package Name");
            packageNameColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("packageName"));

            TableColumn versionColumn = new TableColumn("Version");
            versionColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("version"));

            TableColumn priorityColumn = new TableColumn("Priority");
            priorityColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("priority"));

            TableColumn sectionColumn = new TableColumn("Section");
            sectionColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("section"));

            TableColumn maintainerColumn = new TableColumn("Maintainer");
            maintainerColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("maintainer"));

            TableColumn installSizeColumn = new TableColumn("Install Size");
            installSizeColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("installSize"));

            TableColumn downloadSizeColumn = new TableColumn("Download Size");
            downloadSizeColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("downloadSize"));

            TableColumn dependsColumn = new TableColumn("Depends");
            dependsColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("depends"));

            TableColumn breaksColumn = new TableColumn("Breaks");
            breaksColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("breaks"));

            TableColumn replacesColumn = new TableColumn("Replaces");
            replacesColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("replaces"));

            TableColumn homepageColumn = new TableColumn("Homepage");
            homepageColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("homepage"));

            TableColumn tagColumn = new TableColumn("Tag");
            tagColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("tag"));

            TableColumn aptSourcesColumn = new TableColumn("APT Sources");
            aptSourcesColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("aptSources"));

            TableColumn recommendsColumn = new TableColumn("Recommends");
            recommendsColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("recommends"));

            TableColumn providesColumn = new TableColumn("Provides");
            providesColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("provides"));

            TableColumn sourceNameColumn = new TableColumn("Source Name");
            sourceNameColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("sourceName"));

            TableColumn preDependsColumn = new TableColumn("Pre-depends");
            preDependsColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("preDepends"));

            TableColumn suggestsColumn = new TableColumn("Suggests");
            suggestsColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("suggests"));

            TableColumn descriptionColumn = new TableColumn("Description");
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("description"));

            columnsList.add(idColumn);
            columnsList.add(packageNameColumn);
            columnsList.add(versionColumn);
            columnsList.add(priorityColumn);
            columnsList.add(sectionColumn);
            columnsList.add(maintainerColumn);
            columnsList.add(installSizeColumn);
            columnsList.add(downloadSizeColumn);
            columnsList.add(dependsColumn);
            columnsList.add(breaksColumn);
            columnsList.add(replacesColumn);
            columnsList.add(homepageColumn);
            columnsList.add(tagColumn);
            columnsList.add(aptSourcesColumn);
            columnsList.add(recommendsColumn);
            columnsList.add(providesColumn);
            columnsList.add(sourceNameColumn);
            columnsList.add(preDependsColumn);
            columnsList.add(suggestsColumn);
            columnsList.add(descriptionColumn);

            table.setItems(tableData);

            boolean[] isCheckedArray = analizeColumnCheckBoxes();
            for (int counter = 0;counter < isCheckedArray.length;counter++){
                if (isCheckedArray[counter]){
                    table.getColumns().add(columnsList.get(counter));
                }
            }
        }catch (Exception e){
            Logger.appendLog(e);
        }
    }

    public void updateTableData(){
        try {
            table.getColumns().clear();

            ArrayList<TableColumn> columnsList = new ArrayList<>();

            TableColumn idColumn = new TableColumn("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("id"));

            TableColumn packageNameColumn = new TableColumn("Package Name");
            packageNameColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("packageName"));

            TableColumn versionColumn = new TableColumn("Version");
            versionColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("version"));

            TableColumn priorityColumn = new TableColumn("Priority");
            priorityColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("priority"));

            TableColumn sectionColumn = new TableColumn("Section");
            sectionColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("section"));

            TableColumn maintainerColumn = new TableColumn("Maintainer");
            maintainerColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("maintainer"));

            TableColumn installSizeColumn = new TableColumn("Install Size");
            installSizeColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("installSize"));

            TableColumn downloadSizeColumn = new TableColumn("Download Size");
            downloadSizeColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("downloadSize"));

            TableColumn dependsColumn = new TableColumn("Depends");
            dependsColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("depends"));

            TableColumn breaksColumn = new TableColumn("Breaks");
            breaksColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("breaks"));

            TableColumn replacesColumn = new TableColumn("Replaces");
            replacesColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("replaces"));

            TableColumn homepageColumn = new TableColumn("Homepage");
            homepageColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("homepage"));

            TableColumn tagColumn = new TableColumn("Tag");
            tagColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("tag"));

            TableColumn aptSourcesColumn = new TableColumn("APT Sources");
            aptSourcesColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("aptSources"));

            TableColumn recommendsColumn = new TableColumn("Recommends");
            recommendsColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("recommends"));

            TableColumn providesColumn = new TableColumn("Provides");
            providesColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("provides"));

            TableColumn sourceNameColumn = new TableColumn("Source Name");
            sourceNameColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("sourceName"));

            TableColumn preDependsColumn = new TableColumn("Pre-depends");
            preDependsColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("preDepends"));

            TableColumn suggestsColumn = new TableColumn("Suggests");
            suggestsColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("suggests"));

            TableColumn descriptionColumn = new TableColumn("Description");
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<PackageInformation, String>("description"));

            columnsList.add(idColumn);
            columnsList.add(packageNameColumn);
            columnsList.add(versionColumn);
            columnsList.add(priorityColumn);
            columnsList.add(sectionColumn);
            columnsList.add(maintainerColumn);
            columnsList.add(installSizeColumn);
            columnsList.add(downloadSizeColumn);
            columnsList.add(dependsColumn);
            columnsList.add(breaksColumn);
            columnsList.add(replacesColumn);
            columnsList.add(homepageColumn);
            columnsList.add(tagColumn);
            columnsList.add(aptSourcesColumn);
            columnsList.add(recommendsColumn);
            columnsList.add(providesColumn);
            columnsList.add(sourceNameColumn);
            columnsList.add(preDependsColumn);
            columnsList.add(suggestsColumn);
            columnsList.add(descriptionColumn);

            table.setItems(tableData);

            boolean[] isCheckedArray = analizeColumnCheckBoxes();
            for (int counter = 0;counter < isCheckedArray.length;counter++){
                if (isCheckedArray[counter]){
                    table.getColumns().add(columnsList.get(counter));
                }
            }
        }catch (Exception e){
            Logger.appendLog(e);
        }
    }


}
