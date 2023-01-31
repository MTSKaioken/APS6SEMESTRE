package controller;

import model.ImageModel;
import view.ImageView;

public class EqualizacaoImagem {
    public static void main(String[] args) {
        ImageView view = new ImageView();
//        ImageModel model = new ImageModel();
//        ImageController controller = new ImageController(view, model);
        ImageController controller = new ImageController(view);
        controller.initController();

        view.setVisible(true);
    }

}
