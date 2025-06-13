package Main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.awt.Component;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileManager {
    public static final Gson gson = new Gson();

    // Save to File
    public static <T> void save(Component parent, T data, String filename, String fileExtension) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter(filename, fileExtension));
        fileChooser.setCurrentDirectory(new File(".")); // set defalu directory to current
        fileChooser.setFileSelectionMode(fileChooser.FILES_ONLY); // we can only select files not folders

        int result = fileChooser.showSaveDialog(parent); // open popup for selecting files, return 0 if click on save, 1
                                                         // if cancel, and -1 if any error occurs

        File selectedFile;

        if (result == fileChooser.APPROVE_OPTION) {

            selectedFile = fileChooser.getSelectedFile();

            if (selectedFile.getName().toLowerCase().endsWith("." + fileExtension) == false) {
                selectedFile = new File(selectedFile.getAbsolutePath() + "." + fileExtension);
            }

        } else {
            selectedFile = null;
        }

        if (selectedFile != null) {

            try (FileWriter writer = new FileWriter(selectedFile)) {
                gson.toJson(data, writer);
                JOptionPane.showMessageDialog(parent, "saved to" + selectedFile.getName()); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Load from file
    public static <T> T load(Component parent, Type typeOfT, String description, String fileExtension) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setFileSelectionMode(fileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter(description, fileExtension));

        int result = fileChooser.showOpenDialog(parent);

        File selectedFile;
        if (result == fileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        } else {
            return null;
        }

        try (FileReader reader = new FileReader(selectedFile)) {
            return gson.fromJson(reader, typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
