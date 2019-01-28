package com.tobias.configchecker.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.tobias.configchecker.config.comparator.ConfigComparator;
import com.tobias.configchecker.config.ConfigLoader;
import com.tobias.configchecker.task.TaskLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;


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
    private TreeView errorView;
    @FXML
    private JFXListView detailedErrorView;
    @FXML
    private Label configLinesLabel;
    @FXML
    private Label configNameLabel;
    @FXML
    private JFXComboBox taskSelector;

    private static Stage stage;
    private static HashMap<String, ObservableList<String>> errorMessages = new HashMap<>();
    private GuiInfoAPI infoAPI = new GuiInfoAPI();
    private TaskLoader taskLoader = new TaskLoader();
    private ConfigLoader configLoader = new ConfigLoader();
    private File configFile;
    private long fileLastModified;

    public void initialize() {
        taskLoader.load();
        setTaskList();

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
        if(errorMessages.size() > 0) {
            for (String s : errorMessages.keySet()) {
                errorItem.getChildren().addAll(new TreeItem(s));
            }
        } else {
            errorItem.getChildren().add(new TreeItem<>("No errors found!"));
        }
        errorView.setRoot(errorItem);
    }

    public void onErrorItemClick() {
        TreeItem<String> item = new TreeItem<>();
        if (errorView.getSelectionModel().getSelectedItem() != null) {
            item = (TreeItem<String>) errorView.getSelectionModel().getSelectedItem();
        }
        if (item.isLeaf() && item.getValue() != null) {
            detailedErrorView.setItems(errorMessages.get(item.getValue()));
        }
    }

    public void onTaskItemSelect() {
        infoAPI.setTask(taskLoader.getTaskByName((String) taskSelector.getSelectionModel().getSelectedItem()));
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
        stage = stageInstance;
    }

    private String getSelectedTaskAsString() {
        return (String) taskSelector.getSelectionModel().getSelectedItem();
    }

    public void onSelectFileClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Config File");
        this.configFile = fileChooser.showOpenDialog(stage);
        fileLastModified = configFile.lastModified();
        if (configFile != null) {
            setConfigFile();
        }
    }

    private void setConfigFile() {
        configLoader.load(configFile);
        updateConfigSpecification();
    }

    public void onCompareButtonClick() {
        if (getSelectedTaskAsString() != null && configLoader.getConfig() != null) {
            if (errorMessages.size() > 0) {
                errorMessages.clear();
                detailedErrorView.getItems().clear();
            }
            if (configFile.lastModified() > fileLastModified) {
                setConfigFile();
            }
        }
        ConfigComparator configComparator = new ConfigComparator();
        configComparator.configure(taskLoader.getTaskByName(getSelectedTaskAsString()), configLoader.getConfig());
        configComparator.compare();
        setErrorMessages();

    }

}
