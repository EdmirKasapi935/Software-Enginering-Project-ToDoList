# Software-Enginering-Project-ToDoList


# Introduction

This application is designed to help users **organize, manage, and track tasks** in a simple and efficient way. It provides a structured environment where users can create multiple task lists, add detailed tasks, and monitor progress over time.

The core aim of the application is to improve task organization by allowing users to arrange tasks into separate lists and categories such as:

- Personal
- Work
- Event-related
- Miscellaneous

In addition to basic task management, the application supports:

- Sorting
- Deadline notifications
- Report generation
- Automatic data saving

This guide provides **step-by-step instructions** for all major features.

---

# 1. System Requirements

Before installing or running the application, please ensure your system meets the following requirements.

## Minimum Requirements

- **Operating System:** Windows 10+, Linux (desktop), or macOS
- **Java Runtime Environment (JRE):** Version 17+
- **RAM:** Minimum 4 GB recommended
- **Disk Space:** At least 100 MB
- **Display Resolution:** 1920 × 1080 minimum

## Recommended Requirements

- **Operating System:** Latest supported version
- **RAM:** 8 GB or more
- **Disk Space:** 250 MB or more
- **Java Development Kit (JDK):** Version 17+ (for IDE usage)

---

# 2. Getting Started

## 2.1 Main Menu

The **Main Menu** is displayed when the application starts.

It contains:

- **List Panel** → displays all created task lists
- **Button Panel** → contains actions like:
  - Add List
  - Search
  - View Report

---

## 2.2 Task Menu

Clicking a list opens the **Task Menu**.

This menu contains:

- Task panel
- Add Task button
- Sort options
- Export options
- Delete List button

---

## 2.3 Task Creation Form

Accessible via **Add Task**.

The form includes:

- Description text field
- Category combo box
- Priority combo box
- Date spinner
- Submit button
- Back button

---

## 2.4 Task Editing Form

Similar to the task creation form, but pre-filled with the selected task’s data.

Accessible through the **edit button**.

---

## 2.5 Report Menu

Displays important task statistics.

Accessible via **View Report**.

Includes:

- Statistics panel
- Export button
- Navigation controls

---

# 3. Visual Indicators

The application uses visual indicators for clarity.

---

## 3.1 Deadline Indicators

- **Yellow outline** → task due today
- **Red outline** → task overdue
- **Yellow task area** → due today
- **Red task area** → overdue

---

## 3.2 Priority Indicators

- **Green ribbon** → Low priority
- **Yellow ribbon** → Medium priority
- **Red ribbon** → High priority

---

## 3.3 Status Indicators

- **Unchecked checkbox** → incomplete
- **Checked checkbox** → complete

---

# 4. Application Procedures

---

## 4.1 Creating a List

1. Click **Add List**
2. Enter list name
3. Click **OK**

---

## 4.2 Removing a List

1. Click the **trash icon**
2. Confirm with **OK**

---

## 4.3 Searching for a List

1. Click **Search**
2. Enter full or partial list name
3. Click **OK**

> Leaving the input empty resets the list view.

---

## 4.4 Renaming a List

1. Select a list
2. Click **Edit Name**
3. Enter new name
4. Click **OK**

---

## 4.5 Creating a Task

1. Select a list
2. Click **Add Task**
3. Fill in task information
4. Click **Add Task**

---

## 4.6 Removing a Task

1. Select a list
2. Click the task’s **delete button**

---

## 4.7 Editing a Task

1. Select a list
2. Click the **edit button**
3. Modify information
4. Click **Save Changes**

---

## 4.8 Marking a Task Complete

1. Open the list
2. Click the task checkbox

---

## 4.9 Sorting Tasks

1. Open a list
2. Choose sorting option
3. Click **Sort**

---

## 4.10 Exporting a List

1. Open list
2. Click **Export**
3. Choose location
4. Enter filename
5. Click **Save**

---

## 4.11 Viewing the Report

1. Click **View Report**
2. Review displayed statistics

---

## 4.12 Exporting the Report

1. Open **Report Menu**
2. Click **Export Report**
3. Choose location
4. Enter filename
5. Click **Save**

---

# 5. Notifications

The application automatically notifies users about:

- tasks due today
- overdue tasks

Notifications appear in the **bottom-right corner** of the screen.

The application checks tasks:

- on startup
- every hour afterward

Notifications are only triggered if the pending task count changes.

---

# 6. Frequently Asked Questions (FAQ)

## Does sorting change the saved task order?

No. Sorting only changes display order.

---

## Are deleted tasks recoverable?

No. Deleted tasks are permanently removed.

---

## Why am I not receiving notifications?

Notifications only appear for:

- incomplete tasks
- due today
- overdue tasks

---

## Are tasks automatically saved?

Yes. All changes are automatically saved.

---

## Can multiple tasks share the same due date?

Yes.

---

## Does the app need internet?

No. The application works fully offline.

---

## Can I move tasks between lists?

No. Recreate the task in another list.

---

## Is there a task limit?

No practical limit under normal use.

---

# 7. Glossary

## Task
A single activity or reminder to complete.

## Task List
A collection of related tasks.

## Due Date
The deadline for task completion.

## Priority
Task urgency level:
- Low
- Medium
- High

## Category
Task grouping type:
- Personal
- Work
- Event
- Miscellaneous

## Pending Task
An incomplete task.

## Completed Task
A finished task.

## Overdue Task
An incomplete task past its due date.

## Notification
A reminder for due or overdue tasks.

## Report
A statistical summary of tasks.

## Export
Saving task data externally.

## Storage
Permanent task data persistence.

---

# Important

> Before running the application, make sure to use **JDK 17 or higher**.

Also ensure **JUnit 5** is added to the classpath for the test classes inside the `Tests` package.








