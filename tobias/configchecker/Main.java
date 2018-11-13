package com.tobias.configchecker;
import com.tobias.configchecker.gui.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainWindowController.setStageInstance(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("gui/mainwindow.fxml"));
        primaryStage.setTitle("Cisco Config Checker");
        primaryStage.setScene(new Scene(root, 1300, 700));
        primaryStage.show();
        root.getStylesheets().add(this.getClass().getResource("gui/style/app.css").toExternalForm());

    }

    public static void main(String[] args) {
        launch(args);

    }
}
