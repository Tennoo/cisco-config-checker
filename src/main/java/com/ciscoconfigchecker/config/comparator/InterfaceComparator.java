package com.ciscoconfigchecker.config.comparator;

import com.ciscoconfigchecker.config.Config;
import com.ciscoconfigchecker.gui.MainWindowController;
import com.ciscoconfigchecker.task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

class InterfaceComparator {

    private Config config;
    private Task task;

    void configure(Task task, Config config) {
        this.task = task;
        this.config = config;
    }

    void compare() {
        compareTaggedVlans();
        compareTrunkedVlan();
    }

    private void compareTrunkedVlan() {
        ObservableList<String> detailedErrorMessages = FXCollections.observableArrayList();
        Collection res = CollectionUtils.removeAll(task.getTrunkedVlans(), config.getTrunkedVlans());
        for (Object o : res) {
            detailedErrorMessages.add("Vlan" + o + " has not been trunked");
        }
        if (detailedErrorMessages.size() > 0) {
            MainWindowController.addErrorMessage("Config is missing trunked Vlans", detailedErrorMessages);
        }
    }

    private void compareTaggedVlans() {
        ObservableList<String> detailedErrorMessages = FXCollections.observableArrayList();
        for (String s : task.getEffectiveVlans()) {
            if (!config.getTaggedVlans().contains(s)) {
                detailedErrorMessages.add("Vlan" + s + " has not been tagged");
            }
        }
        if (detailedErrorMessages.size() > 0) {
            MainWindowController.addErrorMessage("Config missing tagged Vlans", detailedErrorMessages);
        }

    }


}
