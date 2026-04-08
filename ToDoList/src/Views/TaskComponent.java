package Views;

import Models.Priority;
import Models.Status;
import Models.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class TaskComponent extends JPanel implements ActionListener {

    private JCheckBox checkbox;
    private final JTextPane taskField;
    private final JButton deleteButton;
    private final JButton editButton;
    private final TaskMenu parentWindow;
    private final Task task;
    private final Color priorityColor;
    private final Color cardBg;

    private static final String CMD_DELETE = "DELETE_TASK";
    private static final String CMD_EDIT   = "EDIT_TASK";

    public TaskComponent(TaskMenu parentWindow, Task task) {
        this.parentWindow = parentWindow;
        this.task         = task;

        if      (task.getTaskPriority() == Priority.HIGH)   { priorityColor = AppDimensions.PRIORITY_HIGH;   cardBg = AppDimensions.ROW_HIGH; }
        else if (task.getTaskPriority() == Priority.MEDIUM) { priorityColor = AppDimensions.PRIORITY_MEDIUM; cardBg = AppDimensions.ROW_MEDIUM; }
        else                                                 { priorityColor = AppDimensions.PRIORITY_LOW;    cardBg = AppDimensions.ROW_LOW; }

        taskField = new JTextPane();
        taskField.setEditable(false);
        taskField.setContentType("text/html");
        taskField.setOpaque(false);
        taskField.setBackground(new Color(0, 0, 0, 0));
        taskField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 4));
        renderTaskText(task);

        activateCheckbox();

        editButton   = makePencilButton();
        deleteButton = makeTrashButton();
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);

        setOpaque(false);
        setLayout(new BorderLayout(0, 0));
        setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 10));
        setPreferredSize(new Dimension(AppDimensions.TASKPANEL_SIZE.width - 10, 62));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));

        JPanel west = new JPanel(new GridBagLayout());
        west.setOpaque(false);
        west.setPreferredSize(new Dimension(36, 52));
        west.add(checkbox);

        JPanel east = new JPanel();
        east.setOpaque(false);
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.setPreferredSize(new Dimension(38, 52));
        east.add(Box.createVerticalGlue());
        east.add(editButton);
        east.add(Box.createVerticalStrut(4));
        east.add(deleteButton);
        east.add(Box.createVerticalGlue());

        add(west,      BorderLayout.WEST);
        add(taskField, BorderLayout.CENTER);
        add(east,      BorderLayout.EAST);
    }

    //edit icon
    private JButton makePencilButton() {
        Color bg = new Color(0xC8EDD8);
        JButton b = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color fill = getModel().isPressed() ? bg.darker().darker()
                        : getModel().isRollover() ? bg.darker() : bg;
                g2.setColor(fill);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                Color ink = new Color(0x1A5C35);
                g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(ink);

                g2.drawLine(cx - 5, cy - 6, cx + 2, cy - 6); // top (partial)
                g2.drawLine(cx - 5, cy - 6, cx - 5, cy + 6); // left
                g2.drawLine(cx - 5, cy + 6, cx + 5, cy + 6); // bottom
                g2.drawLine(cx + 5, cy + 6, cx + 5, cy - 2); // right (partial)

                g2.drawLine(cx + 2, cy - 6, cx + 5, cy - 2); // fold diagonal
                g2.setColor(new Color(0x4A9A6A));
                g2.fillPolygon(
                        new int[]{cx+2, cx+5, cx+5},
                        new int[]{cy-6, cy-6, cy-2}, 3
                );
                g2.setColor(ink);
                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawPolygon(
                        new int[]{cx+2, cx+5, cx+5},
                        new int[]{cy-6, cy-6, cy-2}, 3
                );

                g2.setStroke(new BasicStroke(1.3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(cx - 3, cy - 1, cx + 3, cy - 1);
                g2.drawLine(cx - 3, cy + 2, cx + 3, cy + 2);

                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
            @Override public Dimension getPreferredSize() { return new Dimension(32, 24); }
            @Override public Dimension getMaximumSize()   { return new Dimension(32, 24); }
            @Override public Dimension getMinimumSize()   { return new Dimension(32, 24); }
        };
        styleIconButton(b, CMD_EDIT);
        return b;
    }

    //tashcan icon
    private JButton makeTrashButton() {
        Color bg = new Color(0xF5DFDF);
        JButton b = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color fill = getModel().isPressed() ? bg.darker().darker()
                        : getModel().isRollover() ? bg.darker() : bg;
                g2.setColor(fill);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                Color ink = new Color(0x7A1E1E);
                g2.setColor(ink);
                g2.setStroke(new BasicStroke(1.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                g2.drawLine(cx - 6, cy - 4, cx + 6, cy - 4);
                g2.drawLine(cx - 2, cy - 4, cx - 2, cy - 6);
                g2.drawLine(cx + 2, cy - 4, cx + 2, cy - 6);
                g2.drawLine(cx - 2, cy - 6, cx + 2, cy - 6);

                int[] bx = { cx - 5, cx + 5, cx + 4, cx - 4 };
                int[] by = { cy - 3, cy - 3, cy + 5, cy + 5 };
                g2.setColor(new Color(0xF5BFBF));
                g2.fillPolygon(bx, by, 4);
                g2.setColor(ink);
                g2.drawPolygon(bx, by, 4);

                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(cx,     cy - 2, cx,     cy + 4);
                g2.drawLine(cx - 2, cy - 2, cx - 2, cy + 4);
                g2.drawLine(cx + 2, cy - 2, cx + 2, cy + 4);

                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
            @Override public Dimension getPreferredSize() { return new Dimension(32, 24); }
            @Override public Dimension getMaximumSize()   { return new Dimension(32, 24); }
            @Override public Dimension getMinimumSize()   { return new Dimension(32, 24); }
        };
        styleIconButton(b, CMD_DELETE);
        return b;
    }

    private void styleIconButton(JButton b, String cmd) {
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        b.setActionCommand(cmd);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int pad = 4;
        float x = pad, y = pad, w = getWidth()-2f*pad, h = getHeight()-2f*pad, arc = 10f;

        g2.setColor(new Color(50, 30, 20, 10));
        g2.fill(new RoundRectangle2D.Float(x+1, y+3, w, h, arc, arc));

        Color bg = task.isOverdue()  ? AppDimensions.STATUS_OVERDUE  :
                task.isDueToday() ? AppDimensions.STATUS_DUE_TODAY : cardBg;
        g2.setColor(bg);
        g2.fill(new RoundRectangle2D.Float(x, y, w, h, arc, arc));

        g2.clip(new RoundRectangle2D.Float(x, y, w, h, arc, arc));
        g2.setColor(priorityColor);
        g2.fillRect((int)x, (int)y, 5, (int)h);
        g2.setClip(null);

        Color bc = task.isOverdue()  ? AppDimensions.BORDER_OVERDUE  :
                task.isDueToday() ? AppDimensions.BORDER_DUE_TODAY : AppDimensions.CARD_BORDER;
        g2.setColor(bc);
        g2.setStroke(new BasicStroke(1f));
        g2.draw(new RoundRectangle2D.Float(x, y, w-1, h-1, arc, arc));
        g2.dispose();
    }

    private void renderTaskText(Task task) {
        boolean done = task.getStatus() == Status.DONE;
        String nc = done ? "#B0A898" : "#2A2018";
        String mc = done ? "#C8BEA8" : "#8A7D68";
        String sk = done ? "text-decoration:line-through;" : "";
        taskField.setText(
                "<html><body style='font-family:Segoe UI;margin:0;padding:0;'>"
                        + "<div style='font-size:13px;color:"+nc+";font-weight:bold;"+sk+"'>"+task.getTaskText()+"</div>"
                        + "<div style='font-size:10px;color:"+mc+";margin-top:2px;'>"
                        + task.getTaskCategory()+" &nbsp;&middot;&nbsp; Due: "+task.getDueDateString()
                        + "</div></body></html>");
    }

    private void activateCheckbox() {
        checkbox = new JCheckBox();
        checkbox.setOpaque(false);
        checkbox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkbox.addActionListener(this);
        if (task.getStatus() == Status.DONE) checkbox.setSelected(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkbox) {
            if (checkbox.isSelected()) task.markAsDone();
            else                       task.markAsUndone();
            renderTaskText(task);
            repaint();
            return;
        }
        String cmd = e.getActionCommand();
        if (CMD_DELETE.equals(cmd)) parentWindow.removeTask(task);
        if (CMD_EDIT.equals(cmd))   parentWindow.goToTaskEditForm(task);
    }
}