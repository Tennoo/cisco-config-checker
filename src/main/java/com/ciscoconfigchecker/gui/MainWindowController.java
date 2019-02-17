package com.ciscoconfigchecker.gui;

import com.ciscoconfigchecker.config.ConfigLoader;
import com.ciscoconfigchecker.config.comparator.ConfigComparator;
import com.ciscoconfigchecker.task.TaskLoader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainWindowController {

    @FXML
    private JFXButton conmpareConfigButton;
    @FXML
    private JFXButton selectConfigButton;
    @FXML
    private Label taskCommandLabel;
    @FXML
    private Label taskVlansLabel;
    @FXML
    private Label taskTrunkedVlansLabel;
    @FXML
    private TreeView<String> errorView;
    @FXML
    private JFXListView<String> detailedErrorView;
    @FXML
    private Label configLinesLabel;
    @FXML
    private Label configNameLabel;
    @FXML
    private JFXComboBox<String> taskSelector;
    @FXML
    private Text errorCountText;
    @FXML
    private Text compareFailedText;
    @FXML
    private Text issueWasFoundText;
    @FXML
    private Text hyperLinkText;
    @FXML
    private Text informationText;
    @FXML
    private Text configFileNotTxt;


    private GuiInfoAPI infoAPI;
    private TaskLoader taskLoader;
    private ConfigLoader configLoader;
    private File configFile;
    private long fileLastModified;
    private static List<String> configIOErrorMessages = new ArrayList<>();
    private static List<String> taskIOErrorMessages = new ArrayList<>();
    private static Stage mainWindowStage;
    private Stage errorWindowStage;
    private static HashMap<String, ObservableList<String>> errorMessages = new HashMap<>();
    private boolean configIsValid;


    public void initialize() {
        this.infoAPI = new GuiInfoAPI();
        this.taskLoader = new TaskLoader();
        this.configLoader = new ConfigLoader();
        taskLoader.load();
        setTaskList();
        notifyIOErrorLabel();

    }

    public static void addErrorMessage(String brief, ObservableList<String> detailed) {
        if (brief != null && detailed != null) {
            errorMessages.put(brief, detailed);
        }
    }

    private void setTaskList() {
        ObservableList<String> tasks = FXCollections.observableList(taskLoader.getTaskNames());
        if (taskLoader.getTasksList() != null) {
            taskSelector.setItems(tasks);
        }
    }

    private void setErrorMessages() {
        TreeItem<String> errorItem = new TreeItem<>("Errors");
        errorItem.setExpanded(true);
        if (errorMessages.size() > 0) {
            for (String s : errorMessages.keySet()) {
                errorItem.getChildren().addAll(new TreeItem<>(s));
            }
        } else {
            errorItem.getChildren().add(new TreeItem<>("Config is correct!"));
        }
        errorView.setRoot(errorItem);
    }

    public static void addConfigIoErrorMessage(String ioErrorMessage) {
        if(ioErrorMessage != null) {
            configIOErrorMessages.add(ioErrorMessage);
        }
    }
    public static void addTaskIOErrorMessage(String ioErrorMessage){
        if(ioErrorMessage != null){
            taskIOErrorMessages.add(ioErrorMessage);
        }
    }

    public void onErrorItemClick() {
        TreeItem<String> item = new TreeItem<>();
        if (errorView.getSelectionModel().getSelectedItem() != null) {
            item = errorView.getSelectionModel().getSelectedItem();
        }
        if (item.isLeaf() && item.getValue() != null) {
            detailedErrorView.setItems(errorMessages.get(item.getValue()));
        }
    }

    public void onTaskItemSelect() {
        infoAPI.setTask(taskLoader.getTaskByName(taskSelector.getSelectionModel().getSelectedItem()));
        updateTaskSpecification();
    }

    private void updateConfigSpecification() {
        configLinesLabel.setText(Integer.toString(configLoader.getLineNumber()));
        configNameLabel.setText(configLoader.getConfig().getName());
    }

    private void updateTaskSpecification() {
        taskCommandLabel.setText(infoAPI.getTaskCommands());
        taskVlansLabel.setText(infoAPI.getTaskVlans(true).toString());
        taskTrunkedVlansLabel.setText(infoAPI.getTaskTrunkedVlans(true).toString());
    }

    public static void setStageInstance(Stage stageInstance) {
        mainWindowStage = stageInstance;
    }

    private String getSelectedTaskAsString() {
        return taskSelector.getSelectionModel().getSelectedItem();
    }

    public void onSelectFileClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Config File");
        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("Text file (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        this.configFile = fileChooser.showOpenDialog(mainWindowStage);
        if (configFile != null) {
            if (FilenameUtils.getExtension(configFile.getPath()).equals("txt")) {
                fileLastModified = configFile.lastModified();
                setConfigFile();
                configFileNotTxt.setVisible(false);
            } else {
                configFileNotTxt.setVisible(true);
            }
        }
    }

    private void notifyIOErrorLabel(){
        if(configIOErrorMessages.size() > 0 || taskIOErrorMessages.size() > 0){
            errorCountText.setVisible(true);
            errorCountText.setText(Integer.toString((configIOErrorMessages.size() + taskIOErrorMessages.size())));
            issueWasFoundText.setVisible(true);
            hyperLinkText.setVisible(true);
            informationText.setVisible(true);
            compareFailedText.setVisible(true);
        } else {
            errorCountText.setText(null);
            errorCountText.setVisible(false);
            issueWasFoundText.setVisible(false);
            hyperLinkText.setVisible(false);
            informationText.setVisible(false);
            compareFailedText.setVisible(false);
        }
    }

    public void onHyperLinkTextClick(){
        if(hyperLinkText.isVisible()){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/errorview.fxml"));
                this.errorWindowStage = new Stage();
                this.errorWindowStage.setScene(new Scene(fxmlLoader.load()));
                ErrorViewController controller = fxmlLoader.getController();
                controller.setErrorItems(configIOErrorMessages,taskIOErrorMessages);
                errorWindowStage.show();
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    private void setConfigFile() {
        configIOErrorMessages.clear();
        configLoader.load(configFile);
        configIsValid = configLoader.validate();
        notifyIOErrorLabel();
        if(configIsValid) {
            updateConfigSpecification();
        }
    }

    public void onCompareButtonClick() {
        if(taskLoader.validate())
            taskIOErrorMessages.clear();
        if (getSelectedTaskAsString() != null && configLoader.getConfig() != null && configIsValid) {
            if (errorMessages.size() > 0) {
                errorMessages.clear();
                detailedErrorView.getItems().clear();
            }
            if (configFile.lastModified() > fileLastModified) {
                setConfigFile();
            }
            ConfigComparator configComparator = new ConfigComparator();
            configComparator.configure(taskLoader.getTaskByName(getSelectedTaskAsString()), configLoader.getConfig());
            configComparator.compare();
            setErrorMessages();
        }
    }

}
