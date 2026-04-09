package Views;

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
        setLayout(new BorderLayout());
        addGuiComponents();
        refreshListPanel(taskLists);
        mainFrame.getListController().addListObserver(this);
    }

    private void addGuiComponents() {
        // ── Header ────────────────────────────────────────────────────────────
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(AppDimensions.BG_MAIN);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(0x3D6B4F));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.07f));
                g2.fillOval(getWidth() - 90, -30, 110, 110);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                g2.setColor(AppDimensions.CARD_BORDER);
                g2.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
                g2.dispose();
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 70));
        headerPanel.setOpaque(false);

        // Mushroom icon
        JPanel mushroomIcon = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xEDE0C8));
                g2.fillRoundRect(9, 22, 10, 12, 5, 5);
                g2.setColor(new Color(0xC0A880));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(9, 22, 10, 12, 5, 5);
                g2.setColor(new Color(0x8B3A3A));
                g2.fillArc(3, 4, 22, 20, 0, 180);
                g2.setColor(new Color(0xA04848));
                g2.fillRect(3, 16, 22, 5);
                g2.setColor(new Color(0x8B3A3A));
                g2.fillRoundRect(3, 14, 22, 6, 4, 4);
                g2.setColor(new Color(0xF5EEE4));
                g2.fillOval(7, 8, 4, 4);
                g2.fillOval(15, 7, 3, 3);
                g2.fillOval(11, 12, 3, 3);
                g2.dispose();
            }
        };
        mushroomIcon.setOpaque(false);
        mushroomIcon.setPreferredSize(new Dimension(44, 70));

        JLabel bannerLabel = new JLabel("My Lists", SwingConstants.CENTER);
        bannerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        bannerLabel.setForeground(AppDimensions.TEXT_PRIMARY);

        headerPanel.add(mushroomIcon,  BorderLayout.WEST);
        headerPanel.add(bannerLabel,   BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 44)); // balance mushroom width

        // ── Scroll area ───────────────────────────────────────────────────────
        JPanel listPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        listPanel.setBackground(AppDimensions.BG_MAIN);

        listComponentPanel = new JPanel();
        listComponentPanel.setLayout(new BoxLayout(listComponentPanel, BoxLayout.Y_AXIS));
        listComponentPanel.setBackground(AppDimensions.BG_MAIN);
        listComponentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        listPanel.add(listComponentPanel);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AppDimensions.BG_MAIN);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        // ── Button bar ────────────────────────────────────────────────────────
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
        buttonPanel.setPreferredSize(new Dimension(0, 72));

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
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane,  BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
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
        int choice = showThemedConfirm("Are you sure you want to delete \"" + list.getListName() + "\"?", "Delete List");
        if (choice == JOptionPane.YES_OPTION)
            mainFrame.getListController().deleteList(this.taskLists, list);
    }

    private void searchLists() {
        String term = showThemedInput("Search lists by name:", "Search");
        if (term != null && !term.isEmpty())
            refreshListPanel(taskLists.stream().filter(n -> n.getListName().contains(term)).toList());
        else
            refreshListPanel(taskLists);
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

    static int showThemedConfirm(String message, String title) { return ThemedDialog.confirm(message, title); }
    static String showThemedInput(String message, String title) { return ThemedDialog.input(message, title); }

    public void goToTaskMenu() { mainFrame.showTaskMenu(); }
}