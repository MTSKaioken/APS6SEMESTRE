import controller.ImageController;
import model.ImageModel;
import view.ImageView;

public class EqualizacaoImagem {
    public static void main(String[] args) {
        ImageView view = new ImageView();
        ImageModel model = new ImageModel();
        ImageController controller = new ImageController(view, model);
        controller.initController();

        view.setVisible(true);
    }

}
