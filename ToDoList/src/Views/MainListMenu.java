package Views;

import Controllers.ListController;
import Data.ListRepository;
import Models.TaskList;
import Observers.ListObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class MainListMenu extends JFrame implements ActionListener, ListObserver {

    private final MainFrame mainFrame;
    private final List<TaskList> taskLists = ListRepository.getInstance().getAllLists();
    private JPanel listComponentPanel;

    public MainListMenu(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);
        addGuiComponents();
        refreshListPanel(taskLists);                    // initial load first
        mainFrame.getListController().addListObserver(this); // observer registered AFTER
    }

    private void addGuiComponents() {
        int W = AppDimensions.GUI_SIZE.width;
        int H = AppDimensions.GUI_SIZE.height;

        JPanel headerPanel = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(AppDimensions.BG_MAIN);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(0x3D6B4F));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.07f));
                g2.fillOval(W - 90, -30, 110, 110);
                g2.fillOval(W - 50, 10, 70, 70);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                g2.setColor(AppDimensions.CARD_BORDER);
                g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                g2.dispose();
            }
        };
        headerPanel.setBounds(0, 0, W, 70);

        //mushroom icon on mainpage
        JPanel leafIcon = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0xEDE0C8));
                g2.fillRoundRect(12, 22, 10, 12, 5, 5);
                g2.setColor(new Color(0xC0A880));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(12, 22, 10, 12, 5, 5);

                g2.setColor(new Color(0x8B3A3A));
                g2.fillArc(3, 4, 28, 26, 0, 180);

                g2.setColor(new Color(0xA04848));
                g2.fillRect(3, 17, 28, 5);
                g2.setColor(new Color(0x8B3A3A));
                g2.fillRoundRect(3, 15, 28, 6, 4, 4);

                g2.setColor(new Color(0xF5EEE4));
                g2.fillOval(8,  8, 5, 5);
                g2.fillOval(19, 7, 5, 5);
                g2.fillOval(13, 13, 4, 4);

                g2.dispose();
            }
        };
        leafIcon.setOpaque(false);
        leafIcon.setBounds(14, 12, 36, 40);
        headerPanel.add(leafIcon);

        JLabel bannerLabel = new JLabel("My Lists");
        bannerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        bannerLabel.setForeground(AppDimensions.TEXT_PRIMARY);
        bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bannerLabel.setBounds(0, 14, W, 42);
        headerPanel.add(bannerLabel);

        JPanel listPanel = new JPanel();
        listPanel.setBackground(AppDimensions.BG_MAIN);

        listComponentPanel = new JPanel();
        listComponentPanel.setLayout(new BoxLayout(listComponentPanel, BoxLayout.Y_AXIS));
        listComponentPanel.setBackground(AppDimensions.BG_MAIN);
        listComponentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        listPanel.add(listComponentPanel);

        int barH = 72;
        int barY = H - barH - 28;

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(8, 72, W - 16, barY - 76);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AppDimensions.BG_MAIN);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 14)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(AppDimensions.BG_BUTTON_BAR);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(AppDimensions.CARD_BORDER);
                g2.drawLine(0, 0, getWidth(), 0);
                g2.dispose();
            }
        };
        buttonPanel.setBounds(0, barY, W, barH);

        JButton addListButton    = AppDimensions.makeButton("+ Add List",  AppDimensions.BTN_PRIMARY,   AppDimensions.BTN_PRIMARY_FG);
        JButton reportFormButton = AppDimensions.makeButton("View Report", AppDimensions.BTN_SECONDARY, AppDimensions.BTN_SECONDARY_FG);
        JButton searchButton     = AppDimensions.makeButton("Search",      AppDimensions.BTN_NEUTRAL,   AppDimensions.BTN_NEUTRAL_FG);

        addListButton.setActionCommand("Add List");
        reportFormButton.setActionCommand("View Report");
        searchButton.setActionCommand("Search");

        addListButton.addActionListener(this);
        reportFormButton.addActionListener(this);
        searchButton.addActionListener(this);

        buttonPanel.add(addListButton);
        buttonPanel.add(reportFormButton);
        buttonPanel.add(searchButton);

        getContentPane().setBackground(AppDimensions.BG_MAIN);
        getContentPane().add(headerPanel);
        getContentPane().add(scrollPane);
        getContentPane().add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if ("Add List".equalsIgnoreCase(cmd)) {
            String listName = showThemedInput("Enter the name of the new list:", "New List");
            if (listName != null && !listName.isEmpty())
                mainFrame.getListController().createList(taskLists, listName);
        } else if ("View Report".equalsIgnoreCase(cmd)) {
            mainFrame.showReportForm();
        } else if ("Search".equalsIgnoreCase(cmd)) {
            searchLists();
        }
    }

    public void removeList(TaskList list) {
        int choice = showThemedConfirm(
                "Are you sure you want to delete \"" + list.getListName() + "\"?",
                "Delete List");
        if (choice == JOptionPane.YES_OPTION)
            mainFrame.getListController().deleteList(this.taskLists, list);
    }

    private void searchLists() {
        String term = showThemedInput("Search lists by name:", "Search");
        if (term != null && !term.isEmpty()) {
            refreshListPanel(taskLists.stream().filter(n -> n.getListName().contains(term)).toList());
        } else {
            refreshListPanel(taskLists);
        }
    }

    @Override public void onListsCollectionUpdated(List<TaskList> lists) { refreshListPanel(lists); }

    private void refreshListPanel(List<TaskList> lists) {
        if (listComponentPanel != null) {
            listComponentPanel.removeAll();
            for (TaskList list : lists)
                listComponentPanel.add(new ListComponent(this, list.getListName(), list));
            listComponentPanel.revalidate();
            listComponentPanel.repaint();
        }
    }

    static int showThemedConfirm(String message, String title) {
        return ThemedDialog.confirm(message, title);
    }

    static String showThemedInput(String message, String title) {
        return ThemedDialog.input(message, title);
    }

    public void goToTaskMenu() { mainFrame.showTaskMenu(); }
}