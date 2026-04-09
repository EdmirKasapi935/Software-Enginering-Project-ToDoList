package Tests.Services;

import Models.Category;
import Models.Priority;
import Models.ReportData;
import Models.Task;
import Models.TaskList;
import Services.ReportService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceTest {

    private final ReportService reportService = new ReportService();

    @Test
    public void testProcessGenerateReportWithEmptyLists() {
        List<TaskList> lists = new ArrayList<>();

        ReportData report = reportService.processGenerateReport(lists);

        assertEquals(0, report.getTotalTasks());
        assertEquals(0, report.getCompletedTasks());
        assertEquals(0, report.getPendingTasks());
        assertEquals(0, report.getDueTodayTasks());
        assertEquals(0, report.getOverdueTasks());
    }

    @Test
    public void testProcessGenerateReportWithTasks() {
        TaskList list = new TaskList("Main List");

        Task task1 = new Task("Task 1", Priority.HIGH, Category.WORK, LocalDate.now());
        Task task2 = new Task("Task 2", Priority.MEDIUM, Category.PERSONAL, LocalDate.now().plusDays(2));
        Task task3 = new Task("Task 3", Priority.LOW, Category.EVENT, LocalDate.now().minusDays(1));

        task2.markAsDone();

        list.addTask(task1);
        list.addTask(task2);
        list.addTask(task3);

        List<TaskList> lists = new ArrayList<>();
        lists.add(list);

        ReportData report = reportService.processGenerateReport(lists);

        assertEquals(3, report.getTotalTasks());
        assertEquals(1, report.getCompletedTasks());
        assertEquals(2, report.getPendingTasks());
        assertEquals(1, report.getDueTodayTasks());
        assertEquals(1, report.getOverdueTasks());
        assertEquals(1, report.getPriorityCounts().get(Priority.HIGH));
        assertEquals(1, report.getPriorityCounts().get(Priority.MEDIUM));
        assertEquals(1, report.getPriorityCounts().get(Priority.LOW));
    }
}