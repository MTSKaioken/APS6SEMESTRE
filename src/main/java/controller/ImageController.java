package controller;

import model.ImageModel;
import utils.*;
import view.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
//                BufferedImage imagemBinarizada = Binarizacao.binarizacaoImagem(img, 45);
                ManipulatedImage manipulatedImage = new ManipulatedImage(img);
                manipulatedImage.makeBlackAndWhite(45);
//                ManipulatedImage manipulatedImage = new ManipulatedImage(imagemBinarizada);
                manipulatedImage.zhangSuen();


                FingerPrintImage finger = new FingerPrintImage(manipulatedImage.image.getHeight(),
                        manipulatedImage.image.getWidth());

                FingerPrintImage fingerPrintImage = manipulatedImage.zhangsuen2(finger);

//                FingerPrintImage minucias = fingerPrintImage.invertir();
//                BufferedImage minucias1 = manipulatedImage.minucias(minucias);

                view.getImagemSaida().setIcon(new ImageIcon(manipulatedImage.image));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Somente .png, .jpg e .bmp");
        }

    }

}


