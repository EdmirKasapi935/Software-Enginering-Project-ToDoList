package Tests.Services;

import Services.NotificationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {

    private final NotificationService notificationService = new NotificationService();

    @Test
    public void testShowNotificationWhenTrayIconIsNull() {
        assertDoesNotThrow(() -> {
            notificationService.showNotification("Test Title", "Test Message");
        });
    }

    @Test
    public void testInitializeDoesNotThrowException() {
        assertDoesNotThrow(NotificationService::initialize);
    }
}