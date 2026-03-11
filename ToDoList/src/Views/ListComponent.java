package Views;

import Models.TaskList;
import Views.AppDimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListComponent extends JPanel implements ActionListener {

    private JButton listButton;
    private JButton deleteButton;
    private MainListMenu parentWindow;
    private TaskList taskList;

    public ListComponent(MainListMenu parentWindow, String listName, TaskList taskList)
    {
        this.listButton = new JButton(listName);
        listButton.setPreferredSize(new Dimension( (int)(AppDimensions.GUI_SIZE.width * 0.7), (int)(AppDimensions.GUI_SIZE.height*0.05)));

        this.deleteButton = new JButton("X");
        deleteButton.setBackground(Color.RED);
        deleteButton.setPreferredSize(AppDimensions.DELETE_BUTTON_SIZE);
        deleteButton.addActionListener(this);

        this.parentWindow = parentWindow;
        this.taskList = taskList;

        this.setLayout(new BorderLayout());
        this.add(listButton, BorderLayout.CENTER);
        this.add(deleteButton, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("X")){
            parentWindow.removeList(this.taskList);
        }
    }
}
