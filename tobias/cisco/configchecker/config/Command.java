package com.tobias.cisco.configchecker.config;

import java.util.ArrayList;
import java.util.List;

public class Command {

    private String prefix;
    private String args;
    private List<Command> subCommand;

    public Command(String prefix, String args){
        this.prefix = prefix;
        this.args = args;
        this.subCommand = new ArrayList<Command>();

    }


}
