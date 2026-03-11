package Views;

import Views.AppDimensions;

import javax.swing.*;

public class MainFrame extends JFrame {

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

    private void switchWindow(JFrame frame)
    {
        this.setContentPane(frame.getContentPane());
        repaint();
        revalidate();
    }

}
