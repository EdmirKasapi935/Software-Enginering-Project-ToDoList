package Controllers;

import CustomExceptions.EmptyInputException;
import CustomExceptions.ListNameLengthExceededException;
import Handlers.ListHandler;
import Models.TaskList;
import Observers.ListNameObserver;
import Observers.ListObserver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListController {

    private static final ListHandler listHandler = new ListHandler();

    private final ArrayList<ListObserver> listObservers = new ArrayList<>();
    private final ArrayList<ListNameObserver> nameObservers = new ArrayList<>();

    public void addListObserver(ListObserver observer)
    {
        this.listObservers.add(observer);
    }

    public void addNameObserver(ListNameObserver observer)
    {
        this.nameObservers.add(observer);
    }

    private void notifyListObservers(List<TaskList> taskLists)
    {
        this.listObservers.forEach(n -> n.onListsCollectionUpdated(taskLists));
    }

    private void notifyNameObservers(TaskList list)
    {
        this.nameObservers.forEach(n -> n.onListNameChange(list));
    }

    public void createList(List<TaskList> lists, String listName)
    {
        try {
            notifyListObservers(listHandler.processCreateList(lists, listName));
        }catch (EmptyInputException | ListNameLengthExceededException e){
            JOptionPane.showMessageDialog(new Button("OK"), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void deleteList(List<TaskList> lists, TaskList taskList)
    {
        notifyListObservers(listHandler.processDeleteList(lists, taskList));
    }

    public void changeListName(TaskList taskList, String newName)
    {
        try {
            notifyNameObservers(listHandler.processNameChange(taskList, newName));
        }catch (EmptyInputException | ListNameLengthExceededException e)
        {
            JOptionPane.showMessageDialog(new Button("OK"), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
