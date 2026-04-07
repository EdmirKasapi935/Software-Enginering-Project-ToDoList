package Views;

import Data.AppContext;
import Models.Category;
import Models.Priority;
import Models.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class EditTaskForm extends JFrame {

    private final MainFrame mainFrame;
    private final Task currentTask = AppContext.getInstance().getCurrentTask();
    private JPanel mainPanel;
    private JLabel taskTitleLabel;
    private JComboBox<Category> categoryComboBox;
    private JLabel categoryLabel;
    private JComboBox<Priority> priorityComboBox;
    private JLabel priorityLabel;
    private JTextField taskTitleField;
    private JLabel dueDateLabel;
    private JSpinner dueDateSpinner;
    private JPanel buttonPanel;
    private JButton submitButton;
    private JButton cancelButton;

    public EditTaskForm(MainFrame frame) {
        this.mainFrame = frame;
        buildMainPanel();
        this.setContentPane(this.mainPanel);
        initializeCategoryComboBox();
        initializePriorityComboBox();
        initializeDueDateSpinnerForEdit();
        fillFields();
        submitButton.addActionListener(e -> editTaskClicked());
        cancelButton.addActionListener(e -> cancelButtonClicked());
    }

    private void buildMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppDimensions.BG_MAIN);

        JPanel headerPanel = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(AppDimensions.BG_MAIN);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(AppDimensions.ACCENT);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.06f));
                g2.fillOval(-20, -20, 80, 80);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                g2.setColor(AppDimensions.CARD_BORDER);
                g2.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
                g2.dispose();
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 72));

        JLabel header = new JLabel("Edit Task");
        header.setFont(new Font("Segoe UI", Font.BOLD, 21));
        header.setForeground(AppDimensions.TEXT_PRIMARY);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBounds(0, 16, AppDimensions.GUI_SIZE.width, 40);
        headerPanel.add(header);

        JPanel card = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(60, 40, 10, 10));
                g2.fill(new RoundRectangle2D.Float(2, 4, getWidth()-4, getHeight()-4, 16, 16));
                g2.setColor(AppDimensions.BG_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-3, 16, 16));
                g2.setColor(AppDimensions.CARD_BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth()-2, getHeight()-4, 16, 16));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 28, 20, 28));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(3, 0, 3, 0);
        gc.gridx = 0; gc.gridwidth = 1; gc.weightx = 1;

        gc.gridy = 0;  card.add(fieldLabel("Task Title"), gc);
        gc.gridy = 1;
        taskTitleField = AppDimensions.makeField();
        taskTitleField.setPreferredSize(new Dimension(0, 40));
        card.add(taskTitleField, gc);
        gc.gridy = 2;  card.add(spacer(8), gc);

        gc.gridy = 3;  card.add(fieldLabel("Category"), gc);
        gc.gridy = 4;  categoryComboBox = styledCombo(); card.add(categoryComboBox, gc);
        gc.gridy = 5;  card.add(spacer(8), gc);

        gc.gridy = 6;  card.add(fieldLabel("Priority"), gc);
        gc.gridy = 7;  priorityComboBox = styledCombo(); card.add(priorityComboBox, gc);
        gc.gridy = 8;  card.add(spacer(8), gc);

        gc.gridy = 9;  card.add(fieldLabel("Due Date"), gc);
        gc.gridy = 10;
        dueDateSpinner = new JSpinner();
        dueDateSpinner.setFont(AppDimensions.FONT_BODY);
        dueDateSpinner.setBackground(AppDimensions.BG_CARD);
        card.add(dueDateSpinner, gc);

        gc.gridy = 11; gc.weighty = 1; card.add(Box.createVerticalGlue(), gc); gc.weighty = 0;

        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setOpaque(false);
        cardWrapper.setBackground(AppDimensions.BG_MAIN);
        cardWrapper.setBorder(BorderFactory.createEmptyBorder(18, 24, 8, 24));
        cardWrapper.add(card, BorderLayout.CENTER);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 14)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(AppDimensions.CARD_BORDER);
                g.drawLine(0, 0, getWidth(), 0);
            }
        };
        buttonPanel.setBackground(AppDimensions.BG_BUTTON_BAR);
        cancelButton = AppDimensions.makeButton("Cancel",       AppDimensions.BTN_NEUTRAL, AppDimensions.BTN_NEUTRAL_FG);
        submitButton = AppDimensions.makeButton("Save Changes", AppDimensions.BTN_PRIMARY, AppDimensions.BTN_PRIMARY_FG);
        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardWrapper, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    @SuppressWarnings("unchecked")
    private <T> JComboBox<T> styledCombo() {
        JComboBox<T> b = new JComboBox<>();
        b.setFont(AppDimensions.FONT_BODY);
        b.setBackground(AppDimensions.BG_CARD);
        b.setForeground(AppDimensions.TEXT_PRIMARY);
        return b;
    }

    private JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(AppDimensions.TEXT_MUTED);
        l.setBorder(BorderFactory.createEmptyBorder(0, 2, 4, 0));
        return l;
    }

    private Component spacer(int h) { return Box.createRigidArea(new Dimension(0, h)); }

    private void editTaskClicked() {
        Date d = (Date) dueDateSpinner.getValue();
        LocalDate ld = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        mainFrame.getTaskController().editTask(currentTask, taskTitleField.getText(),
                (Category) categoryComboBox.getSelectedItem(),
                (Priority) priorityComboBox.getSelectedItem(), ld);
        AppContext.getInstance().setCurrentTask(null);
        mainFrame.showTaskMenu();
    }

    private void cancelButtonClicked() {
        AppContext.getInstance().setCurrentTask(null);
        mainFrame.showTaskMenu();
    }

    private void initializeCategoryComboBox() {
        categoryComboBox.addItem(Category.PERSONAL);
        categoryComboBox.addItem(Category.WORK);
        categoryComboBox.addItem(Category.EVENT);
        categoryComboBox.addItem(Category.MISCELLANEOUS);
    }

    private void initializePriorityComboBox() {
        priorityComboBox.addItem(Priority.LOW);
        priorityComboBox.addItem(Priority.MEDIUM);
        priorityComboBox.addItem(Priority.HIGH);
    }

    private void initializeDueDateSpinnerForEdit() {
        Date start = todayWithoutTime();
        if (currentTask.getDueDate().isBefore(LocalDate.now()))
            start = Date.from(currentTask.getDueDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        SpinnerDateModel m = new SpinnerDateModel(
                Date.from(currentTask.getDueDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                start, null, Calendar.DAY_OF_MONTH);
        dueDateSpinner.setModel(m);
        JSpinner.DateEditor ed = new JSpinner.DateEditor(dueDateSpinner, "dd/MM/yyyy");
        dueDateSpinner.setEditor(ed);
        ed.getTextField().setEditable(false);
        ed.getTextField().setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
    }

    private void fillFields() {
        taskTitleField.setText(currentTask.getTaskText());
        categoryComboBox.setSelectedItem(currentTask.getTaskCategory());
        priorityComboBox.setSelectedItem(currentTask.getTaskPriority());
    }

    private Date todayWithoutTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,0); c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0); c.set(Calendar.MILLISECOND,0);
        return c.getTime();
    }
}