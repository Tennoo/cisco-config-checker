package com.tobias.configchecker.config.item;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InterfaceItem extends ConfigItem {

    private String name;
    private List<String> props;

    public InterfaceItem(String name, List<String> props) {
        this.name = name;
        this.props = props;
        this.type = ItemType.INTERFACEITEM;
    }

    public boolean hasProperty(String property) {
        if (props == null) {
            return false;
        }
        for (String s : props) {
            if (s.contains(property)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public List<String> getProps() {
        return props;
    }

    public List<String> getTrunkedVlans() {
        if (props != null) {
            for (String s : props) {
                if (s.startsWith("switchport trunk allowed vlan")) {
                    String temp = s.substring(s.indexOf("vlan ") + 5);
                    List<String> tempList = Arrays.asList(temp.split("\\s*,\\s*"));
                    Collections.sort(tempList);
                    return tempList;
                }
            }
        }
        return null;
    }

    public boolean hasTaggedVlan(String vlan){
         for(String s: props){
                 if(s.equals("switchport access vlan " + vlan)){
                     return true;
                 }
             }
             return false;
    }

}

