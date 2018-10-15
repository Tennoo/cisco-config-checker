package com.tobias.cisco.configchecker.config.item;


import java.util.List;

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


}
