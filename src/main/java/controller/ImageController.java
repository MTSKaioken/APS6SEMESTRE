package controller;

import service.FiltroService;
import ui.ImageView;
import utils.StringUtils;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Classe controlladora das Imagens, onde serão chamadas as implementações dos filtros.
 */
public class ImageController {

    private ImageView view;

    /**
     * Utilização de composição (POO), para termos acesso sobre a view.
     *
     * @param view ImageView - Classe onde se encontra o UI do software.
     */
    public ImageController(ImageView view) {
        this.view = view;
    }

    /**
     * Método no qual se encontram os ActionListener da view, que por fim quando chamados executam suas respectivas
     * rotinas
     */
    public void initController() {

        view.observaArquivoSelecionado(e -> {
            BufferedImage imagemEntrada = montaImagemEntradaPartindoDoCaminho(view.getFiles()
                    .getSelectedFile()
                    .getAbsolutePath());
            aplicaFiltroSelecionado(imagemEntrada);
        });

        view.observaSalvarArquivo(e -> salvarImagemSaida() );
}

    private void salvarImagemSaida() {
        view.getExploradorSaida().showSaveDialog(view);

        File file = view.getExploradorSaida().getSelectedFile();

        String extensao = Utils.getExtension(file);
        Icon icon = view.getImagemSaida().getIcon();

        BufferedImage imagem = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = imagem.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();

        try {
            ImageIO.write(imagem, extensao, file);
        } catch (IOException e) {
            view.exibirMensagem("Erro ao salvar.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Converte caminho da imagem em um BufferedImage
     *
     * @param path String - Caminho absoluto da imagem.
     * @return BufferedImage entrada - Imagem a partir do caminho.
     */

    public BufferedImage montaImagemEntradaPartindoDoCaminho(String path) {
        BufferedImage entrada = null;

        if (view.getFiles().getFileFilter().accept(view.getFiles().getSelectedFile())) {
            try {
                File file = new File(path);
                entrada = ImageIO.read(file);
            } catch (IOException | ClassCastException e) {
                view.limparImagens();
                JOptionPane.showMessageDialog(null, "Falha ao carregar a imagem de entrada!");
                throw new RuntimeException(e);
            }
            atualizaImagemDeEntradaOuDeSaida("entrada", entrada);
        }
        return entrada;
    }


    /**
     * Metodo responsável por chamar a implentação do filtro selecionado.
     *
     * @param entrada BufferedImage - Imagem a qual terá o filtro aplicado
     */
    private void aplicaFiltroSelecionado(BufferedImage entrada) {

        String tipoFiltro = view.getFiltros().getSelectedItem().toString();
        String nomeDaClasseQueImplementaFiltro = StringUtils.toPascalCase(tipoFiltro);

        String fqn = "service.impl." + nomeDaClasseQueImplementaFiltro + "ServiceImpl";

        FiltroService instancia;

        try {
            Class<?> classeGenerica = Class.forName(fqn);
            instancia = (FiltroService) classeGenerica.newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            view.exibirMensagem("Selecione um filtro valido!");
            throw new RuntimeException(e);
        }

        BufferedImage imagemSaida = instancia.aplicarFiltro(entrada);
        atualizaImagemDeEntradaOuDeSaida("saida", imagemSaida);
    }

    /**
     * Metodo que atualiza imagem de entrada ou saida, extraido para evitar repetição de código
     *
     * @param entradaOuSaida String - tipo da imagem que será atualizada
     * @param imagem         BufferedImage - imagem que atualizara o campo.
     */
    private void atualizaImagemDeEntradaOuDeSaida(String entradaOuSaida, BufferedImage imagem) {
        ImageIcon labelImagem = new ImageIcon(imagem);

        try {
            labelImagem.setImage(labelImagem.getImage().getScaledInstance(416, 416, Image.SCALE_REPLICATE));


            switch (entradaOuSaida) {
                case "entrada":
                    view.getImagemEntrada().setIcon(labelImagem);
                case "saida":
                    view.getImagemSaida().setIcon(labelImagem);
            }

        } catch (IllegalArgumentException | NullPointerException e) {
            view.limparImagens();
            JOptionPane.showMessageDialog(null, "Falha ao carregar a imagem!");
            throw new RuntimeException(e);
        }
    }

}



