package com.ciscoconfigchecker.config.comparator;

import com.ciscoconfigchecker.config.Config;
import com.ciscoconfigchecker.config.item.VlanItem;
import com.ciscoconfigchecker.gui.MainWindowController;
import com.ciscoconfigchecker.task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

class VlanComparator {
    private Task task;
    private Config config;


    void configure(Task task, Config config) {
        this.task = task;
        this.config = config;
    }

    void compare() {
        hasCorrectProperties();
        hasAllVlans();
    }

    private boolean hasIp() {
        String taskIp;
        if ((taskIp = task.getFullCommand("ip")) != null) {
            if (taskIp.equals("ip")) {
                return (config.getVlanItemById("1").getIp() != null);
            } else {
                String[] confIp = config.getVlanItemById("1").getIp().split(" ");
                // Index two and three is IP + subnet mask. The full IP returned by getIp() would be "ip address xxxx xxxx".
                // Index one would be "ip".
                return ("ip " + confIp[2] + " " + confIp[3]).equals(taskIp);
            }
        }
        return false;
    }

    private void hasCorrectProperties() {
        ObservableList<String> detailedErrorMessages = FXCollections.observableArrayList();
        for (VlanItem v : config.getVlanItems()) {
            if (v.isShutDown()) {
                detailedErrorMessages.add(v.getName() + "is offline");
            }
        }
        if (task.hasCommand("ip")) {
            if (!hasIp()) {
                String ip;
                if((ip = task.getFullCommand("ip")).length() > 2){
                    String[] ipSplit = ip.split("\\s");
                    detailedErrorMessages.add("The IP: " + ipSplit[1] + " " + ipSplit[2]
                            + " must be set on Vlan 1");
                } else {
                    detailedErrorMessages.add("IP has not been set on Vlan 1");
                }
            }
        }
        if (detailedErrorMessages.size() > 0) {
            MainWindowController.addErrorMessage("Vlans have not been configured properly", detailedErrorMessages);
        }
    }


    private void hasAllVlans() {
        ObservableList<String> detailedErrorMessages = FXCollections.observableArrayList();
        Collection res = CollectionUtils.removeAll(task.getVlans(), config.getVlanItemId());
        for (Object o : res) {
            detailedErrorMessages.add("Vlan" + o + " is missing from the config file");
        }
        if (detailedErrorMessages.size() > 0) {
            MainWindowController.addErrorMessage("Config is missing Vlans", detailedErrorMessages);
        }
    }
}

