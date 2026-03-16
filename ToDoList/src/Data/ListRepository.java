package Data;

import Models.TaskList;
import Services.StorageService;

import java.util.ArrayList;
import java.util.List;

public class ListRepository {

    private static final ListRepository instance = new ListRepository();

    private final List<TaskList> taskLists;

    private ListRepository(){
        taskLists = StorageService.loadTasks();
    }

    public static ListRepository getInstance()
    {
        return instance;
    }

    public List<TaskList> getAllLists()
    {
        return taskLists;
    }



}
