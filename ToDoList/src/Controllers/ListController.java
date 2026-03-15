package Controllers;

import CustomExceptions.EmptyInputException;
import CustomExceptions.EmptyListException;
import CustomExceptions.ListNameLengthExceededException;
import CustomExceptions.ListNameUnavailableException;
import Services.ExportService;
import Services.ListService;
import Models.TaskList;
import Observers.ListNameObserver;
import Observers.ListObserver;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListController {

    private static final ListService listHandler = new ListService();
    private static final ExportService exportHandler = new ExportService();

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
        }catch (EmptyInputException | ListNameLengthExceededException | ListNameUnavailableException e){
            JOptionPane.showMessageDialog(new Button("OK"), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void deleteList(List<TaskList> lists, TaskList taskList)
    {
        notifyListObservers(listHandler.processDeleteList(lists, taskList));
    }

    public void changeListName(List<TaskList> taskLists, TaskList taskList, String newName)
    {
        try {
            notifyNameObservers(listHandler.processNameChange(taskLists, taskList, newName));
        }catch (EmptyInputException | ListNameLengthExceededException | ListNameUnavailableException e)
        {
            JOptionPane.showMessageDialog(new Button("OK"), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void exportTaskList(File file, TaskList list)
    {
        try {
            exportHandler.processExportTaskList(file, list);
            JOptionPane.showMessageDialog(null, "Your list was exported successfully!", "Task List Exported", JOptionPane.INFORMATION_MESSAGE);
        }catch (IOException | EmptyListException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
