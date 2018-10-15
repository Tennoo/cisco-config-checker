package com.tobias.configchecker.config;

import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.task.Task;

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
        int correctConfigCount = 0;
        for(int i = 0; i < config.getConfigItems().size(); i++) {
            if (config.getConfigItems().get(i).type == ConfigItem.ItemType.INTERFACEITEM){
                for()
            }
        }

    }

    private boolean compare


}
