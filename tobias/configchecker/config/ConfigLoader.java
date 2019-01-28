package com.tobias.configchecker.config;
import java.io.*;
import java.util.ArrayList;

public class ConfigLoader {
    private Config config;
    private BufferedReader reader;
    private int lineNumber = 0;
    private String currentLine;

    public void load(File configFile) {
        //Todo try with resources.
        try(BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            this.reader = reader;
            this.config = new Config(configFile.getName());
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



    private boolean validate(Config config){
        if(this.config.getVlanItems() == null || this.config.getInterfaceItems() == null){
            return false;
        }
        return true;
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
        if(!currentLine.startsWith("interface")) {
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
        while (!readNextLine().startsWith("interface")){
            if(currentLine.trim().startsWith("switchport")) {
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
