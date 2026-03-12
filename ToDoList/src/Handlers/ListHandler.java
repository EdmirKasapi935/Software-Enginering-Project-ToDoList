package Handlers;

import CustomExceptions.EmptyInputException;
import CustomExceptions.ListNameLengthExceededException;
import Models.TaskList;

import java.util.List;

public class ListHandler {

    private final Validator validator = new Validator();

    public List<TaskList> processCreateList(List<TaskList> taskLists, String listName) throws EmptyInputException, ListNameLengthExceededException {

        TaskList newList = new TaskList(validator.validateListNameLength(validator.validateNotNull(listName, "Name of a list cannot be empty!")));
        taskLists.add(newList);
        return taskLists;

    }

    public List<TaskList> processDeleteList(List<TaskList> taskLists, TaskList list) {
        taskLists.remove(list);
        return taskLists;
    }

    public TaskList processNameChange(TaskList taskList, String newName) throws EmptyInputException, ListNameLengthExceededException
    {
        taskList.setListName(validator.validateListNameLength(validator.validateNotNull(newName, "Cannot change list's name into an empty one!")));
        return  taskList;
    }

}
