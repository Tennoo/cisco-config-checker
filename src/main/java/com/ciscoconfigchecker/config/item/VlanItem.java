package com.ciscoconfigchecker.config.item;

import java.util.List;

public class VlanItem extends ConfigItem {

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

    public String getId() {
        return name.substring(name.indexOf("n") + 1);
    }


    public String getIp() {
        for (String s : props) {
            if (s.startsWith("ip")) {
                return s;
            }
        }
        return null;
    }




    public boolean isShutDown() {
        for (String s : props) {
            if (s.equals("shutdown")) {
                return true;
            }
        }
        return false;
    }
}

