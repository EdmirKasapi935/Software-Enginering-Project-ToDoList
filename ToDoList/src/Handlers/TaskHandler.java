package Handlers;

import CustomExceptions.EmptyInputException;
import Models.*;

import java.util.Date;

public class TaskHandler {

    private static final Validator validator = new Validator();

    public void processCreateTask(TaskList list, String taskTitle, Category taskCategory, Priority taskPriority, Date dueDate) throws EmptyInputException
    {
        Task newTask = new Task( validator.validateNotNull(taskTitle, "Task title cannot be empty!"), taskPriority, taskCategory, dueDate, Status.UNDONE);
        list.addTask(newTask);
    }

}
