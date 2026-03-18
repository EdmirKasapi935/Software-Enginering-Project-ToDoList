package Models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskList implements Serializable {

    @Serial
    private static final long serialVersionUID = 935L;

    private String listName;

    private List<Task> tasks;

    public TaskList(String listName)
    {
        this.listName = listName;
        tasks = new ArrayList<>();
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }
    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public void addTask(Task task)
    {
        this.tasks.add(task);
    }

    public void removeTask(Task task)
    {
        this.tasks.remove(task);
    }

    public List<Task> getSortedList(SortCriterion criterion) //This function does the sorting for the list
    {
        List<Task> copyList = new ArrayList<>(tasks); //in order for the main data not to be permanently altered, a copy is passed instead, the sorting remains view level

        if (criterion == SortCriterion.BY_PRIORITY)
        {
            copyList = copyList.stream().sorted(Comparator.comparingInt( n -> n.getTaskPriority().getImportanceIndex() )).collect(Collectors.toList());
        }
        if (criterion == SortCriterion.BY_DUE_DATE)
        {
            copyList = copyList.stream().sorted(Comparator.comparingLong( n -> n.getDueDate().toEpochDay())).collect(Collectors.toList());
        }
        if (criterion == SortCriterion.BY_CATEGORY)
        {
            copyList = copyList.stream().sorted(Comparator.comparingInt( n -> n.getTaskCategory().getCategoryIndex() )).collect(Collectors.toList());
        }

        return copyList;
    }

}
