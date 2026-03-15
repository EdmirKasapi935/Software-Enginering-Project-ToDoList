package Models;

import java.time.LocalDate;
import java.util.Date;

public class Task {

    private String taskText;
    private Priority taskPriority;
    private Category taskCategory;
    private LocalDate dueDate;
    private Status status;

    public Task(String taskText, Priority taskPriority, Category taskCategory, LocalDate dueDate)
    {
        this.taskText = taskText;
        this.taskPriority = taskPriority;
        this.taskCategory = taskCategory;
        this.dueDate = dueDate;
        this.status = Status.UNDONE;
    }

    public Task(String taskText, Priority taskPriority, Category taskCategory, LocalDate dueDate, Status status)
    {
        this.taskText = taskText;
        this.taskPriority = taskPriority;
        this.taskCategory = taskCategory;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getTaskText() {
        return taskText;
    }

    public Priority getTaskPriority() {
        return taskPriority;
    }

    public Category getTaskCategory() {
        return taskCategory;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public void setTaskPriority(Priority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public void setTaskCategory(Category taskCategory) {
        this.taskCategory = taskCategory;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void markAsDone()
    {
        if (this.status == Status.UNDONE)
        {
            this.status = Status.DONE;
        }
    }

    public void markAsUndone()
    {
        if (this.status == Status.DONE)
        {
            this.status = Status.UNDONE;
        }
    }

    public boolean isOverdue() {
        return status != Status.DONE && dueDate.isBefore(LocalDate.now());
    }

    public boolean isDueToday()
    {
        return status != Status.DONE && dueDate.equals(LocalDate.now());
    }
}
