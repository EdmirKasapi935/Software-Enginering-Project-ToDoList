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
    private TaskMenu parentWindow;
    private Task task;

    public TaskComponent(TaskMenu parentWindow, Task task)
    {
        this.parentWindow = parentWindow;
        this.task = task;

        setBackground(new Color(80, 187, 142));

        taskField = new JTextPane();
        taskField.setEditable(false);
        taskField.setPreferredSize(AppDimensions.TASKFIELD_SIZE);
        taskField.setContentType("text/html");
        taskField.setText("<html><h4>" + task.getTaskText() + "</h4></html>");

        checkbox = new JCheckBox();
        checkbox.setPreferredSize(AppDimensions.CHECKBOX_SIZE);
        checkbox.addActionListener(this);

        deleteButton = new JButton("X");
        deleteButton.setPreferredSize(AppDimensions.DELETE_BUTTON_SIZE);
        deleteButton.addActionListener(this);

        add(checkbox);
        add(taskField);
        add(deleteButton);

    }

    public JTextPane getTaskField() {
        return taskField;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(checkbox.isSelected()) {

            String taskText = taskField.getText().replaceAll("<[^>]*>", "");

            taskField.setText("<html><s>" + taskText + "</s></html>");
            task.setStatus(Status.DONE);


        } else if (!checkbox.isSelected()) {

            String taskText = taskField.getText().replaceAll("<[^>]*>", "");

            taskField.setText(taskText);
            task.setStatus(Status.UNDONE);
        }

        if(e.getActionCommand().equalsIgnoreCase("X")){
            //parentPanel.remove(this);
            //parentPanel.repaint();
            //parentPanel.revalidate();
        }

    }
}
