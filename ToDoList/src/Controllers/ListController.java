package Controllers;

import CustomExceptions.EmptyInputException;
import CustomExceptions.ListNameLengthExceededException;
import Handlers.ListHandler;
import Models.TaskList;
import Observers.ListObserver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListController {

    private static final ListHandler listHandler = new ListHandler();

    private final ArrayList<ListObserver> listObservers = new ArrayList<>();

    public void addListObserver(ListObserver observer)
    {
        this.listObservers.add(observer);
    }

    private void notifyListObservers(List<TaskList> taskLists)
    {
        this.listObservers.stream().forEach( n -> n.onListsCollectionUpdated(taskLists));
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

}
