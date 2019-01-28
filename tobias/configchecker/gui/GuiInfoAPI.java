package com.tobias.configchecker.gui;


import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.task.Task;

class GuiInfoAPI {

    private Task task;
    private Config config;

    void setTask(Task task) {
        this.task = task;
    }
    void setConfig(Config config){
        this.config = config;
    }

     Object getTaskVlans(boolean asString){
        if(asString){
            return (String.join(", ",task.getVlans()));
        }
        return task.getVlans();
    }

    Object getTaskTrunkedVlans(boolean asString) {
        if (asString) {
            return (String.join(", ", task.getTrunkedVlans()));
        }
        return task.getTrunkedVlans();
    }

    String getTaskCommands(){
        return (String.join(", ",task.getTaskCommands()));
    }

    String getConfigName(){
        return config.getName();
    }



}
