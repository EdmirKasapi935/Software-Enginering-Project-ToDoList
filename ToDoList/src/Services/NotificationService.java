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

    public static void initialize()
    {
        if (!SystemTray.isSupported())
        {
            return;
        }

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

        ListController.notifyListObserversFromBackground(lists);
        ReportController.notifyReportObserverFromBackground(ListRepository.getInstance());

        if (AppContext.getInstance().getCurrentList() != null)
        {
            TaskController.notifyTaskPanelObserversFromBackground(AppContext.getInstance().getCurrentList());
        }


    }

    public void showNotification(String title, String message)
    {
        if (trayIcon != null)
        {
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
        }
    }



}
