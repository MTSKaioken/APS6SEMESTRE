import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TestFiles extends JFrame {

    public TestFiles() {
        super("Processamento de Imagem");

        setLayout(new FlowLayout());

        // set up a file picker component
        JFilePicker filePicker = new JFilePicker();
        filePicker.setMode(JFilePicker.MODE_SAVE);
        filePicker.addFileTypeFilter(".jpg", "JPG Images");
        filePicker.addFileTypeFilter(".jpeg", "JPEG Images");
        filePicker.addFileTypeFilter(".png", "PNG Images");

        // access JFileChooser class directly
        JFileChooser fileChooser = filePicker.getFileChooser();
        fileChooser.setCurrentDirectory(new File("D:/"));

        // add the component to the frame
        add(filePicker);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 100);
        setLocationRelativeTo(null);    // center on screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TestFiles().setVisible(true);
            }
        });
    }
}
