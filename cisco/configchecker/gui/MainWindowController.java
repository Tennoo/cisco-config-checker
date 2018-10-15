package com.tobias.cisco.configchecker.gui;

import com.tobias.cisco.configchecker.config.ConfigLoader;
import com.tobias.cisco.configchecker.task.TaskLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class MainWindowController {
    @FXML
    private Button selectFile;

    private static Stage stage;
    private File configFile = new File("C:\\Users\\ekonc\\Documents\\Switch0_startup-config.txt");



    private FileChooser fileChooser = new FileChooser();

    public void initialize() {
        ConfigLoader configLoader = new ConfigLoader();
        configLoader.load(configFile);
       // TaskLoader taskLoader = new TaskLoader();
       // taskLoader.load();

    }

    public static void setStageInstance(Stage stageInstance) {
        stage = stageInstance;
    }

    public void onSelectFileClick(){
      //  fileChooser.setTitle("Open Config File");
       // this.configFile = fileChooser.showOpenDialog(stage);
       // ConfigLoader configLoader = new ConfigLoader();
        //configLoader.load(configFile);
        //configLoader.getconfig();
    }

}