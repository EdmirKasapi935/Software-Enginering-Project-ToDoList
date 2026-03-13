package Observers;

import Models.TaskList;

public interface TaskPanelObserver {

    public void onListStateChange(TaskList list);

}
