package Handlers;

import CustomExcptions.EmptyInputException;
import Models.TaskList;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListHandler {

    private final Validator validator = new Validator();

    public List<TaskList> processCreateList(List<TaskList> taskLists, String listName) throws EmptyInputException {

        TaskList newList = new TaskList(validator.validateNotNull(listName, "Name of a list cannot be empty!"));
        taskLists.add(newList);
        return taskLists;

    }

    public List<TaskList> processDeleteList(List<TaskList> taskLists, TaskList list) {
        taskLists.remove(list);
        return taskLists;
    }

}
