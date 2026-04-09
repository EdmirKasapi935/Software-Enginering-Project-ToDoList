package Tests.Services;

import CustomExceptions.EmptyInputException;
import CustomExceptions.EmptyListException;
import Models.Category;
import Models.Priority;
import Models.ReportData;
import Models.Task;
import Models.TaskList;
import Services.ExportService;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ExportServiceTest {

    private final ExportService exportService = new ExportService();

    @Test
    public void testProcessExportReportDataWithNullFile() {
        ReportData report = new ReportData();
        report.setPriorityCounts(new HashMap<>());

        assertThrows(EmptyInputException.class, () -> {
            exportService.processExportReportData(null, report);
        });
    }

    @Test
    public void testProcessExportReportDataWithNullReport() {
        File file = new File("testReport.txt");

        assertThrows(EmptyInputException.class, () -> {
            exportService.processExportReportData(file, null);
        });
    }

    @Test
    public void testProcessExportReportDataWithValidData() throws IOException, EmptyInputException {
        File file = new File("testReport.txt");

        ReportData report = new ReportData();
        report.setTotalTasks(5);
        report.setCompletedTasks(2);
        report.setPendingTasks(3);
        report.setDueTodayTasks(1);
        report.setOverdueTasks(1);

        Map<Priority, Integer> priorityCounts = new HashMap<>();
        priorityCounts.put(Priority.HIGH, 2);
        priorityCounts.put(Priority.MEDIUM, 2);
        priorityCounts.put(Priority.LOW, 1);
        report.setPriorityCounts(priorityCounts);

        exportService.processExportReportData(file, report);

        String content = Files.readString(file.toPath());

        assertTrue(content.contains("TASK REPORT"));
        assertTrue(content.contains("Total Tasks: 5"));
        assertTrue(content.contains("Completed Tasks: 2"));

        file.delete();
    }

    @Test
    public void testProcessExportTaskListWithNullFile() {
        TaskList list = new TaskList("School");

        assertThrows(EmptyInputException.class, () -> {
            exportService.processExportTaskList(null, list);
        });
    }

    @Test
    public void testProcessExportTaskListWithNullList() {
        File file = new File("testList.txt");

        assertThrows(EmptyInputException.class, () -> {
            exportService.processExportTaskList(file, null);
        });
    }

    @Test
    public void testProcessExportTaskListWithEmptyList() {
        File file = new File("testList.txt");
        TaskList list = new TaskList("School");

        assertThrows(EmptyListException.class, () -> {
            exportService.processExportTaskList(file, list);
        });
    }

    @Test
    public void testProcessExportTaskListWithValidData() throws IOException, EmptyListException, EmptyInputException {
        File file = new File("testList.txt");
        TaskList list = new TaskList("School");

        Task task = new Task(
                "Study chapter 5",
                Priority.HIGH,
                Category.WORK,
                LocalDate.now().plusDays(1)
        );

        list.addTask(task);

        exportService.processExportTaskList(file, list);

        String content = Files.readString(file.toPath());

        assertTrue(content.contains("List: School"));
        assertTrue(content.contains("Study chapter 5"));
        assertTrue(content.contains("PRIORITY: HIGH"));

        assertTrue(file.delete());
    }
}