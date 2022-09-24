package controller;

import model.ImageModel;
import view.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ImageController {

    private ImageView view;
    private ImageModel model;

    public ImageController(ImageView view, ImageModel model) {
        this.view = view;
        this.model = model;

    }

    public void initController(){
        view.getFiles().addActionListener(e -> processaImagem(view.getFiles().getSelectedFile().getAbsolutePath()));
    }

    public void processaImagem(String path) {
        //logica pra processar imagem
        view.getImagem().setIcon(new ImageIcon(path));
        System.out.println(path);
    }

    //openFiles


}


