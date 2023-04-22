package model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Classe para representar uma imagem em Java.
 */
public class ImageModel {
    private BufferedImage imagem;
    private int[][] matrizPixels;

    /**
     * Cria um objeto ImageModel a partir de uma matriz de pixels.
     *
     * @param matrizPixels a matriz de pixels que representa a imagem
     * @throws IllegalArgumentException se a matriz de pixels for nula ou vazia
     */
    public ImageModel(int[][] matrizPixels){
        if (matrizPixels == null || matrizPixels.length == 0 || matrizPixels[0].length == 0) {
            throw new IllegalArgumentException("A matriz de pixels não pode ser nula ou vazia");
        }

        this.matrizPixels = matrizPixels;
        criarImagemPorMatriz();
    }

    /**
     * Cria um objeto ImageModel a partir de uma imagem.
     *
     * @param bufferedImage a imagem que será usada para criar o objeto ImageModel
     * @throws IllegalArgumentException se a imagem for nula
     */
    public ImageModel(BufferedImage bufferedImage){
        if (bufferedImage == null) {
            throw new IllegalArgumentException("A imagem não pode ser nula");
        }
        this.imagem = bufferedImage;
        criarMatrizPorImagem();
    }

    /**
     * Cria uma imagem a partir da matriz de pixels.
     */
    public void criarImagemPorMatriz() {

        int largura = this.matrizPixels.length;
        int altura = this.matrizPixels[0].length;

        BufferedImage imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                int valorPixel = this.matrizPixels[x][y];
                Color corPixel = new Color(valorPixel, valorPixel, valorPixel);
                imagem.setRGB(x, y, corPixel.getRGB());
            }
        }
        this.imagem = imagem;
    }

    /**
     * Cria uma matriz de pixels a partir da imagem BufferedImage.
     */
    public void criarMatrizPorImagem() {
        int altura = imagem.getHeight();
        int largura = imagem.getWidth();

        int[][] matrizPixels = new int[largura][altura];

        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                int valorRGB = imagem.getRGB(x, y);
                int valorCinza = (int) ((valorRGB & 0xFF) * 0.299 + ((valorRGB >> 8) & 0xFF) * 0.587 + ((valorRGB >> 16) & 0xFF) * 0.114);
                matrizPixels[x][y] = valorCinza;
            }
        }
        this.matrizPixels = matrizPixels;
    }
}
