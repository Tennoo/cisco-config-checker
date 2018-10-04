package com.tobias.cisco.configchecker;

import com.tobias.cisco.configchecker.gui.MainWindowController;
import com.tobias.cisco.configchecker.task.TaskLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainWindowController.setStageInstance(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("gui/mainwindow.fxml"));
        primaryStage.setTitle("Cisco Config Checker");
        primaryStage.setScene(new Scene(root, 1300, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
