package com.tobias.cisco.configchecker.config;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private List<String> lines;
    private String name;

    protected Config(String name){
        this.lines = new ArrayList<>();
        this.name = name;
    }

    protected void addLine(String line){
        lines.add(line);
    }
    public List<String> getLines(){
        return lines;
    }
}
