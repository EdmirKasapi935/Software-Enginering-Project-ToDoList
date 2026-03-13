package Views;

import Controllers.ListController;
import Controllers.TaskController;
import Data.AppContext;
import Data.ListRepository;
import Models.Task;
import Models.TaskList;
import Observers.ListNameObserver;
import Observers.TaskPanelObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TaskMenu extends JFrame implements ActionListener, ListNameObserver, TaskPanelObserver {

    private final ListController listController = new ListController();

    private final TaskController taskController = new TaskController();

    private final MainFrame mainFrame;
    private TaskList currentList = AppContext.getInstance().getCurrentList();
    private JLabel pageTitle;
    private JPanel taskPanel, taskComponentPanel;

    public TaskMenu( MainFrame frame)
    {
        listController.addNameObserver(this);
        taskController.addTaskPanelObserver(this);

        this.mainFrame = frame;

        setLayout(null);

        addGuiComponents();
        populateTaskPanel(currentList);
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

    public void removeTask(Task task)
    {
        taskController.deleteTask(currentList, task);
    }

    public void goToTaskEditForm(Task task)
    {
        AppContext.getInstance().setCurrentTask(task);
        mainFrame.showEditTaskForm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("Main Menu"))
        {
            AppContext.getInstance().setCurrentList(null);
            mainFrame.showMainListMenu();
        } else if (command.equalsIgnoreCase("Edit Name")) {
            String newListName = JOptionPane.showInputDialog("Enter the new name for this list.");

            if (newListName != null && !newListName.isEmpty()) {
                listController.changeListName(ListRepository.getInstance().getAllLists(), currentList, newListName);
            }
        } else if (command.equalsIgnoreCase("Add Task")) {
            mainFrame.showAddTaskForm();
        }
    }

    private void populateTaskPanel(TaskList list) {

        taskComponentPanel.removeAll();

        ArrayList<Task> tasks = list.getTasks();

        for (Task task: tasks) {
            taskComponentPanel.add(new TaskComponent(this, task));
        }

        taskComponentPanel.revalidate();
        taskComponentPanel.repaint();
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

    @Override
    public void onListStateChange(TaskList list) {
        populateTaskPanel(list);
    }
}
