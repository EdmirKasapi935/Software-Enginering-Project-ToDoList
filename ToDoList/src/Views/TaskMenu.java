package Views;

import Controllers.ListController;
import Models.TaskList;
import Observers.ListNameObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TaskMenu extends JFrame implements ActionListener, ListNameObserver {

    private final ListController listController = new ListController();

    private final MainFrame mainFrame;
    private final ArrayList<TaskList> taskLists;
    private TaskList currentList;
    private JLabel pageTitle;
    private JPanel taskPanel, taskComponentPanel;

    public TaskMenu(ArrayList<TaskList> taskLists, TaskList list, MainFrame frame)
    {
        listController.addNameObserver(this);

        this.mainFrame = frame;
        this.currentList = list;
        this.taskLists = taskLists;

        setLayout(null);

        addGuiComponents();
    }

    private void addGuiComponents()
    {
        pageTitle = new JLabel("<html> <h3 style='text-align:center;'> Currently viewing list: <br>" + currentList.getListName() + " <h3> </html>");
        pageTitle.setBounds((AppDimensions.BANNER_SIZE.width - pageTitle.getPreferredSize().width)/2,
                15,
                AppDimensions.BANNER_SIZE.width,
                AppDimensions.BANNER_SIZE.height
        );

        taskPanel = new JPanel();

        taskComponentPanel = new JPanel();
        taskComponentPanel.setLayout((new BoxLayout(taskComponentPanel, BoxLayout.Y_AXIS)));
        taskPanel.add(taskComponentPanel);

        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBounds(8, 70,  AppDimensions.TASKPANEL_SIZE.width,  AppDimensions.TASKPANEL_SIZE.height);
        scrollPane.setMaximumSize( AppDimensions.TASKPANEL_SIZE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBounds(-5,  AppDimensions.GUI_SIZE.height - 88,  AppDimensions.ADDTASKBUTTON_SIZE.width,  AppDimensions.ADDTASKBUTTON_SIZE.height);

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(this);
        buttonPanel.add(mainMenuButton);

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(this);
        buttonPanel.add(addTaskButton);

        JButton editListNameButton = new JButton("Edit Name");
        editListNameButton.addActionListener(this);
        buttonPanel.add(editListNameButton);


        this.getContentPane().add(pageTitle);
        this.getContentPane().add(scrollPane);
        this.getContentPane().add(buttonPanel);
        this.getContentPane().setBackground(new Color(78, 206, 225));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("Main Menu"))
        {
            mainFrame.showMainListMenu(taskLists);
        } else if (command.equalsIgnoreCase("Edit Name")) {
            String newListName = JOptionPane.showInputDialog("Enter the name of the new List");

            if (newListName != null && !newListName.isEmpty()) {
                listController.changeListName(currentList, newListName);
            }
        }
    }

    @Override
    public void onListNameChange(TaskList list) {
        this.pageTitle.setText("<html> <h3 style='text-align:center;'> Currently viewing list: <br>" + list.getListName() + " <h3> </html>");
        this.pageTitle.setBounds((AppDimensions.BANNER_SIZE.width - pageTitle.getPreferredSize().width)/2,
                15,
                AppDimensions.BANNER_SIZE.width,
                AppDimensions.BANNER_SIZE.height
        );
    }
}
