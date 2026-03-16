package Controllers;

import CustomExceptions.TaskValidationException;
import Services.TaskService;
import Models.Category;
import Models.Priority;
import Models.Task;
import Models.TaskList;
import Observers.TaskPanelObserver;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskController {

    private static final TaskService taskHandler = new TaskService();

    private static final ArrayList<TaskPanelObserver> taskObservers = new ArrayList<>();

    public void addTaskPanelObserver(TaskPanelObserver observer)
    {
        taskObservers.add(observer);
    }

    private void notifyTaskPanelObservers(TaskList list)
    {
        taskObservers.forEach(n -> n.onListStateChange(list));
    }

    public static void notifyTaskPanelObserversFromBackground(TaskList list)
    {
        taskObservers.forEach(n -> n.onListStateChange(list));
    }

    public void createTask(TaskList list, String taskName, Category taskCategory, Priority taskPriority, LocalDate dueDate)
    {
        try {
            taskHandler.processCreateTask(list, taskName, taskCategory, taskPriority, dueDate);
            JOptionPane.showMessageDialog(null,"Task added successfully!", "Task Added", JOptionPane.INFORMATION_MESSAGE);
        }catch (TaskValidationException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteTask(TaskList list, Task task)
    {
        notifyTaskPanelObservers(taskHandler.processDeleteTask(list, task));
    }

    public void editTask(Task task, String taskName, Category taskCategory, Priority taskPriority, LocalDate dueDate)
    {
        try {
            taskHandler.processEditTask(task, taskName, taskCategory, taskPriority, dueDate);
            JOptionPane.showMessageDialog(null,"Task edited successfully!", "Task Edited", JOptionPane.INFORMATION_MESSAGE);
        }catch (TaskValidationException e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
