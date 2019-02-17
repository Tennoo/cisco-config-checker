package com.ciscoconfigchecker.gui;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;

import java.util.List;

public class ErrorViewController {
    @FXML
    private JFXListView errors;

     void setErrorItems(List<String> configIOErrors, List<String> taskIOErrors) {
        if(configIOErrors.size() > 0){
            errors.getItems().addAll(configIOErrors);
        }
        if (taskIOErrors.size() > 0){
            errors.getItems().addAll(taskIOErrors);
        }
    }

}
