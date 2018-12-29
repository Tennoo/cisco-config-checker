package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.task.Task;

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

    public void compare() {
        interfaceComparator.setConfig(config);
        interfaceComparator.setTask(task);
        vlanComparator.setTask(task);
        vlanComparator.setConfig(config);
        vlanComparator.hasAllVlans();
        vlanComparator.compareVlan();
        interfaceComparator.compareTrunkedVlan();
        interfaceComparator.compareTaggedVlans();


    }
}
