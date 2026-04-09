package Tests.Services;

import CustomExceptions.EmptyInputException;
import CustomExceptions.ListNameLengthExceededException;
import CustomExceptions.ListNameUnavailableException;
import Models.TaskList;
import Services.ListService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListServiceTest {

    private final ListService listService = new ListService();

    @Test
    public void testProcessCreateListWithValidName() throws EmptyInputException, ListNameLengthExceededException, ListNameUnavailableException {
        List<TaskList> taskLists = new ArrayList<>();

        listService.processCreateList(taskLists, "Uni Tasks");

        assertEquals(1, taskLists.size());
        assertEquals("Uni Tasks", taskLists.get(0).getListName());
    }

    @Test
    public void testProcessCreateListWithEmptyName() {
        List<TaskList> taskLists = new ArrayList<>();

        assertThrows(EmptyInputException.class, () -> {
            listService.processCreateList(taskLists, "");
        });
    }

    @Test
    public void testProcessCreateListWithDuplicateName() {
        List<TaskList> taskLists = new ArrayList<>();
        taskLists.add(new TaskList("Work"));

        assertThrows(ListNameUnavailableException.class, () -> {
            listService.processCreateList(taskLists, "Work");
        });
    }

    @Test
    public void testProcessDeleteList() {
        List<TaskList> taskLists = new ArrayList<>();
        TaskList list = new TaskList("School");
        taskLists.add(list);

        listService.processDeleteList(taskLists, list);

        assertEquals(0, taskLists.size());
    }

    @Test
    public void testProcessNameChangeWithValidName() throws EmptyInputException, ListNameLengthExceededException, ListNameUnavailableException {
        List<TaskList> taskLists = new ArrayList<>();
        TaskList list = new TaskList("Old Name");
        taskLists.add(list);

        listService.processNameChange(taskLists, list, "New Name");

        assertEquals("New Name", list.getListName());
    }

    @Test
    public void testProcessNameChangeWithDuplicateName() {
        List<TaskList> taskLists = new ArrayList<>();
        TaskList list1 = new TaskList("Work");
        TaskList list2 = new TaskList("Personal");

        taskLists.add(list1);
        taskLists.add(list2);

        assertThrows(ListNameUnavailableException.class, () -> {
            listService.processNameChange(taskLists, list2, "Work");
        });
    }
}