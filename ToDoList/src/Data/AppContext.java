package Data;

import Models.Task;
import Models.TaskList;

public class AppContext {

    private static AppContext instance;

    private TaskList currentList;
    private Task currentTask;

    private AppContext() {}

    public static AppContext getInstance() {
        if (instance == null)
        {
            instance = new AppContext();
        }

        return instance;
    }

    public void setCurrentList(TaskList currentList) {
        this.currentList = currentList;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public TaskList getCurrentList() {
        return currentList;
    }

    public Task getCurrentTask() {
        return currentTask;
    }
}
