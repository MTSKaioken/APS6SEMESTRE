package controller;

import model.ImageModel;
import view.ImageView;

import javax.swing.*;

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
        if(view.getFilter().accept(view.getFiles().getSelectedFile())) {
            view.getImagemEntrada().setIcon(new ImageIcon(path));

            view.getImagemSaida().setIcon(new ImageIcon(path));
            System.out.println(path);
        } else {
            JOptionPane.showMessageDialog(null, "Somente .png e .jpg");
        }

    }

    //openFiles


}


