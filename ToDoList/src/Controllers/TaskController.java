package Controllers;

import CustomExceptions.EmptyInputException;
import CustomExceptions.TaskValidationException;
import Services.TaskService;
import Models.Category;
import Models.Priority;
import Models.Task;
import Models.TaskList;
import Observers.TaskPanelObserver;
import Views.ThemedDialog;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskController {

    private static final TaskService taskHandler = new TaskService();
    private static final ArrayList<TaskPanelObserver> taskObservers = new ArrayList<>();

    public void addTaskPanelObserver(TaskPanelObserver observer) {
        taskObservers.add(observer);
    }

    private void notifyTaskPanelObservers(TaskList list) //function to notify the observer
    {
        taskObservers.forEach(n -> n.onListStateChange(list));
    }

    public static void notifyTaskPanelObserversFromBackground(TaskList list) //function to notify the observer from the background
    {
        taskObservers.forEach(n -> n.onListStateChange(list));
    }

    public void createTask(TaskList list, String taskName, Category taskCategory, Priority taskPriority, LocalDate dueDate)
    {
        try {
            taskHandler.processCreateTask(list, taskName, taskCategory, taskPriority, dueDate);
            ThemedDialog.message("Task added successfully!", "Task Added");
            notifyTaskPanelObservers(list);
        } catch (TaskValidationException e) {
            ThemedDialog.message(e.getMessage(), "Error");
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
            ThemedDialog.message("Task edited successfully!", "Task Edited");
        } catch (EmptyInputException | TaskValidationException e) {
            ThemedDialog.message(e.getMessage(), "Error");
        }
    }

}