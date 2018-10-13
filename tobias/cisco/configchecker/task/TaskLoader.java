package com.tobias.cisco.configchecker.task;

import com.tobias.cisco.configchecker.exceptions.CorruptTaskFileException;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TaskLoader {
    private File taskFile;
    private List<Task> tasksList;
    private BufferedReader reader;


    public TaskLoader() {

        URL taskPath = getClass().getResource("tasks.txt");
        taskFile = new File(taskPath.getPath());
        this.tasksList = new ArrayList<>();
    }


    public void load() {
        // TODO Try with resources
        try {
            reader = new BufferedReader(new FileReader(taskFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Task")) {
                    parseAndAddTask(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("s");
        } catch (IOException e) {
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
                System.out.println("close");
            }

        }
    }
    private String parseName(String line) {
        return line.substring(5, line.length() - 1);
    }

    private void parseAndAddTask(String line){
        Task task = new Task(parseName(line));

        //Todo check whitespace
        //Todo logic cleanup
        //Todo error handling when no "}"
        //Todo kommentarer
        //Todo end of file reading error
        try {
            while (!(line = reader.readLine()).startsWith("Task") && !line.equals("}"))
                if (line.startsWith("vlans")) {
                    task.setVlanProperties(parseProperties(line));
                } else if (line.startsWith("trunk")) {
                    task.setTrunkProperties(parseProperties(line));
                } else if (!line.equals("")) {
                    task.addTaskCommand(new Command(line));
                } else if(line == null){
                    throw new CorruptTaskFileException("Reached end of file while reading task:" + task.getName());
                }
            tasksList.add(task);
        }catch (CorruptTaskFileException e){
            e.getMessage();
        }catch (IOException e){
            e.getMessage();
        }

    }

    private List<Integer> parseProperties(String line) {
        String temp = line.substring(line.indexOf("[") + 1, line.length() - 1);
        List<String> tempList = Arrays.asList(temp.split(",[ ]*"));
        return tempList.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public List<Task> getTasksList() {
        return tasksList;
    }

}
