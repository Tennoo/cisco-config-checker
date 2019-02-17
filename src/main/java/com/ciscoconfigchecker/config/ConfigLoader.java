package com.ciscoconfigchecker.config;

import com.ciscoconfigchecker.gui.MainWindowController;

import java.io.*;
import java.util.ArrayList;

public class ConfigLoader {
    private Config config;
    private BufferedReader reader;
    private int lineNumber;
    private String currentLine;

    public void load(File configFile) {
        //Todo try with resources.
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            this.reader = reader;
            this.config = new Config(configFile.getName());
            lineNumber = 0;
            readNextLine();
            while (currentLine != null) {
                parseAndAddLines();
            }
        } catch (FileNotFoundException e) {
            //Todo logger
        } catch (IOException e) {
            //Todo logger
        }
    }


    public boolean validate() {
        int count = 0;
        if (this.config.getVlanItems().size() == 0){
            MainWindowController.addConfigIoErrorMessage("Config is corrupt: No VLANS were found");
            count++;
        }
        if(this.config.getInterfaceItems().size() == 0){
            MainWindowController.addConfigIoErrorMessage("Config is corrupt: No port config was found");
            count++;
        }
        if (getLineNumber() == 0){
            MainWindowController.addConfigIoErrorMessage("Config is corrupt: Config is empty");
            count++;
        }
        return (count == 0);
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    private void parseAndAddLines() throws IOException {
        if (!currentLine.equals("!") && !currentLine.equals("")) {
            if (currentLine.startsWith("interface") && !currentLine.startsWith("interface Vlan")) {
                parseInterface();
            } else if (currentLine.startsWith("interface Vlan")) {
                parseVlan();
            } else {
                config.addLine(currentLine);
            }
        }
        if (!currentLine.startsWith("interface")) {
            readNextLine();
        }
    }

    private String readNextLine() throws IOException {
        this.lineNumber++;
        currentLine = reader.readLine();
        return currentLine;
    }

    private void parseInterface() throws IOException {
        String faName = currentLine.substring(currentLine.indexOf("interface "));
        ArrayList<String> portPropList = new ArrayList<>();
        while (!readNextLine().startsWith("interface")) {
            if (currentLine.trim().startsWith("switchport")) {
                String trimmedLine = currentLine.trim();
                portPropList.add(trimmedLine);
            }
        }
        config.setInterfaceProperties(faName, portPropList);
    }

    private void parseVlan() throws IOException {
        String vlanName = currentLine.substring(10);
        ArrayList<String> vlanSubCommands = new ArrayList<>();
        while (!readNextLine().startsWith("interface") && !currentLine.equals("!")) {
            String trimmedLine = currentLine.trim();
            vlanSubCommands.add(trimmedLine);
        }
        config.setVlanProperties(vlanName, vlanSubCommands);
    }

    public Config getConfig() {
        return this.config;
    }
}
