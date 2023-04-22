import controller.ImageController;
import ui.ImageView;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        ImageView imageView = new ImageView();
        ImageController controller = new ImageController(imageView);
        controller.initController();
    }
}
