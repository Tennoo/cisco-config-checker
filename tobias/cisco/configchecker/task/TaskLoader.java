package com.tobias.cisco.configchecker.task;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TaskLoader {

    private URL taskPath;
    private File taskFile;
    private List<Task> tasks;

    public TaskLoader() {

        this.taskPath = getClass().getResource("tasks.txt");
        taskFile = new File(taskPath.getPath());
        this.tasks = new ArrayList<Task>();
    }


    public void load() {
        BufferedReader taskReader = null;

        // TODO Try with resources
        try {
            taskReader = new BufferedReader(new FileReader(taskFile));
            String line;
            while ((line = taskReader.readLine()) != null) {
                read(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("s");
        } catch (IOException e) {

        } finally {
            try {
                taskReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private Task read(String line){
        if(line.startsWith("Task")){
            Task task = new Task();
            task.setName(parseName(line));
            System.out.println(task.getName());
            if (line.startsWith("interface")){

            }
        }
        // TODO Custom exception (CorrupConfigException?)
        return null;

    }

    private String parseName(String line){
        String name = line.substring(5, line.length() - 1);
        return name;
    }

    private boolean validate(String line){

    }

}
