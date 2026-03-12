package Models;

import java.util.ArrayList;

public class TaskList {

    private String listName;

    private ArrayList<Task> tasks;

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
    public ArrayList getTasks() {
        return tasks;
    }

    public void addTask(Task task)
    {
        this.tasks.add(task);
    }

    public void removeTask(Task task)
    {
        this.tasks.remove(task);
    }

}
