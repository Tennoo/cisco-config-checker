package com.tobias.cisco.configchecker.config;


import java.io.*;
import java.util.ArrayList;

public class ConfigLoader {
    private Config config;
    private BufferedReader reader;

    public void load(File configFile) {
        //Todo try with resources.
        try {
            this.reader = new BufferedReader(new FileReader(configFile));
            this.config = new Config(configFile.getName());
            String line;
            while ((line = reader.readLine()) != null) {
                // System.out.println(line);
                parseAndAddLines(line);
            }
        } catch (FileNotFoundException e) {
            //Todo logger
        } catch (IOException e) {
            //Todo logger
        }
        config.printInterface();
    }

    private void parseAndAddLines(String line) throws IOException {
        if (!line.equals("!")) {
            if (line.startsWith("interface") && !line.startsWith("interface Vlan")) {
                parseInterface(line);
            } else if (line.startsWith("interface Vlan")){

            } else{
                config.addLine(line);
            }
        }
    }

    private void parseInterface(String line) throws IOException {
        String faName = line.substring(line.indexOf("interface "));
        ArrayList<String> portPropList = new ArrayList<>();
        while((line = reader.readLine()).startsWith(" switchport") && !line.startsWith("interface")){
            String trimmedLine = line.trim();
            portPropList.add(trimmedLine);
            System.out.println(trimmedLine);
        }
        config.setInterfaceProperties(faName, portPropList);
    }

    private void parseVlan

    public void getconfig() {
        config.printLines();
    }

}
