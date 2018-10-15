package com.tobias.configchecker.config.item;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InterfaceItem extends ConfigItem{

   private String name;
   private List<String> props;

    public InterfaceItem(String name, List<String> props) {
        this.name = name;
        this.props = props;
        this.type = ItemType.INTERFACEITEM;
    }

    public boolean hasProperty(String property){
        if(props == null){
            return false;
        }
        for(String s : props){
            if(s.contains(property)){
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

    public List<String> getTrunkedVlans(){
       if(props != null) {
           for (String s : props) {
               if (s.startsWith("switchport mode trunk vlan")) {
                   String temp = s.substring(s.indexOf("vlan") + 1, s.length() - 1);
                   List<String> tempList = Arrays.asList(temp.split(",*"));
                   return tempList;
               }
           }
       }
            return null;
        }
    }

