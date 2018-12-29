package com.tobias.configchecker.gui;

import com.jfoenix.controls.JFXButton;
import com.tobias.configchecker.config.comparator.ConfigComparator;
import com.tobias.configchecker.config.ConfigLoader;
import com.tobias.configchecker.task.TaskLoader;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.File;
import java.util.HashMap;
import java.util.List;


public class MainWindowController {


    @FXML
    private JFXButton selectConfigButton;

    @FXML
    private ListView briefMessageView;

    @FXML
    private ListView detailedMessageView;

    private static Stage stage;
    private static HashMap<String, ObservableList<String>> errorMessages = new HashMap<>();
    private static HashMap<String, List<String>> correctMessages = new HashMap<>();
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
        for(String m : errorMessages.keySet()){
            briefMessageView.getItems().add(m);
        }
    }

    public void onBriefMessageViewItemClick(){
        detailedMessageView.setItems(errorMessages.get(briefMessageView.getSelectionModel().getSelectedItem()));

    }

    public static void addErrorMessage(String brief, ObservableList<String> detailed) {
        if (brief != null || detailed != null) {
            errorMessages.put(brief, detailed);
        }
    }

    public static void addCorrectMessage(String brief, List<String> detailed) {
        if (brief != null || detailed != null) {
            correctMessages.put(brief, detailed);
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