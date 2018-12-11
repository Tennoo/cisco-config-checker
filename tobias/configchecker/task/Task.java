package com.tobias.configchecker.task;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private String name;
    private List<Command> taskCommand;
    private List<String> vlans;
    private List<String> trunkedVlans;

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

    protected void setVlanProperties(List<String> ids){
        if(!ids.isEmpty()) {
            vlans.addAll(ids);
        }
    }
    protected void setTrunkProperties(List<String> ids){
        if(!ids.isEmpty()){
            trunkedVlans.addAll(ids);
        }
    }

    public List<Command> getTaskCommand(){
        return taskCommand;
    }

    public List<String> getVlans() {
        return vlans;
    }

    // Returns all vlans except Vlan 1
    public List<String> getEffectiveVlans(){
        List<String> efvlans = new ArrayList<>();
        for(String s : vlans){
            if (!s.equals("1")){
                efvlans.add(s);
            }
        }
        return efvlans;
    }

    public List<String> getTrunkedVlans() {
        return trunkedVlans;
    }
}
