package com.tobias.cisco.configchecker.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private List<String> lines;
    private String name;
    private Map<String, ArrayList<String>> interfaceProperties;
    private  Map<String, ArrayList<String>> vlanProperties;

    protected Config(String name){
        this.interfaceProperties = new HashMap<>();
        this.vlanProperties = new HashMap<>();
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

    public void printInterface(){
        for(String s:interfaceProperties.keySet()){
            System.out.println(s);
        }

    }



    public void printLines(){
        for(String s: lines){
            System.out.println(s);
        }
    }

    protected void setVlanProperties(String vlan, ArrayList<String> subcommands){
        this.vlanProperties.put(vlan,subcommands);
    }

}
