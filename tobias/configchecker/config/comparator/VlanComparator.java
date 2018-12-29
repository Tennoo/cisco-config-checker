package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.config.item.VlanItem;
import com.tobias.configchecker.gui.MainWindowController;
import com.tobias.configchecker.task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        ObservableList<String> detailedErrorMessages = FXCollections.observableArrayList();
        ObservableList<String> detailedCorrectMessages = FXCollections.observableArrayList();
        int correctConfig = 0;
        for (VlanItem v : config.getVlanItems()) {
            if (v.getName().equals("Vlan1")) {
                // Todo simplified for()
                for (int i = 0; i < task.getTaskCommand().size(); i++) {
                    String prefix = task.getTaskCommand().get(i).getPrefix();
                    if (prefix.equals("ip")) {
                        if (v.hasIp()) {
                            correctConfig++;
                            detailedCorrectMessages.add("IP for switch has been set");
                        } else {
                            detailedErrorMessages.add("IP for the switch has not been set");
                        }
                    }
                }
            }
            if (!v.isShutDown()) {
                correctConfig++;
                detailedCorrectMessages.add(v.getName() + " is online.");
            } else {
                detailedErrorMessages.add(v.getName() + " is shutdown");
            }
        }
        // Add +1 to accommodate for Vlan 1 configuration
        boolean isCorrect = (correctConfig == config.getVlanItems().size() + 1);
        if (isCorrect) {
            MainWindowController.addCorrectMessage("Vlans have been correctly configured", detailedCorrectMessages);
            return true;
        }
        MainWindowController.addErrorMessage("Some vlans have been misconfigured", detailedErrorMessages);
        return false;
    }

    protected boolean hasAllVlans() {
        ObservableList<String> detailedErrorMessages = FXCollections.observableArrayList();
        ObservableList<String> detailedCorrectMessages = FXCollections.observableArrayList();
        Collection res = CollectionUtils.removeAll(task.getVlans(),config.getVlanItemId());
        if(res.isEmpty()){
            MainWindowController.addCorrectMessage("Config has all Vlans present", detailedCorrectMessages);
            return true;
        }
        else{
            for(Object o : res){
                detailedErrorMessages.add("Vlan" + o + " is missing from the config file");
            }
                MainWindowController.addErrorMessage("Config is missing Vlans", detailedErrorMessages);
            return false;
        }
    }




}

