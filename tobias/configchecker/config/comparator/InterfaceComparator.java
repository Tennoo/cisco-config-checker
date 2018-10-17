package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.config.item.InterfaceItem;
import com.tobias.configchecker.config.item.VlanItem;
import com.tobias.configchecker.task.Task;

public class InterfaceComparator{

    protected boolean compareTrunkedVlan(Config config, Task task) {
        for (int i = 0; i < config.getConfigItems().size(); i++) {
            ConfigItem configItem = config.getConfigItems().get(i);
            if (configItem.type == ConfigItem.ItemType.INTERFACEITEM) {
                InterfaceItem item = (InterfaceItem) configItem;
                if (item.getTrunkedVlans() == task.getTrunkedVlans()) {
                    //Todo logger
                    return true;
                }
            }
        }
        // Todo logger
        return false;
    }

    protected boolean compareVlanConfig(Task task, Config config){
        for (int i = 0; i < config.getConfigItems().size(); i++) {
            ConfigItem configItem = config.getConfigItems().get(i);
            if (configItem.type == ConfigItem.ItemType.VLANITEM) {
                VlanItem item = (VlanItem) configItem;
                
            }
        }
    }
}
