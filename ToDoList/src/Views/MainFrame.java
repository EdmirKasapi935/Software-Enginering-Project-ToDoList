package Views;

import Data.ListRepository;
import Models.TaskList;
import Scheduler.NotificationScheduler;
import Services.NotificationService;
import Services.StorageService;
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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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

                        onExit();

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

    private void onExit() {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {

            dispose();
            StorageService.saveLists(ListRepository.getInstance().getAllLists());
            scheduler.stop();
            System.exit(0);
        }

    }

}
