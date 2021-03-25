/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.jku.se.timemanager;

import at.jku.se.timemanager.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author reinhold
 */
public class TaskTest {
    
   private final Task t= new Task("MyTask");

    /**
     * Test of setTaskName method, of class Task.
     */
    @Test
    void setTaskName() {
        t.setTaskName("MyNewTask");
        assertEquals(t.getTaskName(), "MyNewTask");
    }
    
}
