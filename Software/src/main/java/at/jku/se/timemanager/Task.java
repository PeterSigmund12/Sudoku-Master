/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.jku.se.timemanager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author reinhold
 */
public class Task {
    
    private String taskName;
     
    public Task(String name) {
        this.taskName= name;
    }
    
    
    public void setTaskName(String name) {
        if (name != null && name.length() > 0)
            this.taskName= name;
    }  
    
    public String getTaskName() {
        return taskName;
    }

}
