package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.gui.MainWindowController;
import com.tobias.configchecker.task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.collections4.CollectionUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class InterfaceComparator {

    private Config config;
    private Task task;

    public void setConfig(Config config) {
        this.config = config;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    protected boolean compareTrunkedVlan() {
        ObservableList<String> detailedErrorMessages = FXCollections.observableArrayList();
        ObservableList<String> detailedCorrectMessages = FXCollections.observableArrayList();
        Collection res = CollectionUtils.removeAll(task.getTrunkedVlans(), config.getTrunkedVlans());
        if (res.isEmpty()) {
            MainWindowController.addCorrectMessage("All trunked Vlans present", detailedCorrectMessages);
            return true;
        }
        for (Object o : res) {
            detailedErrorMessages.add("Vlan" + o + " has not been trunked");
        }
        MainWindowController.addErrorMessage("Config is missing trunked Vlans",detailedErrorMessages);
        return false;
    }

    protected boolean compareTaggedVlans() {
        ObservableList<String> detailedErrorMessages = FXCollections.observableArrayList();
        ObservableList<String> detailedCorrectMessages = FXCollections.observableArrayList();
        for (String s : task.getEffectiveVlans()) {
            if (config.getTaggedVlans().contains(s)) {
                detailedCorrectMessages.add("Vlan" + s + " has been tagged");
            } else {
                detailedErrorMessages.add("Vlan" + s + " has not been tagged");
            }
        }
        if (detailedErrorMessages.size() == 0) {
            MainWindowController.addCorrectMessage("Vlans properly tagged", detailedCorrectMessages);
            return true;
        }

        MainWindowController.addErrorMessage("Config missing tagged Vlans", detailedErrorMessages);
        return false;

    }









}
