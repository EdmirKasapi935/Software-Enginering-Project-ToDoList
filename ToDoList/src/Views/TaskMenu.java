package Views;

import Controllers.ListController;
import Controllers.TaskController;
import Data.AppContext;
import Data.ListRepository;
import Models.SortCriterion;
import Models.Task;
import Models.TaskList;
import Observers.ListNameObserver;
import Observers.TaskPanelObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class TaskMenu extends JFrame implements ActionListener, ListNameObserver, TaskPanelObserver {

    private final ListController listController;

    private final TaskController taskController;

    private final MainFrame mainFrame;
    private TaskList currentList = AppContext.getInstance().getCurrentList();
    private JLabel pageTitle;
    private JPanel taskPanel, taskComponentPanel;

    private JComboBox<SortCriterion> sortBox;

    public TaskMenu(MainFrame frame, ListController listController, TaskController taskController)
    {
        this.mainFrame = frame;
        this.listController = listController;
        this.taskController = taskController;

        listController.addNameObserver(this);
        taskController.addTaskPanelObserver(this);

        setLayout(null);

        addGuiComponents();
        populateTaskPanel(currentList, (SortCriterion) sortBox.getSelectedItem());
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
        scrollPane.setBounds(8, 70,  AppDimensions.TASKPANEL_SIZE.width,  (int) (AppDimensions.TASKPANEL_SIZE.height * 0.97));
        scrollPane.setMaximumSize(AppDimensions.TASKPANEL_SIZE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBounds(-5,  AppDimensions.GUI_SIZE.height - 110,  AppDimensions.ADDTASKBUTTON_SIZE.width,  (int)(AppDimensions.ADDTASKBUTTON_SIZE.height * 2));

        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(this);
        buttonPanel.add(mainMenuButton);

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(this);
        buttonPanel.add(addTaskButton);

        JButton editListNameButton = new JButton("Edit Name");
        editListNameButton.addActionListener(this);
        buttonPanel.add(editListNameButton);

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(this);
        buttonPanel.add(sortButton);

        sortBox = new JComboBox<>();
        sortBox.addItem(SortCriterion.NONE);
        sortBox.addItem(SortCriterion.BY_PRIORITY);
        sortBox.addItem(SortCriterion.BY_DUE_DATE);
        sortBox.addItem(SortCriterion.BY_CATEGORY);
        buttonPanel.add(sortBox);

        JButton deleteButton = new JButton("Delete List");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);

        JButton exportButton = new JButton("Export List");
        exportButton.addActionListener(this);
        buttonPanel.add(exportButton);

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
        } else if (command.equalsIgnoreCase("Sort")) {
           populateTaskPanel(currentList, (SortCriterion) sortBox.getSelectedItem());
        } else if (command.equalsIgnoreCase("Delete List")) {

            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + currentList.getListName() + "?", "Delete Task List", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                listController.deleteList(ListRepository.getInstance().getAllLists(), currentList);
                mainFrame.showMainListMenu();
            }

        } else if (command.equalsIgnoreCase("Export List")) {

            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(null);

            if (option == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = chooser.getSelectedFile();

                listController.exportTaskList(selectedFile, currentList);
            }

        }
    }

    private void populateTaskPanel(TaskList list,SortCriterion criterion) {

        if (taskComponentPanel != null)
        {
            taskComponentPanel.removeAll();

            List<Task> tasks = list.getSortedList(criterion);

            for (Task task: tasks) {
                taskComponentPanel.add(new TaskComponent(this, task));
            }

            taskComponentPanel.revalidate();
            taskComponentPanel.repaint();
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

    @Override
    public void onListStateChange(TaskList list) {
        populateTaskPanel(list, (SortCriterion) sortBox.getSelectedItem());
    }
}
