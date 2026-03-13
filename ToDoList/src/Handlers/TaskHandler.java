package Handlers;

import CustomExceptions.EmptyInputException;
import CustomExceptions.TaskTextLengthExceededException;
import Models.*;

import java.util.Date;

public class TaskHandler {

    private static final Validator validator = new Validator();

    public void processCreateTask(TaskList list, String taskTitle, Category taskCategory, Priority taskPriority, Date dueDate) throws EmptyInputException, TaskTextLengthExceededException
    {
        Task newTask = new Task(validator.validateTaskTextLength( validator.validateNotNull(taskTitle, "Task title cannot be empty!")), taskPriority, taskCategory, dueDate, Status.UNDONE);
        list.addTask(newTask);
    }

    public TaskList processDeleteTask(TaskList list, Task task)
    {
        list.removeTask(task);
        return list;
    }

    public void processEditTask(Task task, String taskName, Category taskCategory, Priority taskPriority, Date dueDate) throws EmptyInputException, TaskTextLengthExceededException
    {
        String validatedName = validator.validateTaskTextLength(validator.validateNotNull(taskName, "Task title cannot be empty"));

        task.setTaskText(validatedName);
        task.setTaskCategory(taskCategory);
        task.setTaskPriority(taskPriority);
        task.setDueDate(dueDate);
    }

}
