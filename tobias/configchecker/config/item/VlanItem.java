package com.tobias.configchecker.config.item;

import java.util.List;

public class VlanItem extends ConfigItem{

    private String name;
    private List<String> vlanConfig;

    public VlanItem(String name, List<String> vlanConfig) {
        this.name = name;
        this.vlanConfig = vlanConfig;
        this.type = ItemType.VLANITEM;
    }

    public String getName() {
        return name;
    }
}
