package com.tobias.cisco.configchecker;

import com.tobias.cisco.configchecker.task.TaskLoader;

public class Main {

    public static void main(String[] args) {
        TaskLoader taskLoader = new TaskLoader();
        taskLoader.load();

    }
}
