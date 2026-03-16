package Services;

import Models.TaskList;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StorageService {

    private static final String FILE_NAME = "src\\Storage\\lists.ser";

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
