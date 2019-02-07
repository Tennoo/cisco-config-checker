package com.ciscoconfigchecker.task;


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

    public TaskLoader() {
        //TODO Error handling when no file
        this.taskFile = new File("tasks.txt");
        this.tasksList = new ArrayList<>();
    }

    public void load() throws FileNotFoundException{
        if (taskFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
                this.reader = reader;
                readNextLine();
                while (currentLine != null) {
                    if (currentLine.startsWith("Task")) {
                        parseAndAddTask();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new FileNotFoundException("Tasks.txt could not be found. Check installation direcectory");
        }
    }

    private String readNextLine() throws IOException {
        currentLine = reader.readLine();
        return currentLine;
    }

    private String parseName(String line) {
        return line.substring(5, line.length() - 1);
    }

    public boolean validate() {
        if (!validateTaskContent() || !validateTaskList()) {
            return false;
        }
        return true;
    }

    private boolean validateTaskList() {
        if (tasksList.isEmpty()) {
            return false;
        }
        return true;

    }

    private boolean validateTaskContent() {
        StringBuilder sb = new StringBuilder("These tasks seems to be empty: ");
        int count = 0;
        for (Task t : tasksList) {
            if (!t.validate()) {
                sb.append(t.getName());
                count++;
            }
        }
        if (count > 0) {
            return false;
        }
        return true;
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
