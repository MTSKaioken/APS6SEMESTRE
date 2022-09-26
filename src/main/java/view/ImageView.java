package view;

import utils.FileTypeFilter;
import utils.JFilePicker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ImageView extends JFrame {

    private JLabel imagemEntrada = new JLabel();
    private JLabel imagemSaida = new JLabel();
    private JLabel arrow = new JLabel();
    private JFileChooser files = new JFileChooser();

    private FileTypeFilter filter;

    public ImageView() {
        JPanel imagePanel = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 1000);
        setLocationRelativeTo(null);
        imagePanel.setLayout(null);
        setResizable(false);

        files.setBounds(10, 10, 610, 300);

        imagemEntrada.setBounds(10, 400, 250, 260);
        imagemSaida.setBounds(360, 400, 250, 260);
        arrow.setIcon(new ImageIcon("C:\\Users\\KiriD\\Documents\\Intellij\\APS6SEMESTRE\\src\\main\\resources\\arrow.jpg"));
        arrow.setBounds(270, 400, 10, 10);

        files.setVisible(true);

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


        imagePanel.add(imagemEntrada);
        imagePanel.add(imagemSaida);
        imagePanel.add(files);
        imagePanel.add(arrow);
        this.add(imagePanel);

    }

    public JFileChooser getFiles() {
        return files;
    }

    public FileTypeFilter getFilter() {
        return filter;
    }

    public JLabel getImagemEntrada() {
        return imagemEntrada;
    }

    public JLabel getImagemSaida() {
        return imagemSaida;
    }
}
