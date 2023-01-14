package controller;

import model.ImageModel;
import service.FiltrosService;
import utils.FingerPrintImage;
import utils.ManipulatedImage;
import view.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageController {

    private final ImageView view;
    private final ImageModel model;

    public ImageController(ImageView view, ImageModel model) {
        this.view = view;
        this.model = model;

    }

    public void initController() {
        view.getFiles().addActionListener(e -> converteCaminhoEmImagem(view.getFiles().getSelectedFile().getAbsolutePath()));

        view.getjComboBox().addActionListener(e -> bloqueiaThreshold(view.getjComboBox().getSelectedItem().toString()));

    }

    private void bloqueiaThreshold(String tipoFiltro) {
        System.out.println(tipoFiltro);
        if (tipoFiltro.equals("Preto e Branco")) {
            view.getjSpinner().setEnabled(true);
        } else {
            view.getjSpinner().setEnabled(false);
        }
    }

    public void converteCaminhoEmImagem(String path) {
        //logica pra processar imagem
        if (view.getFilter().accept(view.getFiles().getSelectedFile())) {

            System.out.println(path);
            ImageIcon entrada = new ImageIcon(path);
            entrada.setImage(entrada.getImage().getScaledInstance(416, 416, 100));
            view.getImagemEntrada().setIcon(entrada);


            try {
                BufferedImage img = ImageIO.read(new File(path));
                ManipulatedImage manipulatedImage = new ManipulatedImage(img);

                //imagem em tons de cinza


                //manipulatedImage.makeGrayscale();


                //inverte o preto e o branco da imagem caso a checkbox tiver sido selecionada
                if (view.getjCheckBox().isSelected()) {
                    manipulatedImage.makeNegative();
                }


                int threshold = Integer.parseInt(view.getjSpinner().getValue().toString());
                view.getjSpinner().setEnabled(false);
                // Binariza a imagem após colocala em escala de cinza


                // aplica o algoritmo de afinamento
//                manipulatedImage.zhangSuen();

                //refatorar esse método
                //manipulatedImage.minucias();

                String tipoFiltro = view.getjComboBox().getSelectedItem().toString();


                if (tipoFiltro.equals("Negativo")) {
                    manipulatedImage.makeGrayscale();
                    manipulatedImage.makeNegative();

                    ImageIcon saida = new ImageIcon(manipulatedImage.image);
                    saida.setImage(saida.getImage().getScaledInstance(416, 416, 100));
                    view.getImagemSaida().setIcon(saida);
                } else if (tipoFiltro.equals("Preto e Branco")) {
                    manipulatedImage.makeGrayscale();
                    // desativar o jspinner em todos os outros filtros
                    manipulatedImage.makeBlackAndWhite(threshold);

                    ImageIcon saida = new ImageIcon(manipulatedImage.image);
                    saida.setImage(saida.getImage().getScaledInstance(416, 416, 100));
                    view.getImagemSaida().setIcon(saida);
                } else if (tipoFiltro.equals("Passa Baixa por media")) {
                    BufferedImage passaBaixa = manipulatedImage.filtroPassaBaixaPeloFiltroMedio(manipulatedImage.image);

                    ImageIcon saida = new ImageIcon(passaBaixa);
                    saida.setImage(saida.getImage().getScaledInstance(416, 416, 100));
                    view.getImagemSaida().setIcon(saida);
                } else if (tipoFiltro.equals("Passa Alta")) {
                    BufferedImage passaAlta = manipulatedImage.novoFiltroPassaAlta(manipulatedImage.image);

                    ImageIcon saida = new ImageIcon(passaAlta);
                    saida.setImage(saida.getImage().getScaledInstance(416, 416, 100));
                    view.getImagemSaida().setIcon(saida);
                } else if (tipoFiltro.equals("Tons de cinza")) { // ok
                    manipulatedImage.makeGrayscale();

                    ImageIcon saida = new ImageIcon(manipulatedImage.image);
                    saida.setImage(saida.getImage().getScaledInstance(416, 416, 100));
                    view.getImagemSaida().setIcon(saida);
                } else if (tipoFiltro.equals("Azul")) {
                    BufferedImage filtroVerde = manipulatedImage.filtroVerde(manipulatedImage.image);

                    ImageIcon saida = new ImageIcon(filtroVerde);
                    saida.setImage(saida.getImage().getScaledInstance(416, 416, 100));
                    view.getImagemSaida().setIcon(saida);
                } else if (tipoFiltro.equals("Sobel")) { // ok

                    manipulatedImage.makeGrayscale();

                    //funciona melhor com imagens em escala de cinza
                    BufferedImage passaAltaSobel = FiltrosService.passaAltaSobel(manipulatedImage.image);
                    ImageIcon saida = new ImageIcon(passaAltaSobel);
                    saida.setImage(saida.getImage().getScaledInstance(416, 416, 100));
                    view.getImagemSaida().setIcon(saida);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um filtro");
                }
            } catch (Exception e) {
                view.getImagemEntrada().setIcon(null);
                view.getImagemSaida().setIcon(null);
                JOptionPane.showMessageDialog(null, "Falha ao carregar a imagem!");
                throw new RuntimeException(e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Somente .png e .jpg");
        }

    }

}



