package Data;

import Models.TaskList;

public class AppContext {

    private static AppContext instance;

    private TaskList currentList;

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

    public TaskList getCurrentList() {
        return currentList;
    }
}
