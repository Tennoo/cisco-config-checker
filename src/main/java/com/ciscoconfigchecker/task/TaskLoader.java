package com.ciscoconfigchecker.task;


import com.ciscoconfigchecker.gui.MainWindowController;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskLoader {
    private File taskFile;
    private List<Task> tasksList;
    private BufferedReader reader;
    private String currentLine;
    private int timesReadSameLine = 0;

    public TaskLoader() {
        this.taskFile = new File("tasks.txt");
        this.tasksList = new ArrayList<>();
    }

    public void load(){
        if (taskFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
                this.reader = reader;
                readNextLine();
                while (currentLine != null && timesReadSameLine < 2) {
                    timesReadSameLine++;
                    if (currentLine.startsWith("Task")) {
                        parseAndAddTask();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MainWindowController.addTaskIOErrorMessage("Tasks.txt could not be found. Check installation directory");
        }
    }

    private String readNextLine() throws IOException {
        currentLine = reader.readLine();
        timesReadSameLine = 0;
        return currentLine;
    }

    private String parseName(String line) {
        return line.substring(5, line.length() - 1);
    }

    public boolean validate() {
        boolean validated = true;
        if(!validateTaskIP()){
            validated = false;
        }
        if(!validateTaskContent()){
            validated = false;
        }
        if(!validateTaskList()){
            validated = false;
        }
        return validated;
    }

    private boolean validateTaskList() {
        if(tasksList.isEmpty()){
            MainWindowController.addTaskIOErrorMessage("No tasks could be found. Verify task list!");
            return false;
        }
        return true;
    }

    private boolean validateTaskContent() {
        StringBuilder sb = new StringBuilder("These tasks seems to be empty: ");
        int count = 0;
        for (Task t : tasksList) {
            if (!t.validate()) {
                sb.append(t.getName() + ", ");
                count++;
            }
        }
        if (count > 0) {
            MainWindowController.addTaskIOErrorMessage(sb.toString());
            return false;
        }
        return true;
    }
    private boolean validateTaskIP(){
        boolean isValid = true;
        for(Task t: tasksList){
            if (t.getFullCommand("ip") != null && t.getFullCommand("ip").length() > 2){
                String[] ip = t.getFullCommand("ip").split(" ");
                if(ip.length < 3){
                    MainWindowController.addTaskIOErrorMessage("Task IP subnet mask is not valid");
                    isValid = false;
                }
            }
        }
        return isValid;
    }
    private void parseAndAddTask() throws IOException {
        Task task = new Task(parseName(currentLine));
        readNextLine();
        //Todo logic cleanup
        //Todo error handling when no "}"
        //Todo kommentarer
        //Todo end of file reading error
        //Todo what if VLAN does not exist?
        while (!currentLine.startsWith("Task") && !currentLine.equals("}")) {
            if (currentLine.startsWith("vlans")) {
                task.setVlanProperties(parseProperties(currentLine));
            } else if (currentLine.startsWith("trunk")) {
                task.setTrunkProperties(parseProperties(currentLine));
            } else if (!currentLine.equals("") && !currentLine.startsWith("//")) {
                task.addTaskCommand(currentLine);
            }
            readNextLine();
        }
        if (currentLine.equals("}")) {
            readNextLine();
        }
        tasksList.add(task);

    }

    private List<String> parseProperties(String line) {
        String temp = line.substring(line.indexOf("[") + 1, line.length() - 1);
        List<String> tempList = Arrays.asList(temp.split(",[ ]*"));
        Collections.sort(tempList);
        return tempList;
    }

    public List<Task> getTasksList() {
        return tasksList;
    }

    public List<String> getTaskNames() {
        List<String> list = new ArrayList<>();
        for (Task t : getTasksList()) {
            list.add(t.getName());
        }
        return list;
    }

    public Task getTaskByName(String name) {
        for (Task t : getTasksList()) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

}
