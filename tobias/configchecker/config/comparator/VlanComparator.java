package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.config.item.VlanItem;
import com.tobias.configchecker.gui.MainWindowController;
import com.tobias.configchecker.task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;


class VlanComparator {
    private Task task;
    private Config config;


    void configure(Task task, Config config) {
        this.task = task;
        this.config = config;
    }

    void compare() {
        hasCorrectProperties();
        hasAllVlans();
    }

    private boolean hasIp() {
        return config
                .getVlanItemById("1")
                .hasIp();
    }

    private void hasCorrectProperties() {
        ObservableList<String> detailedErrorMessages = FXCollections.observableArrayList();
        for (VlanItem v : config.getVlanItems()) {
            if (v.isShutDown()) {
                detailedErrorMessages.add(v.getName() + "is offline");
            }
        }
        if (task.hasCommand("ip")) {
            if (!hasIp()) {
                detailedErrorMessages.add("IP has not been set");
            }
        }
        if (detailedErrorMessages.size() > 0) {
            MainWindowController.addErrorMessage("Vlans has not been configured properly", detailedErrorMessages);
        }
    }


    private void hasAllVlans() {
        ObservableList<String> detailedErrorMessages = FXCollections.observableArrayList();
        Collection res = CollectionUtils.removeAll(task.getVlans(), config.getVlanItemId());
        for (Object o : res) {
            detailedErrorMessages.add("Vlan" + o + " is missing from the config file");
        }
        if (detailedErrorMessages.size() > 0) {
            MainWindowController.addErrorMessage("Config is missing Vlans", detailedErrorMessages);
        }
    }
}

