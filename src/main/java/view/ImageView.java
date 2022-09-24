package view;

import utils.FileTypeFilter;
import utils.JFilePicker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ImageView extends JFrame {

    private JLabel imagem = new JLabel();
    private JFileChooser files = new JFileChooser();

    private FileTypeFilter filter;

    public ImageView() {
        JPanel imagePanel = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1000);
        setLocationRelativeTo(null);

        JFilePicker filePicker = new JFilePicker();
        filePicker.setMode(JFilePicker.MODE_OPEN);

        List<FileTypeFilter> filters = new ArrayList<>();

        filter = new FileTypeFilter(".jpg", "JPEG Images");
        filters.add(filter);
        filter = new FileTypeFilter(".png", "PNG Images");
        filters.add(filter);

        for(FileTypeFilter filterExtension : filters){
            files.addChoosableFileFilter(filterExtension);
        }


        imagePanel.add(imagem);
        imagePanel.add(files);
        this.add(imagePanel);

    }

    public JFileChooser getFiles() {
        return files;
    }

    public FileTypeFilter getFilter() {
        return filter;
    }

    public JLabel getImagem() {
        return imagem;
    }
}
