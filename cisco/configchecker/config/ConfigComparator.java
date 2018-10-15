package com.tobias.cisco.configchecker.config;

import com.tobias.cisco.configchecker.config.item.ConfigItem;
import com.tobias.cisco.configchecker.config.item.InterfaceItem;
import com.tobias.cisco.configchecker.task.Task;

import java.util.HashMap;
import java.util.Map;

public class ConfigComparator {
    private Task task;
    private Config config;

    public ConfigComparator(Task task, Config config){
        this.task = task;
        this.config = config;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void compare(){

    }

    private boolean compareInterface(){
        for(int i = 0; i < config.getConfigItems().size(); i++) {
            if (config.getConfigItems().get(i).type == ConfigItem.ItemType.INTERFACEITEM){


                
            }
        }

    }


}
