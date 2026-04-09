package Tests.Services;

import Models.Category;
import Models.Priority;
import Models.Task;
import Models.TaskList;
import Services.StorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StorageServiceTest {

    private final Path storagePath = Path.of("src\\Storage\\lists.ser");
    private byte[] backupData;
    private boolean fileExistedBefore;

    @BeforeEach
    public void setUp() throws Exception {
        fileExistedBefore = Files.exists(storagePath);

        if (fileExistedBefore) {
            backupData = Files.readAllBytes(storagePath);
        } else {
            backupData = null;
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (fileExistedBefore && backupData != null) {
            Files.write(storagePath, backupData);
        } else {
            Files.deleteIfExists(storagePath);
        }
    }

    @Test
    public void testSaveListsAndLoadTasks() {
        List<TaskList> originalLists = new ArrayList<>();

        TaskList list = new TaskList("School");
        list.addTask(new Task(
                "Prepare presentation",
                Priority.HIGH,
                Category.WORK,
                LocalDate.now().plusDays(2)
        ));

        originalLists.add(list);

        StorageService.saveLists(originalLists);
        List<TaskList> loadedLists = StorageService.loadTasks();

        assertEquals(1, loadedLists.size());
        assertEquals("School", loadedLists.get(0).getListName());
        assertEquals(1, loadedLists.get(0).getTasks().size());
        assertEquals("Prepare presentation", loadedLists.get(0).getTasks().get(0).getTaskText());
    }

    @Test
    public void testSaveAndLoadEmptyListCollection() {
        List<TaskList> originalLists = new ArrayList<>();

        StorageService.saveLists(originalLists);
        List<TaskList> loadedLists = StorageService.loadTasks();

        assertNotNull(loadedLists);
        assertEquals(0, loadedLists.size());
    }
}