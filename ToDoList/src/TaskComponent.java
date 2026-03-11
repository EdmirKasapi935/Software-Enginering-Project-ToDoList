import Views.AppDimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskComponent extends JPanel implements ActionListener {
    private JCheckBox checkbox;

    private JTextPane taskField;
    private JButton deleteButton;

    private JPanel parentPanel;

    public TaskComponent(JPanel parentPanel)
    {
        this.parentPanel = parentPanel;

        setBackground(new Color(80, 187, 142));

        taskField = new JTextPane();
        taskField.setPreferredSize(AppDimensions.TASKFIELD_SIZE);
        taskField.setContentType("text/html");

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

        } else if (!checkbox.isSelected()) {

            String taskText = taskField.getText().replaceAll("<[^>]*>", "");

            taskField.setText(taskText);
        }

        if(e.getActionCommand().equalsIgnoreCase("X")){
            parentPanel.remove(this);
            parentPanel.repaint();
            parentPanel.revalidate();
        }

    }
}
