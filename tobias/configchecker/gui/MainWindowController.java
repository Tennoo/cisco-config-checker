package com.tobias.configchecker.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.tobias.configchecker.config.comparator.ConfigComparator;
import com.tobias.configchecker.config.ConfigLoader;
import com.tobias.configchecker.task.TaskLoader;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import java.io.File;
import java.util.HashMap;


public class MainWindowController {


    @FXML
    private JFXButton selectConfigButton;

    @FXML
    private TreeView errorView;

    @FXML
    private JFXListView detailedErrorView;
    private static Stage stage;
    private static HashMap<String, ObservableList<String>> errorMessages = new HashMap<>();
    private File configFile = new File("C:\\Users\\ekonc\\Documents\\Switch0_startup-config.txt");


    public void initialize() {
        ConfigLoader configLoader = new ConfigLoader();
        configLoader.load(configFile);
        TaskLoader taskLoader = new TaskLoader();
        taskLoader.load();
        ConfigComparator configComparator = new ConfigComparator();
        configComparator.setTask(taskLoader.getTasksList().get(0));
        configComparator.setConfig(configLoader.getConfig());
        configComparator.compare();
        TreeItem<String> errorItem = new TreeItem<>("Errors");
        for(String s: errorMessages.keySet()){
        errorItem.getChildren().addAll(new TreeItem(s));
        }
        errorView.setRoot(errorItem);
    }

    public static void addErrorMessage(String brief, ObservableList<String> detailed) {
        if (brief != null || detailed != null) {
            errorMessages.put(brief, detailed);
        }
    }

    public void onErrorItemClick(){
        TreeItem<String> item = new TreeItem<>();
        if(errorView.getSelectionModel().getSelectedItem() != null){
            item = (TreeItem<String>) errorView.getSelectionModel().getSelectedItem();
        }
         if(item.isLeaf() && item.getValue() != null){
             detailedErrorView.setItems(errorMessages.get(item.getValue()));
         }

    }


    public static void setStageInstance(Stage stageInstance) {
        stage = stageInstance;
    }

    public void onSelectFileClick() {
        //  fileChooser.setTitle("Open Config File");
        // this.configFile = fileChooser.showOpenDialog(stage);
        // ConfigLoader configLoader = new ConfigLoader();
        //configLoader.load(configFile);
    }

}