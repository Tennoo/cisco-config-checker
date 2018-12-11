package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.config.item.VlanItem;
import com.tobias.configchecker.config.message.Message;
import com.tobias.configchecker.config.message.MessageCode;
import com.tobias.configchecker.gui.MainWindowController;
import com.tobias.configchecker.task.Task;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
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
        List<Message> detailedErrorMessages = new ArrayList<>();
        List<Message> detailedCorrectMessages = new ArrayList<>();
        int correctConfig = 0;
        for (VlanItem v : config.getVlanItems()) {
            if (v.getName().equals("Vlan1")) {
                // Todo simplified for()
                for (int i = 0; i < task.getTaskCommand().size(); i++) {
                    String prefix = task.getTaskCommand().get(i).getPrefix();
                    if (prefix.equals("ip")) {
                        if (v.hasIp()) {
                            correctConfig++;
                            detailedCorrectMessages.add(new Message("IP for switch has been set", MessageCode.VLAN_INFO_DETAIL));
                        } else {
                            detailedErrorMessages.add(new Message("IP for the switch has not been set", MessageCode.VLAN_ERROR_DETAIL));
                        }
                    }
                }
            }
            if (!v.isShutDown()) {
                correctConfig++;
                detailedCorrectMessages.add(new Message(v.getName() + " is online.", MessageCode.VLAN_INFO_DETAIL));
            } else {
                detailedErrorMessages.add(new Message(v.getName() + " is shutdown", MessageCode.VLAN_ERROR_DETAIL));
            }
        }
        // Add +1 to accommodate for Vlan 1 configuration
        boolean isCorrect = (correctConfig == config.getVlanItems().size() + 1);
        if (isCorrect) {
            MainWindowController.addCorrectMessage(new Message("Vlans have been correctly configured", MessageCode.VLAN_INFO_BRIEF), detailedCorrectMessages);
            return true;
        }
        MainWindowController.addErrorMessage(new Message("Some vlans have been misconfigured", MessageCode.VLAN_ERROR_BRIEF), detailedErrorMessages);
        return false;
    }

    protected boolean hasAllVlans() {
        List<Message> detailedErrorMessages = new ArrayList<>();
        List<Message> detailedCorrectMessages = new ArrayList<>();
        Collection res = CollectionUtils.removeAll(task.getVlans(),config.getVlanItemId());
        if(res.isEmpty()){
            MainWindowController.addCorrectMessage(new Message("Config has all Vlans present", MessageCode.VLAN_INFO_BRIEF), detailedCorrectMessages);
            return true;
        }
        else{
            for(Object o : res){
                detailedErrorMessages.add(new Message("Vlan" + o + " is missing from the config file", MessageCode.VLAN_ERROR_DETAIL));
            }
                MainWindowController.addErrorMessage(new Message("Config is missing Vlans", MessageCode.VLAN_ERROR_BRIEF), detailedErrorMessages);
            return false;
        }
    }




}

