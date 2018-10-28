package com.tobias.configchecker.gui;

import com.tobias.configchecker.config.comparator.ConfigComparator;
import com.tobias.configchecker.config.ConfigLoader;
import com.tobias.configchecker.config.message.Message;
import com.tobias.configchecker.config.message.MessageCode;
import com.tobias.configchecker.task.TaskLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainWindowController {
    @FXML
    private Button selectFile;

    private static Stage stage;
    private File configFile = new File("C:\\Users\\ekonc\\Documents\\Switch0_startup-config.txt");
    private List<Message> messages;



    private FileChooser fileChooser = new FileChooser();

    public void initialize() {
        this.messages = new ArrayList<>();
        ConfigLoader configLoader = new ConfigLoader();
        configLoader.load(configFile);
        TaskLoader taskLoader = new TaskLoader();
        taskLoader.load();
        ConfigComparator configComparator = new ConfigComparator(messages);
        configComparator.setTask(taskLoader.getTasksList().get(0));
        configComparator.setConfig(configLoader.getConfig());
        configComparator.compare();
        System.out.println(configComparator.getMessages().get(1).toString());

    }

    public void getmessage(){

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