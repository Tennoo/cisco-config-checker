package com.tobias.cisco.configchecker.config;

import java.io.*;

public class ConfigLoader {
    Config config;

    public void load(File configFile){
        //Todo try with resources.
        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            this.config = new Config(configFile.getName());
            String line;
            while((line = reader.readLine()) != null){

            }
        }catch (FileNotFoundException e){
            //Todo logger
        }catch (IOException e){
            //Todo logger
        }
    }

    private void parseAndAddLines(String line){
        if (!line.equals("!") || !line.contains("version 1")){
            if(line.startsWith("interface"))
            config.addLine(line);
        }
    }

    private void parseInterface(String line){

    }

}
