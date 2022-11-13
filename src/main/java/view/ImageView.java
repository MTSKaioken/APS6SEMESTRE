package view;

import utils.FileTypeFilter;
import utils.JFilePicker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ImageView extends JFrame {

    private JLabel imagemEntrada = new JLabel();
    private JLabel imagemSaida = new JLabel();
    //private JLabel arrow = new JLabel();
    private JFileChooser files = new JFileChooser();

    private JCheckBox jCheckBox = new JCheckBox("Inverter preto e branco");

    private JSpinner jSpinner = new JSpinner();

    private FileTypeFilter filter;

    public ImageView() {
        JPanel imagePanel = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        //setSize(640, 1000);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        imagePanel.setLayout(null);
        setResizable(false);

        files.setBounds(0, 0, (int) size.getWidth(), 300);

        jCheckBox.setBounds(0, 300, 155, 14);

        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 255, 1);
        jSpinner.setModel(model);
        jSpinner.setBounds(156, 300, 55, 20);

        imagemEntrada.setBounds(20, 300, 416, 560);
        imagemSaida.setBounds(447, 300, 416, 560);


        files.setVisible(true);

        JFilePicker filePicker = new JFilePicker();
        filePicker.setMode(JFilePicker.MODE_OPEN);

        List<FileTypeFilter> filters = new ArrayList<>();

        filter = new FileTypeFilter(".jpg", "JPEG Images");
        filters.add(filter);
        filter = new FileTypeFilter(".png", "PNG Images");
        filters.add(filter);
        filter = new FileTypeFilter(".bmp", "BMP Images");
        filters.add(filter);

        for(FileTypeFilter filterExtension : filters){
            files.addChoosableFileFilter(filterExtension);
        }


        imagePanel.add(imagemEntrada);
        imagePanel.add(imagemSaida);
        imagePanel.add(files);
        imagePanel.add(jCheckBox);
        imagePanel.add(jSpinner);
        //imagePanel.add(arrow);
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

    public JCheckBox getjCheckBox() {
        return jCheckBox;
    }

    public JSpinner getjSpinner() {
        return jSpinner;
    }
}
