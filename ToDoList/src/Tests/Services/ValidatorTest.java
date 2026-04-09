package Tests.Services;

import CustomExceptions.EmptyInputException;
import CustomExceptions.ListNameLengthExceededException;
import CustomExceptions.ListNameUnavailableException;
import CustomExceptions.TaskValidationException;
import Models.Category;
import Models.Priority;
import Models.Task;
import Models.TaskList;
import Services.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    private final Validator validator = new Validator();

    @Test
    public void testValidateTaskCreationWithValidData() throws TaskValidationException {
        List<Object> result = validator.validateTaskCreation(
                "Finish project",
                Category.WORK,
                Priority.HIGH,
                LocalDate.now().plusDays(2)
        );

        assertEquals("Finish project", result.get(0));
        assertEquals(Priority.HIGH, result.get(1));
        assertEquals(Category.WORK, result.get(2));
        assertEquals(LocalDate.now().plusDays(2), result.get(3));
    }

    @Test
    public void testValidateTaskCreationWithEmptyTitle() {
        TaskValidationException ex = assertThrows(TaskValidationException.class, () -> {
            validator.validateTaskCreation("", Category.WORK, Priority.HIGH, LocalDate.now().plusDays(1));
        });

        assertTrue(ex.getMessage().contains("Task's title cannot be empty."));
    }

    @Test
    public void testValidateTaskCreationWithLongTitle() {
        TaskValidationException ex = assertThrows(TaskValidationException.class, () -> {
            validator.validateTaskCreation(
                    "This title is definitely longer than thirty five characters",
                    Category.WORK,
                    Priority.HIGH,
                    LocalDate.now().plusDays(1)
            );
        });

        assertTrue(ex.getMessage().contains("Task's title cannot exceed 35 characters."));
    }

    @Test
    public void testValidateTaskCreationWithNullPriority() {
        TaskValidationException ex = assertThrows(TaskValidationException.class, () -> {
            validator.validateTaskCreation("Task", Category.PERSONAL, null, LocalDate.now().plusDays(1));
        });

        assertTrue(ex.getMessage().contains("Please select a priority level."));
    }

    @Test
    public void testValidateTaskCreationWithNullCategory() {
        TaskValidationException ex = assertThrows(TaskValidationException.class, () -> {
            validator.validateTaskCreation("Task", null, Priority.MEDIUM, LocalDate.now().plusDays(1));
        });

        assertTrue(ex.getMessage().contains("Please select a task category."));
    }

    @Test
    public void testValidateTaskCreationWithNullDueDate() {
        TaskValidationException ex = assertThrows(TaskValidationException.class, () -> {
            validator.validateTaskCreation("Task", Category.EVENT, Priority.MEDIUM, null);
        });

        assertTrue(ex.getMessage().contains("Please enter a due date."));
    }

    @Test
    public void testValidateTaskCreationWithPastDate() {
        TaskValidationException ex = assertThrows(TaskValidationException.class, () -> {
            validator.validateTaskCreation("Task", Category.EVENT, Priority.MEDIUM, LocalDate.now().minusDays(1));
        });

        assertTrue(ex.getMessage().contains("Due date cannot be before today."));
    }

    @Test
    public void testValidateTaskEditingWithNullTask() {
        assertThrows(EmptyInputException.class, () -> {
            validator.validateTaskEditing(null, "Updated", Category.WORK, Priority.HIGH, LocalDate.now().plusDays(1));
        });
    }

    @Test
    public void testValidateTaskEditingWithValidData() throws EmptyInputException, TaskValidationException {
        Task task = new Task("Old task", Priority.LOW, Category.PERSONAL, LocalDate.now().plusDays(2));

        List<Object> result = validator.validateTaskEditing(
                task,
                "Updated task",
                Category.WORK,
                Priority.HIGH,
                LocalDate.now().plusDays(4)
        );

        assertEquals("Updated task", result.get(0));
        assertEquals(Priority.HIGH, result.get(1));
        assertEquals(Category.WORK, result.get(2));
        assertEquals(LocalDate.now().plusDays(4), result.get(3));
    }

    @Test
    public void testValidateNotNullWithBlankInput() {
        assertThrows(EmptyInputException.class, () -> {
            validator.validateNotNull("   ", "Input cannot be empty");
        });
    }

    @Test
    public void testValidateNotNullWithValidInput() throws EmptyInputException {
        String result = validator.validateNotNull("My List", "Input cannot be empty");
        assertEquals("My List", result);
    }

    @Test
    public void testValidateListNameLengthWithValidName() throws ListNameLengthExceededException {
        String result = validator.validateListNameLength("School Tasks");
        assertEquals("School Tasks", result);
    }

    @Test
    public void testValidateListNameLengthWithTooLongName() {
        assertThrows(ListNameLengthExceededException.class, () -> {
            validator.validateListNameLength("This list name is definitely longer than forty characters");
        });
    }

    @Test
    public void testValidateListNameAvailabilityWithUniqueName() throws ListNameUnavailableException {
        List<TaskList> lists = new ArrayList<>();
        lists.add(new TaskList("Work"));

        String result = validator.validateListNameAvailability(lists, "Personal");
        assertEquals("Personal", result);
    }

    @Test
    public void testValidateListNameAvailabilityWithDuplicateName() {
        List<TaskList> lists = new ArrayList<>();
        lists.add(new TaskList("Work"));

        assertThrows(ListNameUnavailableException.class, () -> {
            validator.validateListNameAvailability(lists, "Work");
        });
    }
}