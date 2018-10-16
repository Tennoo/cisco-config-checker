package com.tobias.configchecker.config;

import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.config.item.InterfaceItem;
import com.tobias.configchecker.task.Task;

public class ConfigComparator {
    private Task task;
    private Config config;

    public void setTask(Task task) {
        this.task = task;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void compare(){
        compareInterface();
    }

    private boolean compareInterface(){
        int correctConfigCount = 0;
        for(int i = 0; i < config.getConfigItems().size(); i++) {
            ConfigItem configItem = config.getConfigItems().get(i);
            if (configItem.type == ConfigItem.ItemType.INTERFACEITEM){
                InterfaceItem item = (InterfaceItem)configItem;
                if(item.getTrunkedVlans().equals(task.getTrunkedVlans())){
                    System.out.println("haha");
                    return true;
                }
            }
        }
return false;
    }




}
