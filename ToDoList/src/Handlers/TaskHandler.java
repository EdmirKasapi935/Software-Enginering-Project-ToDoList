package Handlers;

import CustomExceptions.TaskValidationException;
import Models.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TaskHandler {

    private static final Validator validator = new Validator();

    public void processCreateTask(TaskList list, String taskTitle, Category taskCategory, Priority taskPriority, LocalDate dueDate) throws TaskValidationException
    {
        List<Object> taskData = validator.validateTaskCreation(taskTitle, taskCategory, taskPriority, dueDate);
        Task newTask = new Task((String) taskData.get(0), (Priority) taskData.get(1), (Category) taskData.get(2), (LocalDate) taskData.get(3));
        list.addTask(newTask);
    }

    public TaskList processDeleteTask(TaskList list, Task task)
    {
        list.removeTask(task);
        return list;
    }

    public void processEditTask(Task task, String taskTitle, Category taskCategory, Priority taskPriority, LocalDate dueDate) throws TaskValidationException
    {
        List<Object> taskData = validator.validateTaskEditing(task, taskTitle, taskCategory, taskPriority, dueDate);

        task.setTaskText((String) taskData.get(0));
        task.setTaskPriority((Priority) taskData.get(1));
        task.setTaskCategory((Category) taskData.get(2));
        task.setDueDate((LocalDate) taskData.get(3));
    }

}
