package service;

import java.awt.image.BufferedImage;

public class FiltrosService {

    public static BufferedImage passaAltaSobel(BufferedImage image){
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
}
