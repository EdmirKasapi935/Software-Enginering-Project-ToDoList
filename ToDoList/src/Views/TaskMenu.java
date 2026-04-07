package Views;

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
import java.util.List;

public class TaskMenu extends JFrame implements ActionListener, ListNameObserver, TaskPanelObserver {

    private final MainFrame mainFrame;
    private TaskList currentList = AppContext.getInstance().getCurrentList();
    private JLabel pageTitle;
    private JPanel taskPanel, taskComponentPanel;
    private JComboBox<SortCriterion> sortBox;

    public TaskMenu(MainFrame frame) {
        this.mainFrame = frame;
        mainFrame.getListController().addNameObserver(this);
        mainFrame.getTaskController().addTaskPanelObserver(this);
        setLayout(null);
        addGuiComponents();
        populateTaskPanel(currentList, (SortCriterion) sortBox.getSelectedItem());
    }

    private void addGuiComponents() {
        int W = AppDimensions.GUI_SIZE.width;
        int H = AppDimensions.GUI_SIZE.height;

        JPanel headerPanel = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(AppDimensions.CARD_BORDER);
                g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
            }
        };
        headerPanel.setBackground(AppDimensions.BG_TASK_SCREEN);
        headerPanel.setBounds(0, 0, W, 64);

        pageTitle = new JLabel(buildTitleHtml(currentList.getListName()));
        pageTitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        pageTitle.setBounds(0, 16, W, 32);
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(pageTitle);

        int barH  = 100;
        int barY  = H - barH - 28;

        JPanel buttonPanel = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(AppDimensions.CARD_BORDER);
                g.drawLine(0, 0, getWidth(), 0);
            }
        };
        buttonPanel.setBounds(0, barY, W, barH);
        buttonPanel.setBackground(AppDimensions.BG_BUTTON_BAR);

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        row1.setOpaque(false);
        row1.setBounds(0, 0, W, 46);

        JButton mainMenuButton     = AppDimensions.makeButton("Back",         AppDimensions.BTN_NEUTRAL,   AppDimensions.BTN_NEUTRAL_FG);
        JButton addTaskButton      = AppDimensions.makeButton("Add Task",      AppDimensions.BTN_PRIMARY,   AppDimensions.BTN_PRIMARY_FG);
        JButton editListNameButton = AppDimensions.makeButton("Edit Name",     AppDimensions.BTN_SECONDARY, AppDimensions.BTN_SECONDARY_FG);

        mainMenuButton.setActionCommand("Back");
        addTaskButton.setActionCommand("Add Task");
        editListNameButton.setActionCommand("Edit Name");

        mainMenuButton.addActionListener(this);
        addTaskButton.addActionListener(this);
        editListNameButton.addActionListener(this);
        row1.add(mainMenuButton);
        row1.add(addTaskButton);
        row1.add(editListNameButton);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        row2.setOpaque(false);
        row2.setBounds(0, 46, W, 50);

        sortBox = new JComboBox<>();
        sortBox.setFont(AppDimensions.FONT_SMALL);
        sortBox.addItem(SortCriterion.NONE);
        sortBox.addItem(SortCriterion.BY_PRIORITY);
        sortBox.addItem(SortCriterion.BY_DUE_DATE);
        sortBox.addItem(SortCriterion.BY_CATEGORY);
        sortBox.setPreferredSize(new Dimension(110, 30));

        JButton sortButton   = AppDimensions.makeButton("Sort",        AppDimensions.BTN_NEUTRAL,  AppDimensions.BTN_NEUTRAL_FG);
        JButton deleteButton = AppDimensions.makeButton("Delete List",  AppDimensions.BTN_DANGER,   AppDimensions.BTN_DANGER_FG);
        JButton exportButton = AppDimensions.makeButton("Export",       AppDimensions.BTN_SECONDARY,AppDimensions.BTN_SECONDARY_FG);

        sortButton.setActionCommand("Sort");
        deleteButton.setActionCommand("Delete List");
        exportButton.setActionCommand("Export");

        sortButton.addActionListener(this);
        deleteButton.addActionListener(this);
        exportButton.addActionListener(this);

        row2.add(sortBox);
        row2.add(sortButton);
        row2.add(deleteButton);
        row2.add(exportButton);

        buttonPanel.add(row1);
        buttonPanel.add(row2);

        taskPanel = new JPanel();
        taskPanel.setBackground(AppDimensions.BG_TASK_SCREEN);

        taskComponentPanel = new JPanel();
        taskComponentPanel.setLayout(new BoxLayout(taskComponentPanel, BoxLayout.Y_AXIS));
        taskComponentPanel.setBackground(AppDimensions.BG_TASK_SCREEN);
        taskComponentPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        taskPanel.add(taskComponentPanel);

        int scrollY = 64;
        int scrollH = barY - scrollY - 4;

        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBounds(8, scrollY, W - 16, scrollH);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AppDimensions.BG_TASK_SCREEN);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        getContentPane().setBackground(AppDimensions.BG_TASK_SCREEN);
        getContentPane().add(headerPanel);
        getContentPane().add(scrollPane);
        getContentPane().add(buttonPanel);
    }

    private String buildTitleHtml(String name) {
        return "<html><span style='color:#8782B2;font-family:Segoe UI;font-size:13px;'>Viewing&nbsp;</span>"
                + "<span style='color:#18123E;font-family:Segoe UI;font-size:15px;font-weight:bold;'>" + name + "</span></html>";
    }

    public void removeTask(Task task) { mainFrame.getTaskController().deleteTask(currentList, task); }

    public void goToTaskEditForm(Task task) {
        AppContext.getInstance().setCurrentTask(task);
        mainFrame.showEditTaskForm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if ("Back".equalsIgnoreCase(cmd) || "Main Menu".equalsIgnoreCase(cmd)) {
            AppContext.getInstance().setCurrentList(null);
            mainFrame.showMainListMenu();
        } else if ("Edit Name".equalsIgnoreCase(cmd)) {
            String n = MainListMenu.showThemedInput("Enter the new name for this list:", "Edit Name");
            if (n != null && !n.isEmpty())
                mainFrame.getListController().changeListName(ListRepository.getInstance().getAllLists(), currentList, n);
        } else if ("Add Task".equalsIgnoreCase(cmd)) {
            mainFrame.showAddTaskForm();
        } else if ("Sort".equalsIgnoreCase(cmd)) {
            populateTaskPanel(currentList, (SortCriterion) sortBox.getSelectedItem());
        } else if ("Delete List".equalsIgnoreCase(cmd)) {
            int c = MainListMenu.showThemedConfirm(
                    "Are you sure you want to delete \"" + currentList.getListName() + "\"?",
                    "Delete List");
            if (c == JOptionPane.YES_OPTION) {
                mainFrame.getListController().deleteList(ListRepository.getInstance().getAllLists(), currentList);
                mainFrame.showMainListMenu();
            }
        } else if ("Export".equalsIgnoreCase(cmd)) {
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            if (chooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION)
                mainFrame.getListController().exportTaskList(chooser.getSelectedFile(), currentList);
        }
    }

    private void populateTaskPanel(TaskList list, SortCriterion criterion) {
        if (taskComponentPanel != null) {
            taskComponentPanel.removeAll();
            for (Task task : list.getSortedList(criterion))
                taskComponentPanel.add(new TaskComponent(this, task));
            taskComponentPanel.revalidate();
            taskComponentPanel.repaint();
        }
    }

    @Override
    public void onListNameChange(TaskList list) {
        pageTitle.setText(buildTitleHtml(list.getListName()));
    }

    @Override
    public void onListStateChange(TaskList list) {
        populateTaskPanel(list, (SortCriterion) sortBox.getSelectedItem());
    }
}