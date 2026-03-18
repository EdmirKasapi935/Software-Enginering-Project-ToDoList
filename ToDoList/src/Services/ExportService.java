package Services;

import CustomExceptions.EmptyInputException;
import CustomExceptions.EmptyListException;
import Models.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ExportService {



    //This function is  for exporting the report to  text file
    public void processExportReportData(File file, ReportData report) throws IOException, EmptyInputException
    {
        //Safety check to see if the selected file is null or no file was provided.
        if (file == null)
        {
            throw new EmptyInputException("File was not provided or found!");
        }

        //Safety check to prevent an empty report d^t from being passed
        if (report == null)
        {
            throw new EmptyInputException("Report data was not provided!");
        }

        //printwriter object is created and it prints the report data into the selected file
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

    //This function is responsible for exporting a task into a text file
    public void processExportTaskList(File file, TaskList tasks) throws IOException, EmptyListException, EmptyInputException
    {

        //Safety check that the file provided was not null or not found
        if (file == null)
        {
            throw new EmptyInputException("File was not provided or found!");
        }

        //Safety check that the task list provided was not null
        if (tasks == null)
        {
            throw new EmptyInputException("List data was not provided!");
        }

        //Statement that checks if the task list provided contains tasks. Exporting an empty task list would be pointless.
        if (tasks.getTasks().size() == 0)
        {
            throw new EmptyListException("Cannot export a list that contains no tasks!");
        }

        //The printwriter object is ceated here and the information is printed into the file.
        try(PrintWriter writer = new PrintWriter(file)) {

            writer.println("List: " + tasks.getListName());
            writer.println("-----------------------------");
            writer.println("");


            for (Task task:tasks.getTasks()) {

                String completionSquare;

                if (task.getStatus() == Status.UNDONE)
                {
                    completionSquare = "□ ";
                }
                else {
                    completionSquare = "▣ ";
                }

                writer.println(completionSquare + task.getTaskText() + " |  CATEGORY: " + task.getTaskCategory() + " | PRIORITY: " + task.getTaskPriority() + " | Due: " + task.getDueDateString() );
            }

        }

    }

}
