package Views;

import Controllers.ListController;
import Controllers.ReportController;
import Controllers.TaskController;
import Data.ListRepository;
import Scheduler.NotificationScheduler;
import Services.NotificationService;
import Services.StorageService;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    private final NotificationScheduler scheduler;

    private final ListController listController;

    private final TaskController taskController;

    private final ReportController reportController;

    public MainFrame()
    {

        NotificationService notificationService = new NotificationService();
        this.scheduler = new NotificationScheduler(notificationService);

        this.listController = new ListController();
        this.taskController = new TaskController();
        this.reportController = new ReportController();

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
        switchWindow(new MainListMenu(this, listController));
    }

    public void showTaskMenu()
    {
        switchWindow(new TaskMenu(this, listController, taskController));
    }

    public void showAddTaskForm()
    {
        switchWindow(new AddTaskForm(this, taskController));
    }

    public void showEditTaskForm()
    {
        switchWindow(new EditTaskForm(this, taskController));
    }

    public void showReportForm()
    {
        switchWindow(new ReportForm(this, reportController));
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
