package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Parses {

    public static BufferedImage matrizParaImagem(int[][] matriz) {
        int largura = matriz.length, altura = matriz[0].length;

        BufferedImage outputfile = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        for (int h = 0; h < largura; h++) {
            for (int w = 0; w < altura; w++) {
                outputfile.setRGB(h, w, matriz[h][w]);
            }
        }
        return outputfile;
    }

    public static int[][] imagemParaMatriz(BufferedImage image){

        int w = image.getWidth();
        int h = image.getHeight();

        int[][] matriz = new int[w][h];
        System.out.println("Matriz da entrada: ");
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                matriz[i][j] = image.getRGB(i, j);
                System.out.print(matriz[i][j]);
            }
        }
        System.out.println();

        return matriz;
    }
}
