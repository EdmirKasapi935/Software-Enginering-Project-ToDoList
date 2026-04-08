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
import Views.ThemedDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListController {

    private static final ListService listHandler = new ListService();
    private static final ExportService exportHandler = new ExportService();

    private static final ArrayList<ListObserver> listObservers = new ArrayList<>(); //this is where the list observers are notified, they observe the state of the list
    private static final ArrayList<ListNameObserver> nameObservers = new ArrayList<>(); //this is where the list name observers are stored, they keep track of the name of the list for it to be displayed in the task menu

    public void addListObserver(ListObserver observer) //this function adds a list observer
    {
        listObservers.add(observer);
    }

    public void addNameObserver(ListNameObserver observer) //tis function adds a name observer
    {
        nameObservers.add(observer);
    }

    private void notifyListObservers(List<TaskList> taskLists) //the list observers are notified with this function
    {
        listObservers.forEach(n -> n.onListsCollectionUpdated(taskLists));
    }

    public static void notifyListObserversFromBackground(List<TaskList> taskLists) //the list observers are notified with this function, but from the background, used by the notification service to update the menu
    {
        listObservers.forEach(n -> n.onListsCollectionUpdated(taskLists));
    }

    private void notifyNameObservers(TaskList list)//the list observers are notified with this function
    {
        nameObservers.forEach(n -> n.onListNameChange(list));
    }

    public void createList(List<TaskList> lists, String listName)
    {
        try {
            notifyListObservers(listHandler.processCreateList(lists, listName));
        } catch (EmptyInputException | ListNameLengthExceededException | ListNameUnavailableException e) {
            ThemedDialog.message(e.getMessage(), "Error");
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
        } catch (EmptyInputException | ListNameLengthExceededException | ListNameUnavailableException e) {
            ThemedDialog.message(e.getMessage(), "Error");
        }
    }

    public void exportTaskList(File file, TaskList list)
    {
        try {
            exportHandler.processExportTaskList(file, list);
            ThemedDialog.message("Your list was exported successfully!", "List Exported");
        } catch (IOException | EmptyListException | EmptyInputException e) {
            ThemedDialog.message(e.getMessage(), "Error");
        }
    }
}