package com.tobias.configchecker.task;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private String name;
    private List<String> taskCommand;
    private List<String> vlans;
    private List<String> trunkedVlans;

    public Task(String name){
        this.name = name;
        this.taskCommand = new ArrayList<>();
        this.vlans = new ArrayList<>();
        this.trunkedVlans = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    protected void addTaskCommand(String line){
        if(line != null){
            taskCommand.add(line);
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

    public List<String> getTaskCommands(){
        return taskCommand;
    }
    public List<String> getTaskCommandsByWildward(List<String> wildcards){
        List<String> commands = new ArrayList<>();
        for(String s : wildcards){
            for(String c : getTaskCommands()){
                if(c.contains(s)){
                    commands.add(c);
                }
            }
        }
        return commands;
    }

    public boolean hasCommand(String command){
        if(taskCommand != null) {
            for (String s : taskCommand) {
                if (s.contains(command)) {
                    return true;
                }
            }
        }
        return false;
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
