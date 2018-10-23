package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.config.item.InterfaceItem;
import com.tobias.configchecker.config.item.VlanItem;
import com.tobias.configchecker.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InterfaceComparator {

    Config config;
    Task task;

    public InterfaceComparator(Config config, Task task) {
        this.config = config;
        this.task = task;
    }

    protected boolean compareTrunkedVlan() {

        l
        if (item.getTrunkedVlans().equals(task.getTrunkedVlans())) {
            //Todo logger
            return true;
        }

    }
    // Todo logger
        return false;
}

    protected boolean compareVlanConfig() {
        int vlanConfiguredCount = 0;
        int index = 0;
        for (InterfaceItem c : getInterfaceItems()) {
            index++;
            if(c.hasTaggedVlan(task.getVlans().get(index))){

            }
        }
    }

    private List<InterfaceItem> getInterfaceItems() {
        List<InterfaceItem> interfaceItems = new ArrayList<>();
        for (int i = 0; i < config.getConfigItems().size(); i++) {
            ConfigItem configItem = config.getConfigItems().get(i);
            if (configItem.type == ConfigItem.ItemType.INTERFACEITEM) {
                InterfaceItem item = (InterfaceItem) configItem;
                interfaceItems.add(item);
            }
        }
        return interfaceItems;
    }


}
