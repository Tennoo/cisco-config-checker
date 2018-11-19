package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.config.item.InterfaceItem;
import com.tobias.configchecker.config.message.Message;
import com.tobias.configchecker.task.Task;
import org.apache.commons.collections4.CollectionUtils;

import javax.swing.event.ListDataEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class InterfaceComparator {

    private Config config;
    private Task task;

    public void setConfig(Config config) {
        this.config = config;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    protected boolean compareTrunkedVlan() {
        List<Message> detailedErrorMessages = new ArrayList<>();
        List<Message> detailedCorrectMessages = new ArrayList<>();
        List<String> trunkedVlans = 
        Collection res = CollectionUtils.removeAll(task.getTrunkedVlans(),);
    }


    protected boolean compareVlanConfig() {
        int vlanConfiguredCount = 0;
        for (InterfaceItem c : getInterfaceItems()) {
            for (int i = 0; i < task.getVlans().size(); i++) {
                if (c.hasTaggedVlan(task.getVlans().get(i))) {
                    //Todo new message
                    vlanConfiguredCount++;

                }
            }
        }
        return (vlanConfiguredCount >= task.getVlans().size());
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

    private List<String> getTrunkedVlans(){
        List<String> trunkedVlans= new ArrayList<>();
        for(InterfaceItem i : getInterfaceItems()){
            if(i.getTrunkedVlans() != null){
                trunkedVlans.add(i.getTrunkedVlans());
            }
        }
    }


}
