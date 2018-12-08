package com.tobias.configchecker.config;


import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

public class ConfigLoader {
    private Config config;
    private BufferedReader reader;
    private int lineNumber = 0;
    private String currentLine;

    public void load(File configFile) {
        //Todo try with resources.
        try {
            this.reader = new BufferedReader(new FileReader(configFile));
            this.config = new Config(configFile.getName());
            readNextLine();
            while (currentLine != null) {
                parseAndAddLines(currentLine);
            }
        } catch (FileNotFoundException e) {
            //Todo logger
        } catch (IOException e) {
            //Todo logger
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                //Todo logger
            }
        }
    }

    private void parseAndAddLines(String line) throws IOException {
        if (!line.equals("!") && !line.equals("")) {
            if (line.startsWith("interface") && !line.startsWith("interface Vlan")) {
                parseInterface(line);
            } else if (line.startsWith("interface Vlan")) {
                parseVlan(line);
            } else {
                config.addLine(line);

            }
        }
    }

    private String readNextLine() throws IOException {
        this.lineNumber++;
        currentLine = reader.readLine();
        return currentLine;
    }

    private void parseInterface(String line) throws IOException {
        String faName = line.substring(line.indexOf("interface "));
        ArrayList<String> portPropList = new ArrayList<>();
        while (!readNextLine().startsWith("interface") && !line.equals("!")){
            if(line.trim().startsWith("switchport")) {
                String trimmedLine = line.trim();
                portPropList.add(trimmedLine);
            }
        }
        config.setInterfaceProperties(faName, portPropList);
    }

    private void parseVlan(String line) throws IOException {
        String vlanName = line.substring(10);
        ArrayList<String> vlanSubCommands = new ArrayList<>();
        while (!readNextLine().startsWith("interface") && !line.equals("!")) {
            String trimmedLine = line.trim();
            vlanSubCommands.add(trimmedLine);
        }
        readNextLine();
        config.setVlanProperties(vlanName, vlanSubCommands);
    }

    public Config getConfig() {
        return this.config;
    }
}
