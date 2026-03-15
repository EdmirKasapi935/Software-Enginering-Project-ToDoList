package Handlers;

import CustomExceptions.EmptyInputException;
import CustomExceptions.ListNameLengthExceededException;
import CustomExceptions.ListNameUnavailableException;
import CustomExceptions.TaskValidationException;
import Models.Category;
import Models.Priority;
import Models.Task;
import Models.TaskList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Validator {

    public List<Object> validateTaskCreation(String taskText, Category category, Priority priority, LocalDate dueDate) throws TaskValidationException {
        List<Object> taskData = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        if (taskText.trim().equalsIgnoreCase("") || taskText.isBlank())
        {
            errors.add("Task's title cannot be empty.");
        } else if (taskText.length() > 35) {
            errors.add("Task's title cannot exceed 35 characters.");
        }
        else
        {
            taskData.add(taskText);
        }

        if(priority == null)
        {
            errors.add("Please select a priority level.");
        }
        else
        {
            taskData.add(priority);
        }

        if(category == null)
        {
            errors.add("Please select a task category.");
        }
        else
        {
            taskData.add(category);
        }

        if (dueDate == null)
        {
            errors.add("Please enter a due date.");
        }
        else if (dueDate.isBefore(LocalDate.now())) {
            errors.add("Due date cannot be before today.");
        }
        else
        {
            taskData.add(dueDate);
        }

        if (!errors.isEmpty())
        {
            throw new TaskValidationException(String.join("\n", errors));
        }

        return taskData;
    }

    public List<Object> validateTaskEditing(Task task,String taskText, Category category, Priority priority, LocalDate dueDate) throws TaskValidationException
    {
        List<Object> taskData = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        if (taskText.trim().equalsIgnoreCase("") || taskText.isBlank())
        {
            errors.add("Task's title cannot be empty.");
        } else if (taskText.length() > 35) {
            errors.add("Task's title cannot exceed 35 characters.");
        }
        else
        {
            taskData.add(taskText);
        }

        if(priority == null)
        {
            errors.add("Please select a priority level.");
        }
        else
        {
            taskData.add(priority);
        }

        if(category == null)
        {
            errors.add("Please select a task category.");
        }
        else
        {
            taskData.add(category);
        }

        if (dueDate == null)
        {
            errors.add("Please enter a due date.");
        }
        else if (!dueDate.equals(task.getDueDate()) &&  dueDate.isBefore(LocalDate.now())) {
            errors.add("Due date cannot be before today.");
        }
        else
        {
            taskData.add(dueDate);
        }

        if (!errors.isEmpty())
        {
            throw new TaskValidationException(String.join("\n", errors));
        }

        return taskData;
    }

    public String validateNotNull(String input, String message) throws EmptyInputException
    {
        if(input.trim().equals("") || input.isBlank())
        {
            throw new EmptyInputException(message);
        }

        return input;
    }

    public String validateListNameLength(String listName) throws ListNameLengthExceededException
    {
        if (listName.length() > 40)
        {
            throw new ListNameLengthExceededException("Name of a list cannot exceed 40 characters!");
        }

        return listName;
    }

    public String validateListNameAvailability(List<TaskList> lists, String listName) throws ListNameUnavailableException
    {
        if (lists.stream().anyMatch(n -> n.getListName().equals( listName)))
        {
            throw new ListNameUnavailableException("A list with this name already exists!");
        }

        return listName;
    }
}
