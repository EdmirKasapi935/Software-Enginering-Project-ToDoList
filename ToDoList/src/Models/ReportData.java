package Models;

import java.util.Map;

public class ReportData {

    private int totalTasks;
    private int completedTasks;
    private int pendingTasks;
    private int dueTodayTasks;
    private int overdueTasks;
    private Map<Priority, Integer> priorityCounts;

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public int getPendingTasks() {
        return pendingTasks;
    }

    public int getDueTodayTasks() {
        return dueTodayTasks;
    }

    public int getOverdueTasks() {
        return overdueTasks;
    }

    public Map<Priority, Integer> getPriorityCounts() {
        return priorityCounts;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public void setPendingTasks(int pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public void setDueTodayTasks(int dueTodayTasks) {
        this.dueTodayTasks = dueTodayTasks;
    }

    public void setOverdueTasks(int overdueTasks) {
        this.overdueTasks = overdueTasks;
    }

    public void setPriorityCounts(Map<Priority, Integer> priorityCounts) {
        this.priorityCounts = priorityCounts;
    }
}
