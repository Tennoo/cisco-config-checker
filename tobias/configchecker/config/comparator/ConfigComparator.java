package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.config.item.InterfaceItem;
import com.tobias.configchecker.config.message.Message;
import com.tobias.configchecker.task.Task;

import java.util.ArrayList;
import java.util.List;

public class ConfigComparator {
    private Task task;
    private Config config;
    private List<Message> messages;

    public void addMessage(Message message){
        if(messages == null){
            this.messages = new ArrayList<>();
        }
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

    }

}
