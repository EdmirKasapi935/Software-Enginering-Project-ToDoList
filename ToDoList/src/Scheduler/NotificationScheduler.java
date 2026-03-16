package Scheduler;

import Services.NotificationService;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationScheduler {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final NotificationService notificationService;

    public NotificationScheduler(NotificationService notificationService)
    {
        this.notificationService = notificationService;
    }

    public void start()
    {
        scheduler.scheduleAtFixedRate(
                notificationService::checkTasks,
                0,
                1,
                TimeUnit.HOURS
        );
    }

    public void stop()
    {
        scheduler.shutdown();
    }

}
