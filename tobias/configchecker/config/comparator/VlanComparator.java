package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.config.item.VlanItem;
import com.tobias.configchecker.config.message.Message;
import com.tobias.configchecker.config.message.MessageCode;
import com.tobias.configchecker.task.Task;

import java.util.ArrayList;
import java.util.List;

class VlanComparator {
    private Task task;
    private Config config;

    public void setTask(Task task) {
        this.task = task;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    protected boolean compareVlan() {
        int correctConfig = 0;
        for (VlanItem v : getVlanItems()) {
            if (v.getName().equals("Vlan1")) {
                for (int i = 0; i < task.getTaskCommand().size(); i++){
                    String prefix  = task.getTaskCommand().get(i).getPrefix();
                    if(prefix.equals("ip")){
                        if(v.hasIp()){
                            correctConfig++;
                            Message.addDetailedMessage(new Message("IP for switch has been set", MessageCode.VLAN_INFO_DETAIL));
                        } else{
                            Message.addDetailedMessage(new Message("IP for the switch has not been set",MessageCode.VLAN_ERROR_DETAIL));
                        }
                    }
                }
            }
            if (!v.isShutDown()){
                correctConfig++;

                Message.addDetailedMessage(new Message(v.getName() + " is online.",MessageCode.VLAN_INFO_DETAIL));
            }else {
                Message.addDetailedMessage(new Message(v.getName() + " is shutdown", MessageCode.VLAN_ERROR_DETAIL));
            }
        }
        // Add +1 to accommodate for Vlan 1 configuratipn
        return (correctConfig == getVlanItems().size() + 1) ;
    }

    protected boolean hasAllVlans() {
        int index = 0;
        int correctVlan = 0;
        for (VlanItem v : getVlanItems()) {
            if (v.getName().contains(task.getVlans().get(index))) {
                Message.addDetailedMessage(new Message(v.getName() + " correctly configured", MessageCode.VLAN_INFO_DETAIL));
                correctVlan++;
            } else {
                Message.addDetailedMessage(new Message(v.getName() + " could not be found in the task specifications", MessageCode.VLAN_ERROR_DETAIL));
            }
            index++;
        }
        return (correctVlan == task.getVlans().size());
    }

    private List<VlanItem> getVlanItems() {
        List<VlanItem> vlanItems = new ArrayList<>();
        for (int i = 0; i < config.getConfigItems().size(); i++) {
            ConfigItem configItem = config.getConfigItems().get(i);
            if (configItem.type == ConfigItem.ItemType.VLANITEM) {
                VlanItem item = (VlanItem) configItem;
                vlanItems.add(item);
            }
        }
        return vlanItems;
    }
}

