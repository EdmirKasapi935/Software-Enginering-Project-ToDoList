package Services;

import Models.TaskList;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StorageService {

    //The name of the storage file
    private static final String FILE_NAME = getStoragePath();

    private static String getStoragePath() {
        File dir = new File(System.getProperty("user.dir"));
        File candidate = new File(dir, "src" + File.separator + "Storage" + File.separator + "lists.ser");
        if (candidate.exists()) return candidate.getAbsolutePath();

        candidate = new File(dir, "ToDoList" + File.separator + "src" + File.separator + "Storage" + File.separator + "lists.ser");
        if (candidate.exists()) return candidate.getAbsolutePath();

        return dir.getAbsolutePath() + File.separator + "ToDoList" + File.separator
                + "src" + File.separator + "Storage" + File.separator + "lists.ser";
    }

    //this function reads the data from the file
    public static List<TaskList> loadTasks()
    {
        List<TaskList> lists = new ArrayList<>();

        try(FileInputStream inputStream = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(inputStream) ) {

            lists =  (List<TaskList>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return lists;
    }

    //this function saves the data to the file
    public static void saveLists(List<TaskList> lists)
    {
        try(FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream) ) {

            oos.writeObject(lists);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
