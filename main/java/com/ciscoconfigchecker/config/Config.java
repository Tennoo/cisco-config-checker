package com.ciscoconfigchecker.config;

import com.ciscoconfigchecker.config.item.ConfigItem;
import com.ciscoconfigchecker.config.item.InterfaceItem;
import com.ciscoconfigchecker.config.item.VlanItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Config {

    private List<String> lines;
    private String name;
    private List<ConfigItem> items;


    protected Config(String name) {
        this.items = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    void addLine(String line) {
        lines.add(line);
    }

    public List<String> getLines() {
        return this.lines;
    }

    void setInterfaceProperties(String faPort, ArrayList<String> portConfig) {
        this.items.add(new InterfaceItem(faPort, portConfig));

    }

    void setVlanProperties(String vlan, List<String> subCommand) {
        this.items.add(new VlanItem(vlan, subCommand));
    }

    List<ConfigItem> getConfigItems() {
        return this.items;
    }

    public List<String> getTaggedVlans() {
        List<String> vlans = new ArrayList<>();
        for (InterfaceItem i : getInterfaceItems()) {
            for (String s : i.getProps()) {
                if (s.contains("switchport access vlan")) {
                    if (!s.equals("switchport access vlan 1")) {
                        vlans.add(s.substring(s.indexOf("n") + 2));
                    }
                }
            }
        }
        //Remove duplicates
        List<String> filteredVlans = vlans.stream().distinct().collect(Collectors.toList());
        return filteredVlans;
    }


    public List<String> getTrunkedVlans() {
        List<String> trunkedVlans = new ArrayList<>();
        for (InterfaceItem i : getInterfaceItems()) {
            if (i.getTrunkedVlans() != null) {
                trunkedVlans.addAll(i.getTrunkedVlans());
            }
        }
        return trunkedVlans;
    }

    List<InterfaceItem> getInterfaceItems() {
        List<InterfaceItem> interfaceItems = new ArrayList<>();
        for (int i = 0; i < getConfigItems().size(); i++) {
            ConfigItem configItem = getConfigItems().get(i);
            if (configItem.type == ConfigItem.ItemType.INTERFACEITEM) {
                InterfaceItem item = (InterfaceItem) configItem;
                interfaceItems.add(item);
            }
        }
        return interfaceItems;
    }

    public List<VlanItem> getVlanItems() {
        List<VlanItem> vlanItems = new ArrayList<>();
        for (int i = 0; i < getConfigItems().size(); i++) {
            ConfigItem configItem = getConfigItems().get(i);
            if (configItem.type == ConfigItem.ItemType.VLANITEM) {
                VlanItem item = (VlanItem) configItem;
                vlanItems.add(item);
            }
        }
        return vlanItems;
    }


    public List<String> getVlanItemId() {
        List<String> ids = new ArrayList<>();
        for (VlanItem v : getVlanItems()) {
            ids.add(v.getId());
        }
        return ids;
    }

    public VlanItem getVlanItemById(String id) {
        for (VlanItem v : getVlanItems()) {
            if (v.getId().equals(id)) {
                return v;
            }
        }
        return null;
    }

}
