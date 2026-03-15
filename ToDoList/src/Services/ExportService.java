package Services;

import CustomExceptions.EmptyListException;
import Models.Priority;
import Models.ReportData;
import Models.Task;
import Models.TaskList;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Map;

public class ExportService {

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

    public void processExportTaskList(File file, TaskList tasks) throws IOException, EmptyListException
    {
        if (tasks.getTasks().size() == 0)
        {
            throw new EmptyListException("Cannot export a list that contains no tasks!");
        }

        try(PrintWriter writer = new PrintWriter(file)) {

            writer.println("List: " + tasks.getListName());
            writer.println("-----------------------------");
            writer.println("");

            for (Task task:tasks.getTasks()) {
                LocalDate dueDate = task.getDueDate();
                writer.println("□ " + task.getTaskText() + " |  CATEGORY: " + task.getTaskCategory() + " | PRIORITY: " + task.getTaskPriority() + " | Due: " + dueDate.getDayOfMonth() + "/" + dueDate.getMonthValue() + "/" + dueDate.getYear() );
            }

        }

    }

}
