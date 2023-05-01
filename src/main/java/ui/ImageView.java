package ui;

import lombok.Getter;
import lombok.Setter;
import utils.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

@Getter
@Setter
public class ImageView extends JFrame {

    private JLabel imagemEntrada = new JLabel();
    private JLabel imagemSaida = new JLabel();
    private JFileChooser files = new JFileChooser();
    private FileFilter extensoesAceitas = new FileFilter() {
        @Override
        public boolean accept(File file) {
            String extension = StringUtils.getExtension(file);
            if (extension != null) {
                if (extension.endsWith("png") || extension.endsWith("jpg")) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return null;
        }
    };


    private JFileChooser exploradorSaida = new JFileChooser();
    private JComboBox<String> filtros = new JComboBox<>();
    private JButton botaoSalvar = new JButton();

    public ImageView() {
        setarPropriedadesComponentes();
    }

    private void setarPropriedadesComponentes() {
        JPanel imagePanel = new JPanel();

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

        imagemEntrada.setBounds(((int) size.getWidth() / 2) - 436, 260, 416, 550);
        imagemSaida.setBounds(((int) size.getWidth() / 2) + 20, 260, 416, 550);
        imagemSaida.setIcon(iconeDefault);
        imagemEntrada.setIcon(iconeDefault);

        setIconImage(new ImageIcon("src/main/resources/iconePrograma.png").getImage());

        files.setCurrentDirectory(new File("src/main/resources/"));
        files.setVisible(true);
        files.setBounds(0, 0, (int) size.getWidth(), 300);

        String[] tiposDefiltros = {"Filtro Preto e Branco", "Filtro Negativo", "Filtro Remover ruido", "Filtro Melhorar contraste", "Filtro Tons de cinza", "Filtro Detectar Bordas"};
        files.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos de Imagem png", "png"));
        files.addChoosableFileFilter(new FileNameExtensionFilter("Arquivos de Imagem jpg", "jpg"));

        filtros.setBounds(((int) size.getWidth() / 2) - 436, 300, 416, 20);
        filtros.setModel(new DefaultComboBoxModel<>(tiposDefiltros));

        botaoSalvar.setBounds(((int) size.getWidth() / 2) + 20, 300, 416, 20);
        botaoSalvar.setText("Exportar Imagem");
        botaoSalvar.setFont(new Font("Dialog", Font.BOLD, 16));

        adicionarComponentesParaView(imagePanel);
    }

    public void limparImagens() {
        imagemEntrada.setIcon(new ImageIcon("src/main/resources/no_image.jpg"));
        imagemSaida.setIcon(new ImageIcon("src/main/resources/no_image.jpg"));
    }


    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    public void observaArquivoSelecionado(ActionListener actionListener) {
        files.addActionListener(actionListener);
    }

    public void observaSalvarArquivo(ActionListener actionListener) {
        botaoSalvar.addActionListener(actionListener);
    }

    private void adicionarComponentesParaView(JPanel imagePanel) {
        imagePanel.add(imagemEntrada);
        imagePanel.add(imagemSaida);
        imagePanel.add(files);
        imagePanel.add(filtros);
        imagePanel.add(botaoSalvar);
        add(imagePanel);
        setVisible(true);
    }

}
