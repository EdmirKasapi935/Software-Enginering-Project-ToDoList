package Views;

import Models.TaskList;
import Views.AppDimensions;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    ArrayList<TaskList> taskLists = new ArrayList<>();

    public MainFrame()
    {

        this.setSize(AppDimensions.GUI_SIZE);
        this.setTitle("ToDoList");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        showMainListMenu();

        this.setVisible(true);

    }

    public void showMainListMenu()
    {
        switchWindow(new MainListMenu(this));
    }

    public void showTaskMenu()
    {
        switchWindow(new TaskMenu(this));
    }

    public void showAddTaskForm()
    {
        switchWindow(new AddTaskForm(this));
    }

    private void switchWindow(JFrame frame)
    {
        this.setContentPane(frame.getContentPane());
        repaint();
        revalidate();
    }

}
