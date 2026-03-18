package Services;

import Controllers.ListController;
import Controllers.ReportController;
import Controllers.TaskController;
import Data.AppContext;
import Data.ListRepository;
import Models.Task;
import Models.TaskList;

import java.awt.*;
import java.util.List;

public class NotificationService {

    private static TrayIcon trayIcon;
    private int lastDueToday = 0;
    private int lastOverdue = 0;

    //This function initializes the service, if never called, the notifications will never be sent
    public static void initialize()
    {
        //checks if java notifications are supported
        if (!SystemTray.isSupported())
        {
            return;
        }

        //The part beloww creates the notification traz and icon
        SystemTray systemTray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage("notificationIcon.png");

        trayIcon = new TrayIcon(image, "ToDo App");
        trayIcon.setImageAutoSize(true);

        try {
            systemTray.add(trayIcon);
        }catch (AWTException e)
        {
            e.printStackTrace();
        }

    }

    //This function checks the amount of tasks due today and overdue
    public void checkTasks()
    {
        int dueToday = 0;
        int overdue = 0;

        List<TaskList> lists = ListRepository.getInstance().getAllLists();

        for (TaskList list: lists)
        {
            for(Task task: list.getTasks())
            {
                if (task.isDueToday())
                {
                    dueToday++;
                } else if (task.isOverdue()) {
                    overdue++;
                }
            }

        }


        //This part here makes sure no duplicate notifications are sent while the app is running
        if (dueToday != lastDueToday || overdue != lastOverdue)
        {
            String message = "";

            if (overdue != 0)
            {
                message = message + "Overdue: " + overdue;
            }

            if (dueToday != 0)
            {
                message = message + "\nDue Today: " + dueToday;
            }


            showNotification("Task Reminder", message);

            lastDueToday = dueToday;
            lastOverdue = overdue;
        }

        //this part notifies the observers from the outside. This function is supposed to run on the background
        ListController.notifyListObserversFromBackground(lists);
        ReportController.notifyReportObserverFromBackground(ListRepository.getInstance());
        if (AppContext.getInstance().getCurrentList() != null)
        {
            TaskController.notifyTaskPanelObserversFromBackground(AppContext.getInstance().getCurrentList());
        }


    }

    //This message shows the notification
    public void showNotification(String title, String message)
    {
        if (trayIcon != null)
        {
            //DisplayMessage makes the notification appear
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
        }
    }



}
