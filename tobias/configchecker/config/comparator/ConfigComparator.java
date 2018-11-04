package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.config.item.InterfaceItem;
import com.tobias.configchecker.config.message.Message;
import com.tobias.configchecker.config.message.MessageCode;
import com.tobias.configchecker.task.Task;

import java.util.ArrayList;
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

    public void compare() {
        interfaceComparator.setConfig(config);
        interfaceComparator.setTask(task);
        vlanComparator.setTask(task);
        vlanComparator.setConfig(config);

        if(vlanComparator.hasAllVlans()){
            Message.addBriefMessage(new Message("Config has all Vlans present",MessageCode.VLAN_INFO_BRIEF));
        } else{
            Message.addBriefMessage(new Message("Config is missing Vlans.",MessageCode.VLAN_ERROR_BRIEF));
        }
        if (vlanComparator.compareVlan()){
            Message.addBriefMessage(new Message("All Vlans have been properly configured", MessageCode.VLAN_INFO_BRIEF));
        } else{
          Message.addBriefMessage(new Message("Vlans have been misconfigured",MessageCode.VLAN_ERROR_BRIEF));
        }
    }
}
