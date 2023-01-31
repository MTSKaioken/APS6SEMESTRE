//package utils;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class ManipulatedImage {
//    public BufferedImage image;
//    public Color[][] matrix;
//    public String path;
//    public boolean isBW;
//
//    //Contrutor, recebe uma Buffered image
//    public ManipulatedImage(BufferedImage image) throws Exception {
//        this.image = image;
//        path = System.getProperty("user.home") + "/Desktop";
//        createMatrixByImage();
//        isBW = false;
//    }
//
//
//    //Transforma a imagem em escala de cinza
//    public void makeGrayscale() throws Exception {
//        Color helper;
//
//        for (int x = 0; x < image.getWidth(); x++) {
//            for (int y = 0; y < matrix[0].length; y++) {
//                int r = matrix[x][y].getRed();
//                int g = matrix[x][y].getGreen();
//                int b = matrix[x][y].getBlue();
//
//                int media = (r + g + b) / 3;
//
//                helper = new Color(media, media, media);
//                matrix[x][y] = helper;
//            }
//        }
//
//        createImageByMatrix(matrix);
//        createMatrixByImage();
//    }
//
//    //Transforma a imagem em escala de cinza
//    public void makeBlackAndWhite(int threshold) throws Exception {
//        makeGrayscale();
//
//        for (int x = 0; x < image.getWidth(); x++) {
//            for (int y = 0; y < matrix[0].length; y++) {
//                int current = matrix[x][y].getRed();
//
//                if (current < threshold)
//                    matrix[x][y] = new Color(0, 0, 0);
//                else
//                    matrix[x][y] = new Color(255, 255, 255);
//            }
//        }
//
//        createImageByMatrix(matrix);
//        createMatrixByImage();
//        isBW = true;
//    }
//
//    //Transforma para o negativo da imagem
//    public void makeNegative() throws Exception {
//        Color helper;
//
//        for (int x = 0; x < image.getWidth(); x++) {
//            for (int y = 0; y < matrix[0].length; y++) {
//                int r = matrix[x][y].getRed();
//                int g = matrix[x][y].getGreen();
//                int b = matrix[x][y].getBlue();
//
//                helper = new Color(255 - r, 255 - g, 255 - b);
//                matrix[x][y] = helper;
//            }
//        }
//
//        createImageByMatrix(matrix);
//        createMatrixByImage();
//    }
//
//
//    //Cria a matrix de pixel a partir da imagem
//    private void createMatrixByImage() throws Exception {
//        matrix = new Color[image.getWidth()][image.getHeight()];
//
//        for (int x = 0; x < image.getWidth(); x++) {
//            for (int y = 0; y < image.getHeight(); y++) {
//                matrix[x][y] = new Color(image.getRGB(x, y), true);
//            }
//        }
//    }
//
//    //Cria a imagem a partir da matrix de pixel
//    private void createImageByMatrix(Color[][] edited) {
//        BufferedImage newImage = new BufferedImage(edited.length, edited[0].length,
//                BufferedImage.TYPE_INT_ARGB);
//
//
//        for (int x = 0; x < edited.length; x++) {
//            for (int y = 0; y < edited[0].length; y++) {
//                if (edited[x][y] != null)
//                    newImage.setRGB(x, y, edited[x][y].getRGB());
//            }
//        }
//        this.image = newImage;
//    }
//}
