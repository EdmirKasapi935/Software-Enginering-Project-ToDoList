package Views;

import Models.TaskList;
import Scheduler.NotificationScheduler;
import Services.NotificationService;
import Views.AppDimensions;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    ArrayList<TaskList> taskLists = new ArrayList<>();
    NotificationService notificationService = new NotificationService();
    NotificationScheduler scheduler = new NotificationScheduler(notificationService);

    public MainFrame()
    {

        this.setSize(AppDimensions.GUI_SIZE);
        this.setTitle("ToDoList");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        NotificationService.initialize();
        scheduler.start();

        showMainListMenu();

        this.setVisible(true);

        addWindowListener(

                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {

                        scheduler.stop();

                    }
                }

        );

    }

    public void showMainListMenu()
    {
        switchWindow(new MainListMenu(this));
    }

    public void showTaskMenu()
    {
        switchWindow(new TaskMenu(this));
    }

    public void showAddTaskForm()
    {
        switchWindow(new AddTaskForm(this));
    }

    public void showReportForm()
    {
        switchWindow(new ReportForm(this));
    }

    public void showEditTaskForm()
    {
        switchWindow(new EditTaskForm(this));
    }

    private void switchWindow(JFrame frame)
    {
        this.setContentPane(frame.getContentPane());
        repaint();
        revalidate();
    }

}
