package Views;

import Controllers.ReportController;
import Data.ListRepository;
import Models.Priority;
import Models.ReportData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ReportForm extends JFrame {

    private final ReportController reportController = new ReportController();

    private ReportData taskReport;

    private JPanel formPanel;
    private JLabel pageTitle;
    private JLabel totalTasksTitle;
    private JLabel totalTasksValueLabel;
    private JLabel completedTasksTitle;
    private JLabel completedTasksValueLabel;
    private JLabel pendingTasksTitle;
    private JLabel dueTodayTasksTitle;
    private JLabel pendingTasksValueLabel;
    private JLabel dueTodayTasksValueLabel;
    private JLabel overdueTasksTitle;
    private JLabel overdueTasksValueLabel;
    private JPanel prioritiesSection;
    private JLabel highPriorityTitle;
    private JLabel mediumPriorityTitle;
    private JLabel lowPriorityTitle;
    private JLabel highPriorityValueLabel;
    private JLabel mediumPriorityValueLabel;
    private JLabel lowPriorityValueLabel;
    private JLabel prioritySectionLabel;
    private JPanel ButtonPanel;
    private JButton mainMenuButton;
    private JButton exportButton;
    private JPanel mainPanel;

    public ReportForm(MainFrame frame) {

        this.setContentPane(mainPanel);

        this.taskReport = reportController.generateReport(ListRepository.getInstance());

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showMainListMenu();
            }
        });

        showReportValues(this.taskReport);
    }

    private void showReportValues(ReportData taskReport)
    {
        totalTasksValueLabel.setText(String.valueOf(taskReport.getTotalTasks()));
        completedTasksValueLabel.setText(String.valueOf(taskReport.getCompletedTasks()));
        pendingTasksValueLabel.setText(String.valueOf(taskReport.getPendingTasks()));
        dueTodayTasksValueLabel.setText(String.valueOf(taskReport.getDueTodayTasks()));
        overdueTasksValueLabel.setText(String.valueOf(taskReport.getOverdueTasks()));

        Map<Priority, Integer> priorityCounts = taskReport.getPriorityCounts();

        if (priorityCounts.get(Priority.HIGH) != null)
        {
            highPriorityValueLabel.setText(String.valueOf(priorityCounts.get(Priority.HIGH)));
        }
        else
        {
            highPriorityValueLabel.setText(String.valueOf(0));
        }

        if (priorityCounts.get(Priority.MEDIUM) != null)
        {
            mediumPriorityValueLabel.setText(String.valueOf(priorityCounts.get(Priority.MEDIUM)));
        }
        else
        {
            mediumPriorityValueLabel.setText(String.valueOf(0));
        }

        if (priorityCounts.get(Priority.LOW) != null)
        {
            lowPriorityValueLabel.setText(String.valueOf(priorityCounts.get(Priority.LOW)));
        }
        else
        {
            lowPriorityValueLabel.setText(String.valueOf(0));
        }
    }
}
