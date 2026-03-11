package Observers;

import Models.TaskList;

import java.util.List;

public interface ListObserver {

    void onListsCollectionUpdated(List<TaskList> taskLists);

}
