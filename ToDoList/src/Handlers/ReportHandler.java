package Handlers;

import Models.*;

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

}
