package com.tobias.cisco.configchecker.config;


import java.io.*;
import java.util.ArrayList;

public class ConfigLoader {
    private Config config;
    private BufferedReader reader;

    public void load(File configFile){
        //Todo try with resources.
        try {
            reader = new BufferedReader(new FileReader(configFile));
            this.config = new Config(configFile.getName());
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
                parseAndAddLines(line);
            }
        }catch (FileNotFoundException e){
            //Todo logger
        }catch (IOException e){
            //Todo logger
        }
    }

    private void parseAndAddLines(String line) throws IOException{
        if (!line.equals("!")){
            if(line.startsWith("interface") && !((line = reader.readLine())).equals("!")){
                parseInterface(line);
            }
            else {
                config.addLine(line);
            }
        }
        }

    private void parseInterface(String line)throws IOException {
        System.out.println(line);
       // String faName = line.substring(line.indexOf("interface"));
        ArrayList<String> portPropList = null;
        while(!(line = reader.readLine()).equals("!")){
            String temp = line.trim();
            portPropList = new ArrayList<>();
            portPropList.add(temp);
        }
        config.setInterfaceProperties("test",portPropList);
    }

    public void getconfig(){
        config.printLines();
    }

}
