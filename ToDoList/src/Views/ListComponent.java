package Views;

import Data.AppContext;
import Models.Task;
import Models.TaskList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListComponent extends JPanel implements ActionListener {

    private final JButton listButton;
    private final JButton deleteButton;
    private final MainListMenu parentWindow;
    private final TaskList taskList;

    public ListComponent(MainListMenu parentWindow, String listName, TaskList taskList)
    {
        this.listButton = new JButton(listName);
        listButton.setPreferredSize(new Dimension( (int)(AppDimensions.GUI_SIZE.width * 0.7), (int)(AppDimensions.GUI_SIZE.height*0.05)));

        this.deleteButton = new JButton("X");
        deleteButton.setBackground(Color.RED);
        deleteButton.setPreferredSize(AppDimensions.DELETE_BUTTON_SIZE);
        deleteButton.addActionListener(this);
        listButton.addActionListener(this);

        this.parentWindow = parentWindow;
        this.taskList = taskList;

        if(taskList.getTasks().stream().anyMatch(Task::isDueToday))
        {
            listButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        }

        if (taskList.getTasks().stream().anyMatch(Task::isOverdue)) {
            listButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }

        this.setLayout(new BorderLayout());
        this.add(listButton, BorderLayout.CENTER);
        this.add(deleteButton, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("X")){
            parentWindow.removeList(this.taskList);
        } else if (command.equalsIgnoreCase(this.taskList.getListName())) {
            AppContext.getInstance().setCurrentList(this.taskList);
            parentWindow.goToTaskMenu();
        }
    }
}
