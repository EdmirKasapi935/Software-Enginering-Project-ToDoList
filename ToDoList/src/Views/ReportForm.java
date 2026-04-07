package Views;

import Data.ListRepository;
import Models.Priority;
import Models.ReportData;
import Observers.ReportObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;

public class ReportForm extends JFrame implements ReportObserver {

    private final MainFrame mainFrame;
    private final ReportData taskReport;

    private JPanel formPanel;
    private JLabel pageTitle;
    private JLabel totalTasksTitle,      totalTasksValueLabel;
    private JLabel completedTasksTitle,  completedTasksValueLabel;
    private JLabel pendingTasksTitle,    pendingTasksValueLabel;
    private JLabel dueTodayTasksTitle,   dueTodayTasksValueLabel;
    private JLabel overdueTasksTitle,    overdueTasksValueLabel;
    private JLabel prioritySectionLabel;
    private JLabel highPriorityTitle,    highPriorityValueLabel;
    private JLabel mediumPriorityTitle,  mediumPriorityValueLabel;
    private JLabel lowPriorityTitle,     lowPriorityValueLabel;
    private JPanel ButtonPanel;
    private JButton mainMenuButton, exportButton;
    private JPanel mainPanel;

    public ReportForm(MainFrame frame) {
        this.mainFrame  = frame;
        this.taskReport = mainFrame.getReportController().generateReport(ListRepository.getInstance());
        buildMainPanel();
        this.setContentPane(mainPanel);
        mainFrame.getReportController().addReportObserver(this);
        showReportValues(this.taskReport);
        exportButton.addActionListener(e -> exportButtonClicked());
        mainMenuButton.addActionListener(e -> mainMenuButtonClicked());
    }

    private void buildMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppDimensions.BG_MAIN);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(AppDimensions.CARD_BORDER);
                g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
            }
        };
        headerPanel.setBackground(AppDimensions.BG_MAIN);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 14, 0));
        pageTitle = new JLabel("Task Report");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pageTitle.setForeground(AppDimensions.TEXT_PRIMARY);
        headerPanel.add(pageTitle);

        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(AppDimensions.BG_MAIN);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(7, 4, 7, 4);
        gc.weightx = 0.5;

        Font sf = new Font("Segoe UI", Font.PLAIN, 14);
        Color lc = AppDimensions.TEXT_MUTED;

        totalTasksTitle      = rowLabel(formPanel,gc,0,"Total Tasks",sf,lc);
        totalTasksValueLabel = chip(formPanel,gc,0, new Color(0xE8E0D0), new Color(0x3D3020));

        completedTasksTitle      = rowLabel(formPanel,gc,1,"Completed",sf,lc);
        completedTasksValueLabel = chip(formPanel,gc,1, new Color(0xD4EADB), new Color(0x1E5230));

        pendingTasksTitle      = rowLabel(formPanel,gc,2,"Pending",sf,lc);
        pendingTasksValueLabel = chip(formPanel,gc,2, new Color(0xEBE4D4), new Color(0x4A3A20));

        dueTodayTasksTitle      = rowLabel(formPanel,gc,3,"Due Today",sf,lc);
        dueTodayTasksValueLabel = chip(formPanel,gc,3, new Color(0xF5EDD4), new Color(0x7A5010));

        overdueTasksTitle      = rowLabel(formPanel,gc,4,"Overdue",sf,lc);
        overdueTasksValueLabel = chip(formPanel,gc,4, new Color(0xF5DFDF), new Color(0x6B2020));

        gc.gridy=5; gc.gridx=0; gc.gridwidth=2; gc.insets=new Insets(14,4,14,4);
        JSeparator sep = new JSeparator(); sep.setForeground(AppDimensions.CARD_BORDER);
        formPanel.add(sep,gc); gc.gridwidth=1; gc.insets=new Insets(7,4,7,4);

        gc.gridy=6; gc.gridx=0; gc.gridwidth=2;
        prioritySectionLabel = new JLabel("By Priority");
        prioritySectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        prioritySectionLabel.setForeground(AppDimensions.TEXT_PRIMARY);
        formPanel.add(prioritySectionLabel,gc); gc.gridwidth=1;

        highPriorityTitle      = rowLabel(formPanel,gc,7,"High",sf,lc);
        highPriorityValueLabel = chip(formPanel,gc,7, new Color(0xF5DFDF), new Color(0x6B2020));

        mediumPriorityTitle      = rowLabel(formPanel,gc,8,"Medium",sf,lc);
        mediumPriorityValueLabel = chip(formPanel,gc,8, new Color(0xF5EDD4), new Color(0x7A5010));

        lowPriorityTitle      = rowLabel(formPanel,gc,9,"Low",sf,lc);
        lowPriorityValueLabel = chip(formPanel,gc,9, new Color(0xD4EADB), new Color(0x1E5230));

        gc.gridy=10; gc.gridx=0; gc.gridwidth=2; gc.weighty=1;
        formPanel.add(Box.createVerticalGlue(),gc);

        ButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 14)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(AppDimensions.CARD_BORDER);
                g.drawLine(0, 0, getWidth(), 0);
            }
        };
        ButtonPanel.setBackground(AppDimensions.BG_BUTTON_BAR);
        mainMenuButton = AppDimensions.makeButton("Back",          AppDimensions.BTN_NEUTRAL,   AppDimensions.BTN_NEUTRAL_FG);
        exportButton   = AppDimensions.makeButton("Export Report", AppDimensions.BTN_SECONDARY, AppDimensions.BTN_SECONDARY_FG);
        ButtonPanel.add(mainMenuButton);
        ButtonPanel.add(exportButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel,   BorderLayout.CENTER);
        mainPanel.add(ButtonPanel, BorderLayout.SOUTH);
    }

    private JLabel rowLabel(JPanel p, GridBagConstraints gc, int row, String text, Font f, Color c) {
        gc.gridy=row; gc.gridx=0;
        JLabel l = new JLabel(text); l.setFont(f); l.setForeground(c);
        p.add(l,gc); return l;
    }

    private JLabel chip(JPanel p, GridBagConstraints gc, int row, Color chipBg, Color chipFg) {
        gc.gridy=row; gc.gridx=1;
        JLabel l = new JLabel("—") {
            @Override protected void paintComponent(Graphics g) {
                if (!"—".equals(getText())) {
                    Graphics2D g2=(Graphics2D)g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(chipBg.getRed(),chipBg.getGreen(),chipBg.getBlue(),80));
                    g2.fill(new RoundRectangle2D.Float(1,4,getWidth()-2,getHeight()-4,12,12));
                    g2.setColor(chipBg);
                    g2.fill(new RoundRectangle2D.Float(0,2,getWidth()-1,getHeight()-5,12,12));
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        l.setFont(new Font("Segoe UI",Font.BOLD,15));
        l.setForeground(chipFg);
        l.setOpaque(false);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setBorder(BorderFactory.createEmptyBorder(3,14,3,14));
        l.setPreferredSize(new Dimension(70,30));
        p.add(l,gc); return l;
    }

    private void exportButtonClicked() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            mainFrame.getReportController().exportReportData(chooser.getSelectedFile(), taskReport);
    }

    private void mainMenuButtonClicked() { mainFrame.showMainListMenu(); }

    private void showReportValues(ReportData r) {
        totalTasksValueLabel.setText(String.valueOf(r.getTotalTasks()));
        completedTasksValueLabel.setText(String.valueOf(r.getCompletedTasks()));
        pendingTasksValueLabel.setText(String.valueOf(r.getPendingTasks()));
        dueTodayTasksValueLabel.setText(String.valueOf(r.getDueTodayTasks()));
        overdueTasksValueLabel.setText(String.valueOf(r.getOverdueTasks()));
        Map<Priority,Integer> pc = r.getPriorityCounts();
        highPriorityValueLabel.setText(String.valueOf(pc.getOrDefault(Priority.HIGH,0)));
        mediumPriorityValueLabel.setText(String.valueOf(pc.getOrDefault(Priority.MEDIUM,0)));
        lowPriorityValueLabel.setText(String.valueOf(pc.getOrDefault(Priority.LOW,0)));
    }

    @Override public void onReportStateChanged(ReportData r) { showReportValues(r); }
}