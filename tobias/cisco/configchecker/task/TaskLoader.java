package com.tobias.cisco.configchecker.task;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TaskLoader {
    private File taskFile;
    private List<Task> tasksList;


    public TaskLoader() {

        URL taskPath = getClass().getResource("tasks.txt");
        taskFile = new File(taskPath.getPath());
        this.tasksList = new ArrayList<>();
    }


    public void load() {
        BufferedReader taskReader = null;
        // TODO Try with resources
        try {
            taskReader = new BufferedReader(new FileReader(taskFile));
            String line;
            while ((line = taskReader.readLine()) != null) {
                if (line.startsWith("Task")) {
                    parseAndAddTask(line, taskReader);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("s");
        } catch (IOException e) {
        } finally {
            try {
                taskReader.close();
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

    private void parseAndAddTask(String line, BufferedReader reader) throws IOException{
        Task task = new Task(parseName(line));

        //Todo check whitespace
        //Todo logic cleanup
        //Todo error handling when no "}"
        //Todo kommentarer
        //Todo end of file reading error
        line = reader.readLine();
        boolean hasMore = line != null;
        while(hasMore){
            if(line.startsWith("vlans")){
                task.setVlanProperties(parseProperties(line));
            }
            else if(line.startsWith("trunk")){
                task.setTrunkProperties(parseProperties(line));
            }
            else if(line.equals("}")){
                hasMore = false;
            }
            else {
                task.addTaskCommand(new Command(line));

            }
            if(hasMore) {
                tasksList.add(task);
                line = reader.readLine();
                hasMore = line != null;
            }
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
