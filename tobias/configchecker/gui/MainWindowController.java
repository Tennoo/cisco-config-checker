package com.tobias.configchecker.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.tobias.configchecker.config.comparator.ConfigComparator;
import com.tobias.configchecker.config.ConfigLoader;
import com.tobias.configchecker.config.message.Message;
import com.tobias.configchecker.config.message.MessageCode;
import com.tobias.configchecker.task.TaskLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.List;


public class MainWindowController {


    @FXML
    private JFXButton selectConfigButton;

    @FXML
    private JFXListView briefMessageView;

    @FXML
    private JFXListView detailedMessageView;

    private static Stage stage;
    private static HashMap<Message, List<Message>> errorMessages = new HashMap<>();
    private static HashMap<Message, List<Message>> correctMessages = new HashMap<>();
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
        for (Message m : errorMessages.keySet()) {
            briefMessageView.getItems().add(m.getMessage());
            detailedMessageView.getItems().add(errorMessages.get(m));

        }

    }

    public static void addErrorMessage(Message brief, List<Message> detailed) {
        if (brief != null || detailed != null) {
            errorMessages.put(brief, detailed);
        }
    }

    public static void addCorrectMessage(Message brief, List<Message> detailed) {
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
        //configLoader.getconfig();
    }

}