package Services;

import CustomExceptions.EmptyInputException;
import CustomExceptions.ListNameLengthExceededException;
import CustomExceptions.ListNameUnavailableException;
import Models.TaskList;

import java.util.List;

public class ListService {

    private final Validator validator = new Validator();

    //This function is responsible for creating a list object and adding it to the task list collection
    public List<TaskList> processCreateList(List<TaskList> taskLists, String listName) throws EmptyInputException, ListNameLengthExceededException, ListNameUnavailableException {
                           //the input name passes through the validation functions
        TaskList newList = new TaskList(validator.validateListNameAvailability( taskLists, validator.validateListNameLength(validator.validateNotNull(listName, "Name of a list cannot be empty!"))));
        taskLists.add(newList); //The task list is added to the collection
        return taskLists; //the task list is returned for the observer to be notified

    }

    //This function is responsible for deleting a list from the collection
    public List<TaskList> processDeleteList(List<TaskList> taskLists, TaskList list) {
        taskLists.remove(list); //The list is removed from the collection and then returned for the observer to be notified
        return taskLists;
    }

    //This function is responsible for changing a list's name
    public TaskList processNameChange(List<TaskList> taskLists, TaskList taskList, String newName) throws EmptyInputException, ListNameLengthExceededException, ListNameUnavailableException
    {
        //simply put, the new name is validated and then set on the list
        taskList.setListName(validator.validateListNameAvailability(taskLists, validator.validateListNameLength(validator.validateNotNull(newName, "Cannot change list's name into an empty one!"))));
        return  taskList; //returned for the observer to be notified
    }

}
