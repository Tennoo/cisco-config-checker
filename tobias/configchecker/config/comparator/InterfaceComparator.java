package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.config.item.InterfaceItem;
import com.tobias.configchecker.config.message.Message;
import com.tobias.configchecker.config.message.MessageCode;
import com.tobias.configchecker.gui.MainWindowController;
import com.tobias.configchecker.task.Task;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        Collection res = CollectionUtils.removeAll(task.getTrunkedVlans(), config.getTrunkedVlans());
        if (res.isEmpty()) {
            MainWindowController.addCorrectMessage(new Message("All trunked Vlans present", MessageCode.INTERFACE_INFO_BRIEF), detailedCorrectMessages);
            return true;
        }
        for (Object o : res) {
            detailedErrorMessages.add(new Message("Vlan" + o + " has not been trunked", MessageCode.INTERFACE_ERROR_DETAIL));
        }
        MainWindowController.addErrorMessage(new Message("Config is missing trunked Vlans", MessageCode.INTERFACE_ERROR_BRIEF), detailedErrorMessages);
        return false;
    }

    protected boolean compareTaggedVlans() {
        List<Message> detailedErrorMessages = new ArrayList<>();
        List<Message> detailedCorrectMessages = new ArrayList<>();
        for (String s : task.getEffectiveVlans()) {
            if (config.getTaggedVlans().contains(s)) {
                detailedCorrectMessages.add(new Message("Vlan" + s + " has been tagged", MessageCode.INTERFACE_INFO_DETAIL));
            } else {
                detailedErrorMessages.add(new Message("Vlan" + s + " has not been tagged", MessageCode.INTERFACE_ERROR_DETAIL));
            }
        }
        if (detailedErrorMessages.size() == 0) {
            MainWindowController.addCorrectMessage(new Message("Vlans properly tagged", MessageCode.INTERFACE_INFO_BRIEF), detailedCorrectMessages);
            return true;
        }

        MainWindowController.addErrorMessage(new Message("Config missing tagged Vlans", MessageCode.INTERFACE_ERROR_BRIEF), detailedErrorMessages);
        return false;

    }









}
