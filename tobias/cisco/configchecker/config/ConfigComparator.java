package com.tobias.cisco.configchecker.config;

import com.tobias.cisco.configchecker.task.Task;

public class ConfigComparator {
    private Task task;
    private Config config;

    public ConfigComparator(Task task, Config config){
        this.task = task;
        this.config = config;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void compare(){

    }

    private void compareInterface(){


    }


}
