package utils;

import javax.swing.*;

public class JFilePicker{
    private JFileChooser fileChooser;

    private int mode;
    public static final int MODE_OPEN = 1;

    public JFilePicker(){
        fileChooser = new JFileChooser();
    }
    public void addFileTypeFilter(String extension, String description) {
        FileTypeFilter filter = new FileTypeFilter(extension, description);
        fileChooser.addChoosableFileFilter(filter);
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

}
