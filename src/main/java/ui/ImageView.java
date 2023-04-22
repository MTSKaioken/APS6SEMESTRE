package ui;

import controller.ImageController;
import lombok.Getter;
import lombok.Setter;
import utils.FileTypeFilter;
import utils.JFilePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ImageView extends JFrame {



    private JLabel imagemEntrada = new JLabel();
    private JLabel imagemSaida = new JLabel();
    private JLabel tituloThreshold = new JLabel();
    private JFileChooser files = new JFileChooser();
    private JCheckBox inverterPretoBranco = new JCheckBox();
    private JSpinner valorThreshold = new JSpinner();
    private JComboBox<String> filtros = new JComboBox<>();
    private FileTypeFilter filter;

    public ImageView() {
        setarPropriedadesComponentes();
    }

    private void setarPropriedadesComponentes() {
        JPanel imagePanel = new JPanel();
        JFilePicker filePicker = new JFilePicker();

        imagePanel.setLayout(null);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension minimumSize = new Dimension(406, 302);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(minimumSize);
        setLocationRelativeTo(null);
        setResizable(true);
        setTitle("Processamento de Imagens");

        imagemEntrada.setBounds(((int) size.getWidth() / 2)  - 436, 260, 416, 550);
        imagemSaida.setBounds(((int) size.getWidth() / 2) + 20, 260, 416, 550);

        inverterPretoBranco.setText("Inverter preto e branco");
        inverterPretoBranco.setBounds(((int) size.getWidth() / 2) - 225, 300, 155, 14);



        setIconImage(new ImageIcon("src/main/resources/iconePrograma.png").getImage());

        files.setCurrentDirectory(new File("src/main/resources/"));
        files.setVisible(true);
        files.setBounds(0, 0, (int) size.getWidth(), 300);



        tituloThreshold.setBounds(((int) size.getWidth() / 2) - 65, 300, 155, 14);
        tituloThreshold.setText("Threshold:");
        valorThreshold.setBounds(((int) size.getWidth() / 2), 300, 55, 20);
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 255, 1);
        valorThreshold.setModel(model);


        filePicker.setMode(JFilePicker.MODE_OPEN);

        String[] filtros = { "Filtro Preto e Branco", "Filtro Negativo", "Filtro Remover ruido", "Filtro Melhorar contraste", "Filtro Tons de cinza", "Filtro Detectar Bordas" };
        List<FileTypeFilter> filters = new ArrayList<>();
        filter = new FileTypeFilter(".jpg", "JPEG Images");
        filters.add(filter);
        filter = new FileTypeFilter(".png", "PNG Images");
        filters.add(filter);
        for(FileTypeFilter filterExtension : filters){
            files.addChoosableFileFilter(filterExtension);
        }

        this.filtros.setBounds(((int) size.getWidth() / 2) + 65 , 300, 150, 20);
        this.filtros.setModel(new DefaultComboBoxModel<>(filtros));

        adicionarComponentesParaView(imagePanel);
    }

    public void limparImagens(){
        imagemEntrada.setIcon(null);
        imagemSaida.setIcon(null);
    }

    public void exibirMensagem(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem);
    }

    public void observaArquivoSelecionado(ActionListener actionListener){
        files.addActionListener(actionListener);
    }

    public void observaFiltroSelecionado(ActionListener actionListener){
        filtros.addActionListener(actionListener);
    }

    private void adicionarComponentesParaView(JPanel imagePanel) {
        imagePanel.add(imagemEntrada);
        imagePanel.add(imagemSaida);
        imagePanel.add(files);
        imagePanel.add(tituloThreshold);
        imagePanel.add(inverterPretoBranco);
        imagePanel.add(valorThreshold);
        imagePanel.add(filtros);
        add(imagePanel);
        setVisible(true);
    }

}
