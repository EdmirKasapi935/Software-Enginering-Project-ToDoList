package Data;

import Models.TaskList;

import java.util.ArrayList;
import java.util.List;

public class ListRepository {

    private static ListRepository instance;

    private final ArrayList<TaskList> taskLists = new ArrayList<>();

    private ListRepository(){}

    public static ListRepository getInstance()
    {
        if (instance == null)
        {
            instance = new ListRepository();
        }

        return instance;
    }

    public List<TaskList> getAllLists()
    {
        return taskLists;
    }



}
