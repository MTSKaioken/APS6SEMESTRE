package controller;

import service.FiltroService;
import ui.ImageView;
import utils.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageController {

    private ImageView view;

    public ImageController(ImageView view) {
        this.view = view;
    }

    public void initController() {
        view.observaFiltroSelecionado(e -> {
            bloqueiaThresholdComBaseNoFiltro();
        });

        view.observaArquivoSelecionado(e -> {
            BufferedImage imagemEntrada = montaImagemEntradaPartindoDoCaminho(view.getFiles()
                    .getSelectedFile()
                    .getAbsolutePath());
            aplicaFiltroSelecionado(imagemEntrada);
        });
    }


    public void bloqueiaThresholdComBaseNoFiltro() {
        if (view.getFiltros().getSelectedItem().toString().equals("Preto e Branco")) {
            view.getValorThreshold().setEnabled(true);
        } else {
            view.getValorThreshold().setEnabled(false);
        }
    }

    public BufferedImage montaImagemEntradaPartindoDoCaminho(String path) {
        BufferedImage entrada = null;

        if (view.getFilter().accept(view.getFiles().getSelectedFile())) {
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
     * Metodo que pega o texto selecionado e instancia de forma generica, chamando
     *
     * @param entrada
     */
    private void aplicaFiltroSelecionado(BufferedImage entrada) {

        String tipoFiltro = view.getFiltros().getSelectedItem().toString();
        String nomeDaClasseQueImplementaFiltro = StringUtils.toPascalCase(tipoFiltro);
//        int threshold = Integer.parseInt(view.getValorThreshold().getValue().toString());

        if (view.getInverterPretoBranco().isSelected()) {
//            entrada = filtroServiceImpl.tornaImagemNegativa(entrada);
        }

        String fqn = "service.impl." + nomeDaClasseQueImplementaFiltro + "ServiceImpl";

        FiltroService instancia = null;

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


    private void atualizaImagemDeEntradaOuDeSaida(String entradaOuSaida, BufferedImage imagem) {
        ImageIcon labelImagem = null;

        try {
            labelImagem = new ImageIcon(imagem);
            labelImagem.setImage(labelImagem.getImage().getScaledInstance(416, 416, Image.SCALE_DEFAULT));

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



