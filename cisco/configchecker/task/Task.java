package com.tobias.cisco.configchecker.task;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private String name;
    private List<Command> taskCommand;
    private List<Integer> vlans;
    private List<Integer> trunkedVlans;

    public Task(String name){
        this.name = name;
        this.taskCommand = new ArrayList<>();
        this.vlans = new ArrayList<>();
        this.trunkedVlans = new ArrayList<>();
    }


    //TODO public or protected?
    protected String getName() {
        return name;
    }

    protected void addTaskCommand(Command c){
        if(c != null){
            taskCommand.add(c);
        }
    }

    protected void setVlanProperties(List<Integer> ids){
        if(!ids.isEmpty()) {
            vlans.addAll(ids);
        }
    }
    protected void setTrunkProperties(List<Integer> ids){
        if(!ids.isEmpty()){
            trunkedVlans.addAll(ids);
        }
    }

    public List<Command> getTaskCommand(){
        return taskCommand;
    }

    public List<Integer> getVlans() {
        return vlans;
    }

    public List<Integer> getTrunkedVlans() {
        return trunkedVlans;
    }
}
