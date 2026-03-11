package Views;

import Controllers.ListController;
import Models.TaskList;
import Observers.ListObserver;
import Views.AppDimensions;
import Views.ListComponent;
import Views.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainListMenu extends JFrame implements ActionListener, ListObserver {

    private final ListController listController = new ListController();
    private final ArrayList<TaskList> taskLists = new ArrayList<TaskList>();

    private JPanel listPanel, listComponentPanel ;

    public MainListMenu(MainFrame mainFrame){

        listController.addListObserver(this);

        pack();
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
        listPanel.add(listComponentPanel);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(8, 70,  AppDimensions.TASKPANEL_SIZE.width,  AppDimensions.TASKPANEL_SIZE.height);
        scrollPane.setMaximumSize( AppDimensions.TASKPANEL_SIZE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton addListButton = new JButton("Add List");
        addListButton.setBounds(-5,  AppDimensions.GUI_SIZE.height - 88,  AppDimensions.ADDTASKBUTTON_SIZE.width,  AppDimensions.ADDTASKBUTTON_SIZE.height);
        addListButton.addActionListener(this);

        this.getContentPane().add(bannerLabel);
        this.getContentPane().add(scrollPane);
        this.getContentPane().add(addListButton);
        this.getContentPane().setBackground(new Color(126, 132, 188));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("Add List")) {
            String listName = JOptionPane.showInputDialog("Enter the name of the new List");

            if (listName != null && !listName.isEmpty()) {
                listController.createList(taskLists, listName);
            }
        }
    }

    public void removeList(TaskList list)
    {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + list.getListName() + "?", "Delete Task List", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            listController.deleteList(this.taskLists, list);
        }
    }

    @Override
    public void onListsCollectionUpdated(List<TaskList> taskLists) {
        refreshListPanel(taskLists);
    }

    private void refreshListPanel(List<TaskList> taskLists) {
        listComponentPanel.removeAll();

        for (TaskList list: taskLists) {
            listComponentPanel.add(new ListComponent(this, list.getListName(), list));
        }

        listComponentPanel.repaint();
        listComponentPanel.revalidate();
    }

}
