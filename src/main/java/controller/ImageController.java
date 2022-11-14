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

    public void converteCaminhoEmImagem(String path) {
        //logica pra processar imagem
        if (view.getFilter().accept(view.getFiles().getSelectedFile())) {

            System.out.println(path);
            view.getImagemEntrada().setIcon(new ImageIcon(path));

            try {
                BufferedImage img = ImageIO.read(new File(path));
                ManipulatedImage manipulatedImage = new ManipulatedImage(img);

                //imagem em tons de cinza
                manipulatedImage.makeGrayscale();


                //inverte o preto e o branco da imagem caso a checkbox tiver sido selecionada
                if (view.getjCheckBox().isSelected()) {
                    manipulatedImage.makeNegative();
                }


                int threshold = Integer.parseInt(view.getjSpinner().getValue().toString());
                // Binariza a imagem após colocala em escala de cinza
                manipulatedImage.makeBlackAndWhite(threshold);

                // aplica o algoritmo de afinamento
                manipulatedImage.zhangSuen();

                //refatorar esse método
                //manipulatedImage.minucias();

                view.getImagemSaida().setIcon(new ImageIcon(manipulatedImage.image));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Somente .png, .jpg e .bmp");
        }

    }

}



