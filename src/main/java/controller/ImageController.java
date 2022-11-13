package controller;

import model.ImageModel;
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

    }

    private void setaValoresCasoEstejaForaDoIntervalo() {
        System.out.println("valor alterado");
    }

    public void converteCaminhoEmImagem(String path) {
        //logica pra processar imagem
        if (view.getFilter().accept(view.getFiles().getSelectedFile())) {

//            try {
//                BufferedImage img = ImageIO.read(new File(path, "imagemEntrada.jpg"));//carregando a imagem que será equalizada
//                BufferedImage imagemSaida =

//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            System.out.println(path);
            view.getImagemEntrada().setIcon(new ImageIcon(path));

            try {
                BufferedImage img = ImageIO.read(new File(path));
                ManipulatedImage manipulatedImage = new ManipulatedImage(img);

                //imagem em tons de cinza
                manipulatedImage.makeGrayscale();

                if(view.getjCheckBox().isSelected()) {
                    manipulatedImage.makeNegative();
                }

                // Binariza a imagem após colocala em escala de cinza
                int threshold = Integer.parseInt(view.getjSpinner().getValue().toString());
                manipulatedImage.makeBlackAndWhite(threshold);



                if(view.getjSpinner().getValue().equals(0)) {
                    System.out.println("valor igual a zero");
                }

//                manipulatedImage.zhangSuen();

                view.getImagemSaida().setIcon(new ImageIcon(manipulatedImage.image));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Somente .png, .jpg e .bmp");
        }

    }

}



