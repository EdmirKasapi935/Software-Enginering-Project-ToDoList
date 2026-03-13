package Views;

import Models.Status;
import Models.Task;
import Views.AppDimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskComponent extends JPanel implements ActionListener {
    private JCheckBox checkbox;
    private JTextPane taskField;
    private JButton deleteButton;
    private JButton editButton;
    private TaskMenu parentWindow;
    private Task task;

    public TaskComponent(TaskMenu parentWindow, Task task)
    {
        this.parentWindow = parentWindow;
        this.task = task;

        setBackground(new Color(80, 187, 142));

        taskField = new JTextPane();
        taskField.setEditable(false);
        taskField.setPreferredSize(new Dimension( (int)(AppDimensions.TASKFIELD_SIZE.width * 0.85) , AppDimensions.TASKFIELD_SIZE.height));
        taskField.setContentType("text/html");
        renderTaskText(task.getTaskText());

        activateCheckbox();

        deleteButton = new JButton("X");
        deleteButton.setPreferredSize(AppDimensions.DELETE_BUTTON_SIZE);
        deleteButton.addActionListener(this);

        editButton = new JButton("...");
        editButton.setPreferredSize(AppDimensions.DELETE_BUTTON_SIZE);
        editButton.setBackground(Color.YELLOW);
        editButton.addActionListener(this);

        add(checkbox);
        add(taskField);
        add(deleteButton);
        add(editButton);

    }

    public JTextPane getTaskField() {
        return taskField;
    }

    private void renderTaskText(String taskText)
    {
        if (task.getStatus() == Status.UNDONE)
        {
            taskField.setText("<html><h3 style='text-align:center'>" + taskText + "<h3></html>");
        }else if (task.getStatus() == Status.DONE ){
            taskField.setText("<html><h3 style='text-align:center'><s>" + taskText + "<s><h3></html>");
        }


    }

    private void activateCheckbox()
    {
        checkbox = new JCheckBox();
        checkbox.setPreferredSize(AppDimensions.CHECKBOX_SIZE);
        checkbox.addActionListener(this);

        if (task.getStatus() == Status.DONE)
        {
            checkbox.setSelected(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(checkbox.isSelected()) {

            task.markAsDone();
            renderTaskText(task.getTaskText());

        } else if (!checkbox.isSelected()) {

            task.markAsUndone();
            renderTaskText(task.getTaskText());
        }

        if(e.getActionCommand().equalsIgnoreCase("X")){
            parentWindow.removeTask(task);
        }

        if(e.getActionCommand().equalsIgnoreCase("...")){
            parentWindow.goToTaskEditForm(task);
        }

    }
}
