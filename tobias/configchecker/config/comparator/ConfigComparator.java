package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.config.item.InterfaceItem;
import com.tobias.configchecker.config.message.Message;
import com.tobias.configchecker.config.message.MessageCode;
import com.tobias.configchecker.task.Task;

import java.util.ArrayList;
import java.util.List;

public class ConfigComparator {
    private Task task;
    private Config config;
    private List<Message> messages;
    private InterfaceComparator interfaceComparator;
    private VlanComparator vlanComparator;

    public ConfigComparator(List<Message> messages) {
        this.messages = messages;
        this.interfaceComparator = new InterfaceComparator();
        this.vlanComparator = new VlanComparator();
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void compare(){
        if(!interfaceComparator.compareTrunkedVlan(config,task)){
            addMessage(new Message("Incorrect trunk config.", MessageCode.TRUNK_ERROR));
        }
        else{
            addMessage(new Message("Correct trunk config",MessageCode.TRUNK_INFO));
        }
        if(!interfaceComparator.compareVlanConfig(config,task)){
            addMessage(new Message("Incorrect vlan", MessageCode.CONFIG_ERROR));
        }
    }



}
