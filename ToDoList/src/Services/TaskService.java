package Services;

import CustomExceptions.EmptyInputException;
import CustomExceptions.TaskValidationException;
import Models.*;

import java.time.LocalDate;
import java.util.List;

public class TaskService {

    private static final Validator validator = new Validator();

    //this function is responsible for creating a task and adding it to the list
    public void processCreateTask(TaskList list, String taskTitle, Category taskCategory, Priority taskPriority, LocalDate dueDate) throws TaskValidationException
    {

        List<Object> taskData = validator.validateTaskCreation(taskTitle, taskCategory, taskPriority, dueDate);//the task data is retrieved from the validation function
        Task newTask = new Task((String) taskData.get(0), (Priority) taskData.get(1), (Category) taskData.get(2), (LocalDate) taskData.get(3)); //the data is passed to a new task object
        list.addTask(newTask);//The task is added to the list
    }

    //this function is responsible for deleting a task from a list
    public TaskList processDeleteTask(TaskList list, Task task)
    {
        list.removeTask(task); //task is removed from the list
        return list; //The list is returned to update the observer
    }

    //this function is responsible for editing a task
    public void processEditTask(Task task, String taskTitle, Category taskCategory, Priority taskPriority, LocalDate dueDate) throws EmptyInputException,TaskValidationException
    {
        //The data is retrieved fro, the validation function
        List<Object> taskData = validator.validateTaskEditing(task, taskTitle, taskCategory, taskPriority, dueDate);

        //The task's attributes are changed
        task.setTaskText((String) taskData.get(0));
        task.setTaskPriority((Priority) taskData.get(1));
        task.setTaskCategory((Category) taskData.get(2));
        task.setDueDate((LocalDate) taskData.get(3));
    }

}
