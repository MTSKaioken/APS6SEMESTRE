package controller;

import model.ImageModel;
import service.FiltroService;
//import utils.ManipulatedImage;
import view.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageController {

    private final ImageView view;
//    private final ImageModel model;

    public ImageController(ImageView view, ImageModel model) {
        this.view = view;
//        this.model = model;

    }

    public ImageController(ImageView view) {
        this.view = view;
//        this.model = model;

    }

    public void initController() {

        view.getFiles().addActionListener(e -> converteCaminhoEmImagem(view.getFiles().getSelectedFile().getAbsolutePath()));

        view.getjComboBox().addActionListener(e -> bloqueiaThreshold(view.getjComboBox().getSelectedItem().toString()));

    }

    private void bloqueiaThreshold(String tipoFiltro) {
        if (tipoFiltro.equals("Preto e Branco")) {
            view.getjSpinner().setEnabled(true);
        } else {
            view.getjSpinner().setEnabled(false);
        }
    }

    public void converteCaminhoEmImagem(String path) {
        //logica pra processar imagem
        try {
            if (view.getFilter().accept(view.getFiles().getSelectedFile())) {
                try {
                    BufferedImage entrada = ImageIO.read(new File(path));
                    BufferedImage img = ImageIO.read(new File(path));
                    atualizaImagemDeEntradaOuDeSaida("entrada", entrada);
                    FiltroService filtro = new FiltroService();

                    //inverte o preto e o branco da imagem caso a checkbox tiver sido selecionada
                    if (view.getjCheckBox().isSelected()) {
                        img = filtro.tornaImagemNegativa(img);
                    }


                    int threshold = Integer.parseInt(view.getjSpinner().getValue().toString());
                    String tipoFiltro = view.getjComboBox().getSelectedItem().toString();


                    if (tipoFiltro.equals("Negativo")) {
                        BufferedImage imagemSaida = filtro.tornaImagemNegativa(img);
                        atualizaImagemDeEntradaOuDeSaida("saida", imagemSaida);
                    } else if (tipoFiltro.equals("Preto e Branco")) {
                        BufferedImage imagemSaida = filtro.tornaImagemPretaBranca(img, threshold);
                        atualizaImagemDeEntradaOuDeSaida("saida", imagemSaida);
                    } else if (tipoFiltro.equals("Remover ruido")) {
                        BufferedImage imagemSaida = filtro.filtroPassaBaixaPeloFiltroMedianaRemovedorDeRuido(img);
                        atualizaImagemDeEntradaOuDeSaida("saida", imagemSaida);
                    } else if (tipoFiltro.equals("Melhorar contraste")) {
                        BufferedImage imagemSaida = filtro.equalizarHistogramaParaMelhorarContraste(img);
                        atualizaImagemDeEntradaOuDeSaida("saida", imagemSaida);
                    } else if (tipoFiltro.equals("Tons de cinza")) { // ok
                        BufferedImage imagemSaida = filtro.converteParaTonsDeCinza(img);
                        atualizaImagemDeEntradaOuDeSaida("saida", imagemSaida);
                    } else if (tipoFiltro.equals("Detectar Bordas")) { // ok
                        //funciona melhor com imagens em escala de cinza: ok
                        BufferedImage imagemSaida = filtro.passaAltaSobel(img);
                        atualizaImagemDeEntradaOuDeSaida("saida", imagemSaida);
                    } else {
                        JOptionPane.showMessageDialog(null, "Selecione um filtro");
                    }
                } catch (Exception e) {
                    view.getImagemEntrada().setIcon(null);
                    view.getImagemSaida().setIcon(null);
                    JOptionPane.showMessageDialog(null, "Falha ao carregar a imagem de entrada!");
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Somente .png e .jpg");
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Falha ao carregar a imagem de entrada!");
            e.printStackTrace();
        }
    }

    private void atualizaImagemDeEntradaOuDeSaida(String entradaOuSaida, BufferedImage imagem) {
        ImageIcon labelImagem = new ImageIcon(imagem);
        labelImagem.setImage(labelImagem.getImage().getScaledInstance(416, 416, 100));

        if (entradaOuSaida.equals("entrada")) {
            view.getImagemEntrada().setIcon(labelImagem);
        } else if (entradaOuSaida.equals("saida")) {
            view.getImagemSaida().setIcon(labelImagem);
        }
    }

}



