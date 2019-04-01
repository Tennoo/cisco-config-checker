package com.ciscoconfigchecker.config.comparator;

import com.ciscoconfigchecker.config.Config;
import com.ciscoconfigchecker.gui.MainWindowController;
import com.ciscoconfigchecker.task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConfigComparator {
    private Task task;
    private Config config;
    private InterfaceComparator interfaceComparator;
    private VlanComparator vlanComparator;

    public ConfigComparator() {
        this.vlanComparator = new VlanComparator();
        this.interfaceComparator = new InterfaceComparator();
    }

    public void configure(Task task, Config config) {
        this.task = task;
        this.config = config;
    }

    private void compareCommands() {
        ObservableList<String> detailedErrorMessages = FXCollections.observableArrayList();
        List<String> tempCommands = new ArrayList<>();
        for (String c : config.getLines()) {
            for (String t : task.getTaskCommands()) {
                if (c.contains(t)) {
                    tempCommands.add(t);
                }
            }
        }
        // The checking of IP is not done here, it is done in the VLAN comparator class.
        if (task.hasCommand("ip")) {
            tempCommands.add(task.getFullCommand("ip"));
        }
        Collection res = CollectionUtils.removeAll(task.getTaskCommands(), tempCommands);
        for (Object o : res) {
            detailedErrorMessages.add("Command " + "'" + o + "'" + " is missing from the config file");
        }
        if (detailedErrorMessages.size() > 0) {
            MainWindowController.addErrorMessage("Config is missing commands", detailedErrorMessages);
        }
    }

    public void compare() {
        interfaceComparator.configure(task, config);
        vlanComparator.configure(task, config);
        vlanComparator.compare();
        interfaceComparator.compare();
        compareCommands();


    }
}
