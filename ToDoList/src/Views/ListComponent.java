package Views;

import Data.AppContext;
import Models.Task;
import Models.TaskList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class ListComponent extends JPanel implements ActionListener {

    private final JButton listButton;
    private final JButton deleteButton;
    private final MainListMenu parentWindow;
    private final TaskList taskList;

    private static final String CMD_DELETE = "DELETE_LIST";

    public ListComponent(MainListMenu parentWindow, String listName, TaskList taskList) {
        this.parentWindow = parentWindow;
        this.taskList     = taskList;

        boolean overdue  = taskList.getTasks().stream().anyMatch(Task::isOverdue);
        boolean dueToday = taskList.getTasks().stream().anyMatch(Task::isDueToday);
        int taskCount    = taskList.getTasks().size();

        Color stripColor = overdue  ? AppDimensions.PRIORITY_HIGH :
                dueToday ? AppDimensions.PRIORITY_MEDIUM : AppDimensions.PRIORITY_LOW;

        String statusText  = overdue  ? "Overdue tasks" : dueToday ? "Due today" : "";
        String statusColor = overdue  ? "#8B3A3A" : "#B07840";

        String statusHtml = statusText.isEmpty() ? "" :
                "<br><span style='font-size:10px;color:" + statusColor + ";font-weight:bold;'>" + statusText + "</span>";

        String btnHtml = "<html><div style='text-align:left;font-family:Segoe UI;padding:2px 0;'>"
                + "<span style='font-size:14px;color:#2A2018;font-weight:bold;'>" + listName + "</span>"
                + "<br><span style='font-size:10px;color:#8A7D68;'>"
                + taskCount + " task" + (taskCount != 1 ? "s" : "") + "</span>"
                + statusHtml + "</div></html>";

        listButton = new JButton(btnHtml) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover() ? new Color(0xEDE8DC) : AppDimensions.BG_CARD;
                g2.setColor(new Color(80, 60, 20, 12));
                g2.fill(new RoundRectangle2D.Float(1, 3, getWidth()-2, getHeight()-2, 12, 12));
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-2, 12, 12));
                g2.setColor(stripColor);
                g2.clip(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-2, 12, 12));
                g2.fillRect(0, 0, 5, getHeight());
                g2.setClip(null);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bc = overdue  ? AppDimensions.BORDER_OVERDUE :
                        dueToday ? AppDimensions.BORDER_DUE_TODAY : AppDimensions.CARD_BORDER;
                float t = (overdue || dueToday) ? 1.5f : 1f;
                g2.setColor(bc);
                g2.setStroke(new BasicStroke(t));
                g2.draw(new RoundRectangle2D.Float(t/2f, t/2f, getWidth()-t-1, getHeight()-t-2, 12, 12));
                g2.dispose();
            }
        };
        listButton.setActionCommand(listName);
        listButton.setFont(AppDimensions.FONT_LIST);
        listButton.setHorizontalAlignment(SwingConstants.LEFT);
        listButton.setContentAreaFilled(false);
        listButton.setOpaque(false);
        listButton.setFocusPainted(false);
        listButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        int cardH = statusText.isEmpty() ? 60 : 74;
        listButton.setPreferredSize(new Dimension((int)(AppDimensions.GUI_SIZE.width * 0.72), cardH));
        listButton.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 14));
        listButton.addActionListener(this);

        deleteButton = makeTrashButton();
        deleteButton.addActionListener(this);

        JPanel eastPanel = new JPanel(new GridBagLayout());
        eastPanel.setOpaque(false);
        eastPanel.add(deleteButton);

        setLayout(new BorderLayout(8, 0));
        setBackground(AppDimensions.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(5, 14, 5, 14));
        add(listButton, BorderLayout.CENTER);
        add(eastPanel,  BorderLayout.EAST);
    }

    private JButton makeTrashButton() {
        Color bg = new Color(0xF0E0E0);
        JButton b = new JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color fill = getModel().isPressed() ? bg.darker().darker()
                        : getModel().isRollover() ? bg.darker() : bg;
                g2.setColor(fill);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                int cx = getWidth() / 2, cy = getHeight() / 2;
                Color ink = new Color(0x7A1E1E);
                g2.setColor(ink);
                g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                // Lid
                g2.drawLine(cx-6, cy-4, cx+6, cy-4);
                g2.drawLine(cx-2, cy-4, cx-2, cy-6);
                g2.drawLine(cx+2, cy-4, cx+2, cy-6);
                g2.drawLine(cx-2, cy-6, cx+2, cy-6);
                // Body
                int[] bx = {cx-5, cx+5, cx+4, cx-4};
                int[] by = {cy-3, cy-3, cy+5, cy+5};
                g2.setColor(new Color(0xF5BFBF));
                g2.fillPolygon(bx, by, 4);
                g2.setColor(ink);
                g2.drawPolygon(bx, by, 4);
                // Ribs
                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(cx,   cy-2, cx,   cy+4);
                g2.drawLine(cx-2, cy-2, cx-2, cy+4);
                g2.drawLine(cx+2, cy-2, cx+2, cy+4);
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
            @Override public Dimension getPreferredSize() { return new Dimension(36, 28); }
            @Override public Dimension getMaximumSize()   { return new Dimension(36, 28); }
            @Override public Dimension getMinimumSize()   { return new Dimension(36, 28); }
        };
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setActionCommand(CMD_DELETE);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (CMD_DELETE.equals(e.getActionCommand())) {
            parentWindow.removeList(this.taskList);
        } else if (e.getActionCommand().equalsIgnoreCase(this.taskList.getListName())) {
            AppContext.getInstance().setCurrentList(this.taskList);
            parentWindow.goToTaskMenu();
        }
    }
}