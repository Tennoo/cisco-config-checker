package com.tobias.configchecker.config.comparator;

import com.tobias.configchecker.config.Config;
import com.tobias.configchecker.task.Task;

 class VlanComparator {
    private Task task;
    private Config config;

     public void setTask(Task task) {
         this.task = task;
     }

     public void setConfig(Config config) {
         this.config = config;
     }

     public VlanComparator(){


    }
}
