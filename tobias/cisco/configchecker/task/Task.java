package com.tobias.cisco.configchecker.task;

import com.tobias.cisco.configchecker.config.Command;

import java.util.List;

public class Task {

    private String name;
    private List<Command> taskSpecs;

    public void setName(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
