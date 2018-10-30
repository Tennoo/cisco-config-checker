package com.tobias.configchecker.config.item;

import java.util.List;

public class VlanItem extends ConfigItem{

    private String name;
    private List<String> props;

    public VlanItem(String name, List<String> props) {
        this.name = name;
        this.props = props;
        this.type = ItemType.VLANITEM;
    }

    public String getName() {
        return name;
    }


    public boolean hasIp(String vlan){

    }
}
