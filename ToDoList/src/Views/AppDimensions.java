package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class AppDimensions {

    public static final Dimension GUI_SIZE           = new Dimension(540, 800);
    public static final Dimension BANNER_SIZE        = new Dimension(GUI_SIZE.width, 60);
    public static final Dimension TASKPANEL_SIZE     = new Dimension(GUI_SIZE.width - 30, GUI_SIZE.height - 175);
    public static final Dimension ADDTASKBUTTON_SIZE = new Dimension(GUI_SIZE.width, 50);
    public static final Dimension TASKFIELD_SIZE     = new Dimension((int)(TASKPANEL_SIZE.width * 0.80), 50);
    public static final Dimension CHECKBOX_SIZE      = new Dimension((int)(TASKFIELD_SIZE.width * 0.05), 50);
    public static final Dimension DELETE_BUTTON_SIZE = new Dimension((int)(TASKFIELD_SIZE.width * 0.12), 50);


    public static final Color BG_MAIN        = new Color(0xF5F0E8);  // warm cream
    public static final Color BG_TASK_SCREEN = new Color(0xEFEADF);  // slightly toasted cream
    public static final Color BG_BUTTON_BAR  = new Color(0xFAF7F2);  // lightest cream
    public static final Color BG_CARD        = new Color(0xFFFDF8);  // near-white warm

    public static final Color ACCENT         = new Color(0x3D6B4F);  // deep forest green

    //Buttons
    public static final Color BTN_PRIMARY    = new Color(0x3D6B4F);  // forest green
    public static final Color BTN_PRIMARY_FG = new Color(0xFFFDF8);
    public static final Color BTN_SECONDARY  = new Color(0x6B8F5E);  // sage green
    public static final Color BTN_SECONDARY_FG = new Color(0xFFFDF8);
    public static final Color BTN_DANGER     = new Color(0x8B3A3A);  // soft maroon
    public static final Color BTN_DANGER_FG  = new Color(0xFFFDF8);
    public static final Color BTN_NEUTRAL    = new Color(0xE8E0D0);  // warm sand
    public static final Color BTN_NEUTRAL_FG = new Color(0x3D3020);  // dark brown text

    public static final Color PRIORITY_HIGH   = new Color(0x8B3A3A);  // maroon
    public static final Color PRIORITY_MEDIUM = new Color(0xB07840);  // warm amber-brown
    public static final Color PRIORITY_LOW    = new Color(0x4A7C59);  // forest green

    public static final Color ROW_HIGH   = new Color(0xFDF5F0);  // very soft rose-cream
    public static final Color ROW_MEDIUM = new Color(0xFDF8EE);  // warm honey tint
    public static final Color ROW_LOW    = new Color(0xF2F8F2);  // soft green-white

    public static final Color STATUS_DUE_TODAY = new Color(0xFDF6E3);
    public static final Color STATUS_OVERDUE   = new Color(0xFDF0EC);
    public static final Color STATUS_NORMAL    = new Color(0xFFFDF8);

    public static final Color BORDER_DUE_TODAY = new Color(0xB07840);
    public static final Color BORDER_OVERDUE   = new Color(0x8B3A3A);
    public static final Color CARD_BORDER      = new Color(0xDDD5C0);  // warm tan border

    public static final Color TEXT_PRIMARY = new Color(0x2A2018);  // warm near-black
    public static final Color TEXT_MUTED   = new Color(0x8A7D68);  // warm grey-brown
    public static final Color TEXT_ACCENT  = new Color(0x3D6B4F);

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD,  22);
    public static final Font FONT_BTN   = new Font("Segoe UI", Font.BOLD,  12);
    public static final Font FONT_LIST  = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD,  11);
    public static final Font FONT_BODY  = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    public static JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean pressed  = getModel().isPressed();
                boolean rollover = getModel().isRollover();
                if (!pressed) {
                    // warm shadow
                    g2.setColor(new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), 45));
                    g2.fill(new RoundRectangle2D.Float(1, 3, getWidth()-2, getHeight()-1, 10, 10));
                }
                Color fill = pressed ? bg.darker().darker() : rollover ? bg.darker() : bg;
                g2.setColor(fill);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight()-(pressed?0:2), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(fg);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(9, 20, 10, 20));
        return btn;
    }

    public static JButton makeIconButton(String label, Color bg, Color fg, String actionCmd) {
        JButton btn = new JButton(label) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color fill = getModel().isPressed() ? bg.darker().darker()
                        : getModel().isRollover() ? bg.darker() : bg;
                g2.setColor(fill);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
            @Override protected void paintBorder(Graphics g) {}
            @Override public Dimension getPreferredSize() { return new Dimension(46, 22); }
            @Override public Dimension getMaximumSize()   { return new Dimension(46, 22); }
            @Override public Dimension getMinimumSize()   { return new Dimension(46, 22); }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btn.setForeground(fg);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        btn.setActionCommand(actionCmd);
        return btn;
    }

    public static JTextField makeField() {
        JTextField f = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        f.setFont(FONT_BODY);
        f.setForeground(TEXT_PRIMARY);
        f.setOpaque(false);
        f.setCaretColor(ACCENT);
        f.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(new Color(0xC8BFA8), 1, 8),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return f;
    }

    public static class RoundBorder implements javax.swing.border.Border {
        private final Color color; private final int thick, arc;
        public RoundBorder(Color c, int t, int a) { color=c; thick=t; arc=a; }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color); g2.setStroke(new BasicStroke(thick));
            g2.draw(new RoundRectangle2D.Float(x+.5f, y+.5f, w-1, h-1, arc, arc));
            g2.dispose();
        }
        @Override public Insets getBorderInsets(Component c) { return new Insets(thick,thick,thick,thick); }
        @Override public boolean isBorderOpaque() { return false; }
    }
}