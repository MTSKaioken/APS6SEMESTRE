package utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Binarizacao {

    public static BufferedImage binarizacaoImagem(BufferedImage image, int t) {
        int BLACK = Color.BLACK.getRGB();
        int WHITE = Color.WHITE.getRGB();

        BufferedImage output = new BufferedImage(image.getWidth(),
                image.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Percorre a imagem definindo na saída o pixel como branco se o valor
        // na entrada for menor que o threshold, ou como preto se for maior.
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelAtual = new Color(image.getRGB(x, y));
                output.setRGB(x, y, pixelAtual.getRed() < t ? BLACK : WHITE);
            }

        return output;
    }
}
