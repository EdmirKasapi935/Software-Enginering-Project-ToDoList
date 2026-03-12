package Models;

import java.util.Date;

public class Task {

    private String taskText;
    private Priority taskPriority;
    private Category taskCategory;
    private Date dueDate;
    private Status status;

    public Task(String taskText, Priority taskPriority, Category taskCategory, Date dueDate)
    {
        this.taskText = taskText;
        this.taskPriority = taskPriority;
        this.taskCategory = taskCategory;
        this.dueDate = dueDate;
        this.status = Status.UNDONE;
    }

    public Task(String taskText, Priority taskPriority, Category taskCategory, Date dueDate, Status status)
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

    public Date getDueDate() {
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

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
