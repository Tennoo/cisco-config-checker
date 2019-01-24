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

public class ConfigComparator {
    private Task task;
    private Config config;
    private InterfaceComparator interfaceComparator;
    private VlanComparator vlanComparator;

    public ConfigComparator() {
        this.vlanComparator = new VlanComparator();
        this.interfaceComparator = new InterfaceComparator();
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setConfig(Config config) {
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
        if(task.hasCommand("ip")){
            tempCommands.add("ip");
        }
        Collection res = CollectionUtils.removeAll(task.getTaskCommands(),tempCommands);
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
