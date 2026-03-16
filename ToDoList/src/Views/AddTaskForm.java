package Views;

import Controllers.TaskController;
import Data.AppContext;
import Models.Category;
import Models.Priority;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class AddTaskForm extends JFrame{

    private final TaskController taskController = new TaskController();

    private JLabel taskTitleLabel;
    private JComboBox<Category> categoryComboBox;
    private JLabel categoryLabel;
    private JComboBox<Priority> priorityComboBox;
    private JLabel priorityLabel;
    private JPanel mainPanel;
    private JButton submitButton;
    private JPanel buttonPanel;
    private JButton cancelButton;
    private JTextField taskTitleField;
    private JLabel dueDateLabel;
    private JSpinner dueDateSpinner;

    public AddTaskForm(MainFrame frame)
    {
        this.setContentPane(this.mainPanel);
        initializeCategoryComboBox();
        initializePriorityComboBox();
        initializeDueDateSpinner();



        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.showTaskMenu();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Date dateresult = (Date) dueDateSpinner.getValue();
                LocalDate localdateresult = dateresult.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                taskController.createTask(AppContext.getInstance().getCurrentList(), taskTitleField.getText(), (Category) categoryComboBox.getSelectedItem(),(Priority) priorityComboBox.getSelectedItem(), localdateresult );
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
                todayWithoutTime(),
                todayWithoutTime(),
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

    private Date todayWithoutTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
