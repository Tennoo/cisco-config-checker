package com.tobias.cisco.configchecker.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private List<String> lines;
    private String name;
    private Map<String, ArrayList<String>> interfaceProperties;

    protected Config(String name){
        this.interfaceProperties = new HashMap<>();
        this.lines = new ArrayList<>();
        this.name = name;
    }

    protected void addLine(String line){
        lines.add(line);
    }

    protected List<String> getLines(){
        return lines;
    }

    protected void setInterfaceProperties(String faPort, ArrayList<String> portConfig){
        this.interfaceProperties.put(faPort,portConfig);
    }

    public void printLines(){
        for(String s: lines){
            System.out.println(s);
        }
    }

}
