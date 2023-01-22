package service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FiltrosService {

    public static BufferedImage passaAltaSobel(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Criar matrizes de pesos para o filtro de Sobel
        int[][] xKernel = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] yKernel = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        // Aplicar o filtro
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                int xGradient = 0, yGradient = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int rgb = image.getRGB(x + i, y + j);
                        int gray = (rgb >> 16) & 0xff;
                        xGradient += gray * xKernel[i + 1][j + 1];
                        yGradient += gray * yKernel[i + 1][j + 1];
                    }
                }
                int gradient = (int) Math.sqrt(xGradient * xGradient + yGradient * yGradient);
                gradient = Math.min(255, gradient);
                output.setRGB(x, y, (gradient << 16) | (gradient << 8) | gradient);
            }
        }

        return output;
    }

    public static BufferedImage equalizacaoHistograma(BufferedImage image) {
        double contrastFactor = 1.5;

        // Encontra os valores mínimo e máximo da imagem
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);
                int red = (color & 0x00ff0000) >> 16;
                int green = (color & 0x0000ff00) >> 8;
                int blue = color & 0x000000ff;
                int brightness = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);
                min = Math.min(min, brightness);
                max = Math.max(max, brightness);
            }
        }

        // Aplica a operação de "stretch" para melhorar o contraste
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);
                int red = (color & 0x00ff0000) >> 16;
                int green = (color & 0x0000ff00) >> 8;
                int blue = color & 0x000000ff;
                int brightness = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);
                float contrast = (255f / (max - min)) * (brightness - min);
                red = (int) Math.max(0, Math.min(contrast, 255));
                green = (int) Math.max(0, Math.min(contrast, 255));
                blue = (int) Math.max(0, Math.min(contrast, 255));
                int newColor = (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newColor);
            }
        }
        try {
            ImageIO.write(image, "jpg", new File("contrast_enhanced.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
