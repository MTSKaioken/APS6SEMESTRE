package view;

import utils.FileTypeFilter;
import utils.JFilePicker;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageView extends JFrame {

    private JLabel imagemEntrada = new JLabel();
    private JLabel imagemSaida = new JLabel();
    private JLabel tituloThreshold = new JLabel();
    //private JLabel arrow = new JLabel();
    private JFileChooser files = new JFileChooser();

    private JCheckBox jCheckBox = new JCheckBox("Inverter preto e branco");

    private JSpinner jSpinner = new JSpinner();

    private JComboBox<String> jComboBox = new JComboBox<>();

    private FileTypeFilter filter;

    public ImageView() {
        JPanel imagePanel = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        //setSize(640, 1000);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        imagePanel.setLayout(null);
        this.setTitle("Processamento de Imagens");
        this.setIconImage(new ImageIcon("src/main/resources/iconePrograma.png").getImage());
        setResizable(false);

        // dps apagar a linha 41
        files.setCurrentDirectory(new File("src/main/resources/"));
        files.setBounds(0, 0, (int) size.getWidth(), 300);

        //lts
        //jCheckBox.setBounds(0, 300, 155, 14);

        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 255, 1);
        jSpinner.setModel(model);

        tituloThreshold.setText("Threshold:");
        //lts
        //jSpinner.setBounds(156, 300, 55, 20);

        // lts
        //jComboBox.setBounds(220, 300, 150, 20);

        jCheckBox.setBounds(((int) size.getWidth() / 2) - 225, 300, 155, 14);
        tituloThreshold.setBounds(((int) size.getWidth() / 2) - 65, 300, 155, 14);
        jSpinner.setBounds(((int) size.getWidth() / 2), 300, 55, 20);
        jComboBox.setBounds(((int) size.getWidth() / 2) + 65 , 300, 150, 20);


        //lts
        //imagemEntrada.setBounds(20, 250, 416, 560);
        //imagemSaida.setBounds(447, 250, 416, 560);

        imagemEntrada.setBounds(((int) size.getWidth() / 2)  - 436, 260, 416, 550);
        imagemSaida.setBounds(((int) size.getWidth() / 2) + 20, 260, 416, 550);

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

        String[] filtros = { "Preto e Branco", "Negativo", "Remover ruido", "Melhorar contraste", "Tons de cinza", "Detectar Bordas" };
        jComboBox.setModel(new DefaultComboBoxModel<>(filtros));




        imagePanel.add(imagemEntrada);
        imagePanel.add(imagemSaida);
        imagePanel.add(files);
        imagePanel.add(tituloThreshold);
        imagePanel.add(jCheckBox);
        imagePanel.add(jSpinner);
        imagePanel.add(jComboBox);
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

    public JComboBox getjComboBox() {
        return jComboBox;
    }
}
