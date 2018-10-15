package com.tobias.configchecker.config;

import com.tobias.configchecker.config.item.ConfigItem;
import com.tobias.configchecker.config.item.InterfaceItem;
import com.tobias.configchecker.config.item.VlanItem;

import java.util.*;

public class Config {

    private List<String> lines;
    private String name;
    private List<ConfigItem> items;


    protected Config(String name){
        this.items = new ArrayList<>();
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
        this.items.add(new InterfaceItem(faPort,portConfig));

    }

    protected void setVlanProperties(String vlan,List<String> subcommands){
        this.items.add(new VlanItem(vlan,subcommands));
    }

    protected List<ConfigItem>getConfigItems(){
        return this.items;
    }

}
