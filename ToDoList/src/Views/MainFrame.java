package Views;

import Controllers.ListController;
import Controllers.ReportController;
import Controllers.TaskController;
import Data.ListRepository;
import Scheduler.NotificationScheduler;
import Services.NotificationService;
import Services.StorageService;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    private final NotificationScheduler scheduler;
    private final ListController listController;
    private final TaskController taskController;
    private final ReportController reportController;

    public MainFrame() {
        applyLookAndFeel();
        ThemedDialog.applyToOptionPane();
        NotificationService notificationService = new NotificationService();
        this.scheduler      = new NotificationScheduler(notificationService);
        this.listController   = new ListController();
        this.taskController   = new TaskController();
        this.reportController = new ReportController();
        this.setSize(AppDimensions.GUI_SIZE);
        this.setTitle("ToDoList");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        NotificationService.initialize();
        scheduler.start();
        showMainListMenu();
        this.setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { onExit(); }
        });
    }

    private void applyLookAndFeel() {
        boolean flat = tryFlatLaf();
        if (!flat) tryNimbus();
        applyGlobalOverrides();
    }

    private boolean tryFlatLaf() {
        try {
            Class<?> c = Class.forName("com.formdev.flatlaf.FlatIntelliJLaf");
            UIManager.setLookAndFeel((javax.swing.LookAndFeel) c.getDeclaredConstructor().newInstance());

            UIManager.put("defaultFont",                   new FontUIResource(AppDimensions.FONT_BODY));
            UIManager.put("Component.focusColor",          AppDimensions.ACCENT);
            UIManager.put("Component.focusedBorderColor",  AppDimensions.ACCENT);

            UIManager.put("Button.arc",                    999);
            UIManager.put("Button.background",             AppDimensions.BTN_PRIMARY);
            UIManager.put("Button.foreground",             AppDimensions.BTN_PRIMARY_FG);
            UIManager.put("Button.hoverBackground",        AppDimensions.BTN_PRIMARY.darker());
            UIManager.put("Button.innerFocusWidth",        0);

            UIManager.put("TextComponent.arc",             8);
            UIManager.put("TextField.background",          AppDimensions.BG_CARD);
            UIManager.put("TextField.foreground",          AppDimensions.TEXT_PRIMARY);
            UIManager.put("TextField.caretForeground",     AppDimensions.ACCENT);

            UIManager.put("ComboBox.arc",                  8);
            UIManager.put("ComboBox.background",           AppDimensions.BG_CARD);
            UIManager.put("ComboBox.selectionBackground",  new Color(0xD8EADC));
            UIManager.put("ComboBox.selectionForeground",  AppDimensions.TEXT_PRIMARY);

            UIManager.put("Spinner.arc",                   8);
            UIManager.put("Spinner.background",            AppDimensions.BG_CARD);

            UIManager.put("ScrollBar.width",               8);
            UIManager.put("ScrollBar.thumbArc",            999);
            UIManager.put("ScrollBar.thumb",               new ColorUIResource(new Color(0xC0B090)));
            UIManager.put("ScrollBar.hoverThumbColor",     new ColorUIResource(new Color(0xA09070)));
            UIManager.put("ScrollBar.track",               new ColorUIResource(AppDimensions.BG_MAIN));
            UIManager.put("ScrollBar.showButtons",         false);

            UIManager.put("CheckBox.arc",                  4);
            UIManager.put("CheckBox.icon.checkedBackground",  AppDimensions.ACCENT);
            UIManager.put("CheckBox.icon.checkmarkColor",     AppDimensions.BTN_PRIMARY_FG);
            UIManager.put("CheckBox.icon.borderColor",        new Color(0xC0B090));
            UIManager.put("CheckBox.icon.hoverBorderColor",   AppDimensions.ACCENT);


            UIManager.put("Panel.background",              new ColorUIResource(AppDimensions.BG_MAIN));
            UIManager.put("OptionPane.background",         new ColorUIResource(AppDimensions.BG_CARD));
            UIManager.put("OptionPane.messageForeground",  new ColorUIResource(AppDimensions.TEXT_PRIMARY));

            UIManager.put("ToolTip.background",            new ColorUIResource(new Color(0xF0EBE0)));
            UIManager.put("ToolTip.foreground",            new ColorUIResource(AppDimensions.TEXT_PRIMARY));

            UIManager.put("Separator.foreground",          new ColorUIResource(AppDimensions.CARD_BORDER));

            return true;
        } catch (Exception e) { return false; }
    }

    private void tryNimbus() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
                if ("Nimbus".equals(info.getName())) { UIManager.setLookAndFeel(info.getClassName()); break; }
        } catch (Exception ignored) {}
    }

    private void applyGlobalOverrides() {
        FontUIResource fur = new FontUIResource(AppDimensions.FONT_BODY);
        for (String k : new String[]{
                "Button.font","Label.font","TextField.font","TextArea.font",
                "ComboBox.font","CheckBox.font","List.font","Spinner.font",
                "TextPane.font","EditorPane.font","RadioButton.font","ToolTip.font"
        }) UIManager.put(k, fur);

        UIManager.put("Panel.background",              new ColorUIResource(AppDimensions.BG_MAIN));
        UIManager.put("ScrollPane.border",             BorderFactory.createEmptyBorder());
        UIManager.put("TextPane.background",           new ColorUIResource(AppDimensions.BG_CARD));
        UIManager.put("TextPane.border",               BorderFactory.createEmptyBorder(4,6,4,6));
        UIManager.put("CheckBox.background",           new ColorUIResource(new Color(0,0,0,0)));
        UIManager.put("TextField.selectionBackground", new ColorUIResource(new Color(0xC8DFC8)));
        UIManager.put("ComboBox.selectionBackground",  new ColorUIResource(new Color(0xD8EADC)));
        UIManager.put("ComboBox.selectionForeground",  new ColorUIResource(AppDimensions.TEXT_PRIMARY));
    }

    public void showMainListMenu() { switchWindow(new MainListMenu(this)); }
    public void showTaskMenu()     { switchWindow(new TaskMenu(this)); }
    public void showAddTaskForm()  { switchWindow(new AddTaskForm(this)); }
    public void showEditTaskForm() { switchWindow(new EditTaskForm(this)); }
    public void showReportForm()   { switchWindow(new ReportForm(this)); }

    private void switchWindow(JFrame frame) {
        this.setContentPane(frame.getContentPane());
        repaint(); revalidate();
    }

    public Controllers.ListController   getListController()   { return listController; }
    public Controllers.TaskController   getTaskController()   { return taskController; }
    public Controllers.ReportController getReportController() { return reportController; }

    private void onExit() {
        int choice = ThemedDialog.confirm("Are you sure you want to exit?", "Exit");
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            StorageService.saveLists(ListRepository.getInstance().getAllLists());
            scheduler.stop();
            System.exit(0);
        }
    }
}