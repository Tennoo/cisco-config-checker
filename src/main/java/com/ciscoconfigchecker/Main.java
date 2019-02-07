package com.ciscoconfigchecker;

import com.ciscoconfigchecker.gui.MainWindowController;
import com.threerings.getdown.util.LaunchUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = new FXMLLoader().load(getClass().getResourceAsStream("/fxml/mainwindow.fxml"));
        primaryStage.setTitle("Cisco Config Checker");
        primaryStage.setScene(new Scene(root, 1300, 700));
        MainWindowController.setStageInstance(primaryStage);
        primaryStage.show();
        root.getStylesheets().add(this.getClass().getClassLoader().getResource("css/app.css").toExternalForm());

    }

    public static void main(String[] args) {
        if (args.length > 0) {
            final File appdir = new File(args[0]);
            new Thread() {
                @Override
                public void run() {
                    LaunchUtil.upgradeGetdown(new File(appdir, "./getdown-old.jar"),
                            new File(appdir, "./getdown.jar"),
                            new File(appdir, "./getdown-new.jar"));
                }
            }.start();
        }
        launch(args);

    }
}
