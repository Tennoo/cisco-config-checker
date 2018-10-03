package com.tobias.cisco.configchecker.gui;

import com.tobias.cisco.configchecker.config.ConfigLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;


public class MainWindowController {
    private static Stage stage;
    private File configFile;



    private FileChooser fileChooser = new FileChooser();

    public void initialize() {
        ConfigLoader configLoader = new ConfigLoader();
        fileChooser.setTitle("Open Config File");
        this.configFile = fileChooser.showOpenDialog(stage);
        configLoader.load(configFile);
    }

    public static void setStageInstance(Stage stageInstance) {
        stage = stageInstance;
    }



}