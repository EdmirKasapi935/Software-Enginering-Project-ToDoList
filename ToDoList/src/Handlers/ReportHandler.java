package Handlers;

import Models.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportHandler {

    public ReportData processGenerateReport(List<TaskList> taskLists)
    {
        ReportData report = new ReportData();

        int totalTasks = 0;
        int completedTasks = 0;
        int pendingTasks = 0;
        int dueTodayTasks = 0;
        int overdueTasks = 0;
        Map<Priority, Integer> priorityCounts = new HashMap<>();

        for (TaskList taskList: taskLists) {

            for (Task task: taskList.getTasks()) {

                totalTasks++;

                if (task.getStatus() == Status.DONE)
                {
                    completedTasks++;
                }
                else
                {
                    pendingTasks++;

                    if (task.isDueToday())
                    {
                        dueTodayTasks++;
                    }

                    if (task.isOverdue())
                    {
                        overdueTasks++;
                    }

                }

                priorityCounts.merge(task.getTaskPriority(), 1, Integer::sum);

            }

        }

        report.setTotalTasks(totalTasks);
        report.setCompletedTasks(completedTasks);
        report.setPendingTasks(pendingTasks);
        report.setDueTodayTasks(dueTodayTasks);
        report.setOverdueTasks(overdueTasks);
        report.setPriorityCounts(priorityCounts);

        return report;
    }

    public void processExportReportData(File file, ReportData report) throws IOException
    {

        try(PrintWriter writer = new PrintWriter(file)) {

            writer.println("TASK REPORT");
            writer.println("-----------------------");

            writer.println("Total Tasks: " + report.getTotalTasks());
            writer.println("Completed Tasks: " + report.getCompletedTasks());
            writer.println("Pending Tasks: " + report.getPendingTasks());
            writer.println("Tasks Due Today: " + report.getDueTodayTasks());
            writer.println("Overdue Tasks: " + report.getOverdueTasks());

            writer.println("");
            writer.println("Priority Correspondence:");

            Map<Priority, Integer> priorityCounts = report.getPriorityCounts();

            if (priorityCounts.get(Priority.HIGH) != null)
            {
                writer.println("High: " + priorityCounts.get(Priority.HIGH));
            }
            else
            {
                writer.println("High: " + 0);
            }

            if (priorityCounts.get(Priority.MEDIUM) != null)
            {
                writer.println("Medium: " + priorityCounts.get(Priority.MEDIUM));
            }
            else
            {
                writer.println("Medium: " + 0);
            }

            if (priorityCounts.get(Priority.LOW) != null)
            {
                writer.println("Low: " + priorityCounts.get(Priority.LOW));
            }
            else
            {
                writer.println("Low: " + 0);
            }

        }

    }

}
