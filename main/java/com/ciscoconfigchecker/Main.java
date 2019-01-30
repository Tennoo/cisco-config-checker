package com.ciscoconfigchecker;

import com.ciscoconfigchecker.gui.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("fxml/mainwindow.fxml"));
        primaryStage.setTitle("Cisco Config Checker");
        primaryStage.setScene(new Scene(root, 1300, 700));
        MainWindowController.setStageInstance(primaryStage);
        primaryStage.show();
        root.getStylesheets().add(this.getClass().getClassLoader().getResource("css/app.css").toExternalForm());

    }

    public static void main(String[] args) {
        launch(args);

    }
}
