package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManipulatedImage {
    public BufferedImage image;
    public Color[][] matrix;
    public String path;
    public boolean isBW;

    //Contrutor, recebe uma Buffered image
    public ManipulatedImage(BufferedImage image) throws Exception {
        this.image = image;
        path = System.getProperty("user.home") + "/Desktop";
        createMatrixByImage();
        isBW = false;
    }


    //Transforma a imagem em escala de cinza
    public void makeGrayscale() throws Exception {
        Color helper;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                int r = matrix[x][y].getRed();
                int g = matrix[x][y].getGreen();
                int b = matrix[x][y].getBlue();

                int media = (r + g + b) / 3;

                helper = new Color(media, media, media);
                matrix[x][y] = helper;
            }
        }

        createImageByMatrix(matrix);
        createMatrixByImage();
    }

    //Transforma a imagem em escala de cinza
    public void makeBlackAndWhite(int threshold) throws Exception {
        makeGrayscale();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                int current = matrix[x][y].getRed();

                if (current < threshold)
                    matrix[x][y] = new Color(0, 0, 0);
                else
                    matrix[x][y] = new Color(255, 255, 255);
            }
        }

        createImageByMatrix(matrix);
        createMatrixByImage();
        isBW = true;
    }

    //Transforma para o negativo da imagem
    public void makeNegative() throws Exception {
        Color helper;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                int r = matrix[x][y].getRed();
                int g = matrix[x][y].getGreen();
                int b = matrix[x][y].getBlue();

                helper = new Color(255 - r, 255 - g, 255 - b);
                matrix[x][y] = helper;
            }
        }

        createImageByMatrix(matrix);
        createMatrixByImage();
    }


    public BufferedImage equalizarHistogramaParaMelhorarContraste(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage equalized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[] histogram = new int[256];
        int[] cdf = new int[256];
        int[] equalizedValue = new int[256];
        int totalPixels = width * height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int color = image.getRGB(x, y);
                int red = (color >> 16) & 0xff;
                histogram[red]++;
            }
        }
        cdf[0] = histogram[0];
        for (int i = 1; i < 256; i++) {
            cdf[i] = cdf[i-1] + histogram[i];
        }
        for (int i = 0; i < 256; i++) {
            equalizedValue[i] = (int)(((double)cdf[i] / totalPixels) * 255);
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int color = image.getRGB(x, y);
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;
                red = equalizedValue[red];
                green = equalizedValue[green];
                blue = equalizedValue[blue];
                int newColor = (red << 16) | (green << 8) | blue;
                equalized.setRGB(x, y, newColor);
            }
        }
        return equalized;
    }

    public BufferedImage filtroPassaBaixaPeloFiltroMedianaRemovedorDeRuido(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Cria uma imagem para armazenar a imagem processada
        BufferedImage filteredImage = new BufferedImage(width, height, originalImage.getType());

        // Define o tamanho da janela deslizante (neste caso, 3x3)
        int windowSize = 3;
        int halfWindow = windowSize / 2;

        // Percorre cada pixel da imagem
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                // Inicializa um array para armazenar os valores de cor dos pixels na janela deslizante
                int[] redValues = new int[windowSize*windowSize];
                int[] greenValues = new int[windowSize*windowSize];
                int[] blueValues = new int[windowSize*windowSize];
                int pixels = 0;
                for (int i = -halfWindow; i <= halfWindow; i++) {
                    for (int j = -halfWindow; j <= halfWindow; j++) {
                        // Verifica se o pixel está dentro dos limites da imagem
                        if (x + i >= 0 && x + i < width && y + j >= 0 && y + j < height) {
                            // Adiciona os valores de cor do pixel ao array
                            int color = originalImage.getRGB(x + i, y + j);
                            redValues[pixels] = (color >> 16) & 0xff;
                            greenValues[pixels] = (color >> 8) & 0xff;
                            blueValues[pixels] = color & 0xff;
                            pixels++;
                        }
                    }
                }

                // Ordena os arrays de valores de cor
                Arrays.sort(redValues, 0, pixels);
                Arrays.sort(greenValues, 0, pixels);
                Arrays.sort(blueValues, 0, pixels);

                // Obtém a mediana dos valores de cor na janela deslizante
                int redMedian = redValues[pixels / 2];
                int greenMedian = greenValues[pixels / 2];
                int blueMedian = blueValues[pixels / 2];

                // Define os valores de cor medianos para o pixel atual
                int newColor = (redMedian << 16) | (greenMedian << 8) | blueMedian;
                filteredImage.setRGB(x, y, newColor);
            }
        }

        return filteredImage;
    }


    //Cria a matrix de pixel a partir da imagem
    private void createMatrixByImage() throws Exception {
        matrix = new Color[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                matrix[x][y] = new Color(image.getRGB(x, y), true);
            }
        }
    }

    //Cria a imagem a partir da matrix de pixel
    private void createImageByMatrix(Color[][] edited) {
        BufferedImage newImage = new BufferedImage(edited.length, edited[0].length,
                BufferedImage.TYPE_INT_ARGB);


        for (int x = 0; x < edited.length; x++) {
            for (int y = 0; y < edited[0].length; y++) {
                if (edited[x][y] != null)
                    newImage.setRGB(x, y, edited[x][y].getRGB());
            }
        }
        this.image = newImage;
    }
}
