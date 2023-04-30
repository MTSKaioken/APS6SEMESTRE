package ui;

import lombok.Getter;
import lombok.Setter;
import utils.FileTypeFilter;
import utils.JFilePicker;

import javax.swing.*;
import java.awt.*;
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

    private JButton botaoSalvar = new JButton();

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

        ImageIcon iconeDefault = new ImageIcon("src/main/resources/no_image.jpg");
        iconeDefault.setImage(iconeDefault.getImage().getScaledInstance(416, 416, Image.SCALE_REPLICATE));

        imagemEntrada.setBounds(((int) size.getWidth() / 2)  - 436, 260, 416, 550);
        imagemSaida.setBounds(((int) size.getWidth() / 2) + 20, 260, 416, 550);
        imagemSaida.setIcon(iconeDefault);
        imagemEntrada.setIcon(iconeDefault);


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
        filter = new FileTypeFilter(".jpg", "jpg Images");
        filter = new FileTypeFilter(".JPG", "JPG Images");
        filter = new FileTypeFilter(".jpeg", "jpeg Images");
        filter = new FileTypeFilter(".JPEG", "JPEG Images");
        filters.add(filter);
        filter = new FileTypeFilter(".png", "PNG Images");
        filter = new FileTypeFilter(".PNG", "PNG Images");
        filters.add(filter);
        for(FileTypeFilter filterExtension : filters){
            files.addChoosableFileFilter(filterExtension);
        }

        this.filtros.setBounds(((int) size.getWidth() / 2) + 65 , 300, 300, 20);
        this.filtros.setModel(new DefaultComboBoxModel<>(filtros));

        botaoSalvar.setBounds(((int) size.getWidth() / 2)  - 436, 760, 872, 30);
        botaoSalvar.setText("Salvar Imagem");
        botaoSalvar.setFont(new Font("Dialog", Font.BOLD, 16));

        adicionarComponentesParaView(imagePanel);
    }

    public void limparImagens(){
        imagemEntrada.setIcon(new ImageIcon("src/main/resources/no_image.jpg"));
        imagemSaida.setIcon(new ImageIcon("src/main/resources/no_image.jpg"));
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
        imagePanel.add(botaoSalvar);
        add(imagePanel);
        setVisible(true);
    }

}
