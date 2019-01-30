package com.ciscoconfigchecker.task;


import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TaskLoader {
    private File taskFile;
    private List<Task> tasksList;
    private BufferedReader reader;
    private String currentLine;


    public TaskLoader() {
        try {
            this.taskFile = new File(this.getClass().getResource("/tasks.txt").toURI());
        } catch (URISyntaxException e){e.printStackTrace();}
        this.tasksList = new ArrayList<>();
    }


    public void load() {
        try(BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
            this.reader = reader;

            while (currentLine != null) {
                readNextLine();
                if (currentLine.startsWith("Task")) {
                    parseAndAddTask();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private String readNextLine() throws IOException {
        currentLine = reader.readLine();
        return currentLine;
    }

    private String parseName(String line) {
        return line.substring(5, line.length() - 1);
    }

    private void parseAndAddTask() throws IOException {
        Task task = new Task(parseName(currentLine));
        readNextLine();
        //Todo logic cleanup
        //Todo error handling when no "}"
        //Todo kommentarer
        //Todo end of file reading error

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
