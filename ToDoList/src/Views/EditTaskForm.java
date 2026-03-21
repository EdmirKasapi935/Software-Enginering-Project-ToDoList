package Views;

import Controllers.TaskController;
import Data.AppContext;
import Models.Category;
import Models.Priority;
import Models.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class EditTaskForm extends JFrame {

    private final Task currentTask = AppContext.getInstance().getCurrentTask();

    private final TaskController taskController = new TaskController();

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

    public EditTaskForm(MainFrame frame)
    {
        this.setContentPane(this.mainPanel);
        initializeCategoryComboBox();
        initializePriorityComboBox();
        initializeDueDateSpinner();
        fillFields();

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppContext.getInstance().setCurrentTask(null);
                frame.showTaskMenu();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Date dateResult = (Date) dueDateSpinner.getValue();
                LocalDate localDateResult = dateResult.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                taskController.editTask(currentTask, taskTitleField.getText(), (Category) categoryComboBox.getSelectedItem(), (Priority) priorityComboBox.getSelectedItem(), localDateResult);
            }
        });
    }

    private void initializeCategoryComboBox()
    {
        categoryComboBox.addItem(Category.PERSONAL);
        categoryComboBox.addItem(Category.WORK);
        categoryComboBox.addItem(Category.EVENT);
        categoryComboBox.addItem(Category.MISCELLANEOUS);
    }

    private void initializePriorityComboBox()
    {
        priorityComboBox.addItem(Priority.LOW);
        priorityComboBox.addItem(Priority.MEDIUM);
        priorityComboBox.addItem(Priority.HIGH);
    }

    private void initializeDueDateSpinner()
    {
        SpinnerDateModel dateModel = new SpinnerDateModel(
                Date.from(currentTask.getDueDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(currentTask.getDueDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                null,
                Calendar.DAY_OF_MONTH
        );

        dueDateSpinner.setModel(dateModel);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(dueDateSpinner, "dd/MM/yyyy");
        dueDateSpinner.setEditor(editor);

        editor.getTextField().setEditable(false);
        JFormattedTextField textField = editor.getTextField();
        textField.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
    }

    private void fillFields()
    {
        taskTitleField.setText(currentTask.getTaskText());
        categoryComboBox.setSelectedItem(currentTask.getTaskCategory());
        priorityComboBox.setSelectedItem(currentTask.getTaskPriority());
    }
}
