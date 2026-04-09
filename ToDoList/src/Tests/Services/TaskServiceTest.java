package Tests.Services;

import CustomExceptions.EmptyInputException;
import CustomExceptions.TaskValidationException;
import Models.Category;
import Models.Priority;
import Models.Task;
import Models.TaskList;
import Services.TaskService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    private final TaskService taskService = new TaskService();

    @Test
    public void testProcessCreateTaskWithValidData() throws TaskValidationException {
        TaskList list = new TaskList("My Tasks");

        taskService.processCreateTask(
                list,
                "Study for exam",
                Category.PERSONAL,
                Priority.HIGH,
                LocalDate.now().plusDays(2)
        );

        assertEquals(1, list.getTasks().size());
        assertEquals("Study for exam", list.getTasks().get(0).getTaskText());
        assertEquals(Category.PERSONAL, list.getTasks().get(0).getTaskCategory());
        assertEquals(Priority.HIGH, list.getTasks().get(0).getTaskPriority());
    }

    @Test
    public void testProcessCreateTaskWithInvalidTitle() {
        TaskList list = new TaskList("My Tasks");

        assertThrows(TaskValidationException.class, () -> {
            taskService.processCreateTask(
                    list,
                    "",
                    Category.PERSONAL,
                    Priority.HIGH,
                    LocalDate.now().plusDays(2)
            );
        });

        assertEquals(0, list.getTasks().size());
    }

    @Test
    public void testProcessDeleteTask() {
        TaskList list = new TaskList("My Tasks");
        Task task = new Task("Read chapter", Priority.MEDIUM, Category.WORK, LocalDate.now().plusDays(1));
        list.addTask(task);

        TaskList result = taskService.processDeleteTask(list, task);

        assertEquals(0, result.getTasks().size());
    }

    @Test
    public void testProcessEditTaskWithValidData() throws EmptyInputException, TaskValidationException {
        Task task = new Task("Old title", Priority.LOW, Category.EVENT, LocalDate.now().plusDays(3));

        taskService.processEditTask(
                task,
                "New title",
                Category.WORK,
                Priority.HIGH,
                LocalDate.now().plusDays(5)
        );

        assertEquals("New title", task.getTaskText());
        assertEquals(Category.WORK, task.getTaskCategory());
        assertEquals(Priority.HIGH, task.getTaskPriority());
        assertEquals(LocalDate.now().plusDays(5), task.getDueDate());
    }

    @Test
    public void testProcessEditTaskWithInvalidData() {
        Task task = new Task("Old title", Priority.LOW, Category.EVENT, LocalDate.now().plusDays(3));

        assertThrows(TaskValidationException.class, () -> {
            taskService.processEditTask(
                    task,
                    "",
                    Category.WORK,
                    Priority.HIGH,
                    LocalDate.now().plusDays(5)
            );
        });

        assertEquals("Old title", task.getTaskText());
    }
}