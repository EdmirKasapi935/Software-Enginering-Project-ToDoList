package Controllers;

import CustomExceptions.EmptyInputException;
import Handlers.TaskHandler;
import Models.Category;
import Models.Priority;
import Models.Task;
import Models.TaskList;

import javax.swing.*;
import java.util.Date;

public class TaskController {

    private static final TaskHandler taskHandler = new TaskHandler();

    public void createTask(TaskList list, String taskName, Category taskCategory, Priority taskPriority, Date dueDate)
    {
        try {
            taskHandler.processCreateTask(list, taskName, taskCategory, taskPriority, dueDate);
            JOptionPane.showMessageDialog(null,"Task added successfully!", "Task Added", JOptionPane.INFORMATION_MESSAGE);
        }catch (EmptyInputException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
