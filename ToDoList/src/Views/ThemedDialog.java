package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class ThemedDialog {

    public static void applyToOptionPane() {
        UIManager.put("OptionPane.background",          AppDimensions.BG_MAIN);
        UIManager.put("Panel.background",               AppDimensions.BG_MAIN);
        UIManager.put("OptionPane.messageForeground",   AppDimensions.TEXT_PRIMARY);
        UIManager.put("OptionPane.messageFont",         AppDimensions.FONT_BODY);
        UIManager.put("OptionPane.buttonFont",          AppDimensions.FONT_BTN);
        UIManager.put("Button.background",              AppDimensions.BTN_PRIMARY);
        UIManager.put("Button.foreground",              AppDimensions.BTN_PRIMARY_FG);
        UIManager.put("OptionPane.informationIcon",     makeInfoIcon());
        UIManager.put("OptionPane.questionIcon",        makeQuestionIcon());
        UIManager.put("OptionPane.warningIcon",         makeWarningIcon());
        UIManager.put("OptionPane.errorIcon",           makeErrorIcon());
    }

    private static Icon makeInfoIcon() {
        return new Icon() {
            public int getIconWidth()  { return 32; }
            public int getIconHeight() { return 32; }
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xD4EADB));
                g2.fillOval(x, y, 32, 32);
                g2.setColor(new Color(0x3D6B4F));
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(x+1, y+1, 30, 30);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
                g2.setColor(new Color(0x2A5A3A));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("i", x + (32 - fm.stringWidth("i")) / 2, y + 23);
                g2.dispose();
            }
        };
    }

    private static Icon makeQuestionIcon() {
        return new Icon() {
            public int getIconWidth()  { return 32; }
            public int getIconHeight() { return 32; }
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xE8D8C0));
                g2.fillOval(x, y, 32, 32);
                g2.setColor(new Color(0xB07840));
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(x+1, y+1, 30, 30);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
                g2.setColor(new Color(0x7A5010));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("?", x + (32 - fm.stringWidth("?")) / 2, y + 23);
                g2.dispose();
            }
        };
    }

    private static Icon makeWarningIcon() {
        return new Icon() {
            public int getIconWidth()  { return 32; }
            public int getIconHeight() { return 32; }
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xF5EDD4));
                int[] px = {x+16, x+1, x+31};
                int[] py = {y+2,  y+30, y+30};
                g2.fillPolygon(px, py, 3);
                g2.setColor(new Color(0xB07840));
                g2.setStroke(new BasicStroke(2f));
                g2.drawPolygon(px, py, 3);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
                g2.setColor(new Color(0x7A5010));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("!", x + (32 - fm.stringWidth("!")) / 2, y + 26);
                g2.dispose();
            }
        };
    }

    private static Icon makeErrorIcon() {
        return new Icon() {
            public int getIconWidth()  { return 32; }
            public int getIconHeight() { return 32; }
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xF5DFDF));
                g2.fillOval(x, y, 32, 32);
                g2.setColor(new Color(0x8B3A3A));
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(x+1, y+1, 30, 30);
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(x+10, y+10, x+22, y+22);
                g2.drawLine(x+22, y+10, x+10, y+22);
                g2.dispose();
            }
        };
    }

    public static int confirm(String message, String title) {
        JDialog dialog = new JDialog((Frame) null, title, true);
        int[] result = { JOptionPane.NO_OPTION };

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(AppDimensions.BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(28, 32, 20, 32));

        JPanel iconPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xE8D8C0));
                g2.fillOval(0, 0, 40, 40);
                g2.setColor(new Color(0xB07840));
                g2.setStroke(new BasicStroke(2f));
                g2.drawOval(1, 1, 38, 38);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
                g2.setColor(new Color(0x7A5010));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString("?", (40 - fm.stringWidth("?")) / 2, 28);
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(44, 44); }
        };
        iconPanel.setOpaque(false);

        JLabel msgLabel = new JLabel("<html><body style='font-family:Segoe UI;font-size:13px;color:#2A2018;width:220px;'>"
                + message + "</body></html>");
        msgLabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(iconPanel, BorderLayout.WEST);
        top.add(msgLabel,  BorderLayout.CENTER);
        root.add(top, BorderLayout.CENTER);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnRow.setOpaque(false);
        btnRow.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton noBtn  = AppDimensions.makeButton("No",  AppDimensions.BTN_NEUTRAL, AppDimensions.BTN_NEUTRAL_FG);
        JButton yesBtn = AppDimensions.makeButton("Yes", AppDimensions.BTN_DANGER,  AppDimensions.BTN_DANGER_FG);

        noBtn.addActionListener(e  -> { result[0] = JOptionPane.NO_OPTION;  dialog.dispose(); });
        yesBtn.addActionListener(e -> { result[0] = JOptionPane.YES_OPTION; dialog.dispose(); });

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,  0), "yes");
        root.getActionMap().put("yes", new AbstractAction() { public void actionPerformed(ActionEvent e) { result[0] = JOptionPane.YES_OPTION; dialog.dispose(); }});
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "no");
        root.getActionMap().put("no",  new AbstractAction() { public void actionPerformed(ActionEvent e) { dialog.dispose(); }});

        btnRow.add(noBtn);
        btnRow.add(yesBtn);
        root.add(btnRow, BorderLayout.SOUTH);

        root.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppDimensions.CARD_BORDER, 1),
                BorderFactory.createEmptyBorder(28, 32, 20, 32)));

        dialog.setContentPane(root);
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(AppDimensions.CARD_BORDER, 1));
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        return result[0];
    }

    public static String input(String message, String title) {
        JDialog dialog = new JDialog((Frame) null, title, true);
        String[] result = { null };

        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBackground(AppDimensions.BG_MAIN);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(AppDimensions.TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        JLabel msgLabel = new JLabel("<html><body style='font-family:Segoe UI;font-size:12px;color:#8A7D68;'>"
                + message + "</body></html>");

        JTextField field = AppDimensions.makeField();
        field.setPreferredSize(new Dimension(260, 38));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnRow.setOpaque(false);

        JButton cancelBtn = AppDimensions.makeButton("Cancel", AppDimensions.BTN_NEUTRAL, AppDimensions.BTN_NEUTRAL_FG);
        JButton okBtn     = AppDimensions.makeButton("OK",     AppDimensions.BTN_PRIMARY, AppDimensions.BTN_PRIMARY_FG);

        cancelBtn.addActionListener(e -> dialog.dispose());
        okBtn.addActionListener(e -> { result[0] = field.getText(); dialog.dispose(); });
        field.addActionListener(e  -> { result[0] = field.getText(); dialog.dispose(); });

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
        root.getActionMap().put("cancel", new AbstractAction() { public void actionPerformed(ActionEvent e) { dialog.dispose(); }});

        btnRow.add(cancelBtn);
        btnRow.add(okBtn);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);
        top.add(titleLabel);
        top.add(Box.createVerticalStrut(4));
        top.add(msgLabel);
        top.add(Box.createVerticalStrut(12));
        top.add(field);

        root.add(top,    BorderLayout.CENTER);
        root.add(btnRow, BorderLayout.SOUTH);

        root.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppDimensions.CARD_BORDER, 1),
                BorderFactory.createEmptyBorder(24, 28, 20, 28)));

        dialog.setContentPane(root);
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(AppDimensions.CARD_BORDER, 1));
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        SwingUtilities.invokeLater(field::requestFocusInWindow);
        dialog.setVisible(true);
        return result[0];
    }

    public static void message(String message, String title) {
        boolean isError = title != null && title.toLowerCase().contains("error");
        JDialog dialog = new JDialog((Frame) null, title, true);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(AppDimensions.BG_MAIN);

        JPanel iconPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isError) {
                    g2.setColor(new Color(0xF5DFDF));
                    g2.fillOval(0, 0, 40, 40);
                    g2.setColor(new Color(0x8B3A3A));
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawOval(1, 1, 38, 38);
                    g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(12, 12, 28, 28);
                    g2.drawLine(28, 12, 12, 28);
                } else {
                    g2.setColor(new Color(0xD4EADB));
                    g2.fillOval(0, 0, 40, 40);
                    g2.setColor(new Color(0x3D6B4F));
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawOval(1, 1, 38, 38);
                    g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.drawLine(10, 21, 17, 28);
                    g2.drawLine(17, 28, 30, 13);
                }
                g2.dispose();
            }
            @Override public Dimension getPreferredSize() { return new Dimension(44, 44); }
        };
        iconPanel.setOpaque(false);

        JLabel msgLabel = new JLabel("<html><body style='font-family:Segoe UI;font-size:13px;color:#2A2018;width:200px;'>"
                + message + "</body></html>");
        msgLabel.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(iconPanel, BorderLayout.WEST);
        top.add(msgLabel,  BorderLayout.CENTER);
        root.add(top, BorderLayout.CENTER);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnRow.setOpaque(false);
        btnRow.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton okBtn = AppDimensions.makeButton("OK", AppDimensions.BTN_PRIMARY, AppDimensions.BTN_PRIMARY_FG);
        okBtn.addActionListener(e -> dialog.dispose());

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,  0), "ok");
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ok");
        root.getActionMap().put("ok", new AbstractAction() { public void actionPerformed(ActionEvent e) { dialog.dispose(); }});

        btnRow.add(okBtn);
        root.add(btnRow, BorderLayout.SOUTH);

        root.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppDimensions.CARD_BORDER, 1),
                BorderFactory.createEmptyBorder(28, 32, 20, 32)));

        dialog.setContentPane(root);
        dialog.setUndecorated(true);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(AppDimensions.CARD_BORDER, 1));
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}