package Views;

import Controllers.ListController;
import Data.ListRepository;
import Models.TaskList;
import Observers.ListObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainListMenu extends JFrame implements ActionListener, ListObserver {

    private final MainFrame mainFrame;
    private final List<TaskList> taskLists = ListRepository.getInstance().getAllLists();

    private JPanel listPanel, listComponentPanel;

    public MainListMenu(MainFrame mainFrame){

        this.mainFrame = mainFrame;
        mainFrame.getListController().addListObserver(this);

        setLayout(null);


        addGuiComponents();
        refreshListPanel(taskLists);

    }

    private void addGuiComponents() {

        JLabel bannerLabel = new JLabel("Main Menu");
        bannerLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        bannerLabel.setBounds((AppDimensions.BANNER_SIZE.width - bannerLabel.getPreferredSize().width)/2,
                15,
                AppDimensions.BANNER_SIZE.width,
                AppDimensions.BANNER_SIZE.height
        );

        listPanel = new JPanel();

        listComponentPanel = new JPanel();
        listComponentPanel.setLayout((new BoxLayout(listComponentPanel, BoxLayout.Y_AXIS)));
        listComponentPanel.add(Box.createVerticalGlue());
        listPanel.add(listComponentPanel);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(8, 70,  AppDimensions.TASKPANEL_SIZE.width,  AppDimensions.TASKPANEL_SIZE.height);
        scrollPane.setMaximumSize( AppDimensions.TASKPANEL_SIZE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBounds(-5,  AppDimensions.GUI_SIZE.height - 88,  AppDimensions.ADDTASKBUTTON_SIZE.width,  AppDimensions.ADDTASKBUTTON_SIZE.height);

        JButton addListButton = new JButton("Add List");
        addListButton.addActionListener(this);
        buttonPanel.add(addListButton);

        JButton reportFormButton = new JButton("View Report");
        reportFormButton.addActionListener(this);
        buttonPanel.add(reportFormButton);

        JButton searchButton = new JButton("Search Lists");
        searchButton.addActionListener(this);
        buttonPanel.add(searchButton);

        this.getContentPane().add(bannerLabel);
        this.getContentPane().add(scrollPane);
        this.getContentPane().add(buttonPanel);
        this.getContentPane().setBackground(new Color(126, 132, 188));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("Add List")) {
            String listName = JOptionPane.showInputDialog("Enter the name of the new List");

            if (listName != null && !listName.isEmpty()) {
                mainFrame.getListController().createList(taskLists, listName);
            }
        } else if (command.equalsIgnoreCase("View Report")) {
            mainFrame.showReportForm();
        } else if (command.equalsIgnoreCase("Search Lists")) {
            searchLists();
        }
    }

    public void removeList(TaskList list)
    {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + list.getListName() + "?", "Delete Task List", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            mainFrame.getListController().deleteList(this.taskLists, list);
        }
    }

    private void searchLists()
    {
        String searchTerm = JOptionPane.showInputDialog("Enter the name of the lists you would like to search below");

        //if the input is null, the list just reverts to normal
        if (searchTerm != null && !searchTerm.isEmpty()) {

            List<TaskList> result = taskLists.stream().filter(n -> n.getListName().contains(searchTerm)).toList();
            refreshListPanel(result);
        }
        else {
            refreshListPanel(taskLists);
        }

    }

    @Override
    public void onListsCollectionUpdated(List<TaskList> taskLists) {
        refreshListPanel(taskLists);
    }

    private void refreshListPanel(List<TaskList> taskLists) {

        if (listComponentPanel != null)
        {
            listComponentPanel.removeAll();

            for (TaskList list: taskLists) {
                listComponentPanel.add(new ListComponent(this, list.getListName(), list));
            }

            listComponentPanel.revalidate();
            listComponentPanel.repaint();
        }


    }

    public void goToTaskMenu()
    {
        mainFrame.showTaskMenu();
    }

}
