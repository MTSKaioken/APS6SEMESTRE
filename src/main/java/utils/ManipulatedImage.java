package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManipulatedImage {
    public BufferedImage image;
    public Color[][] matrix;
    public String path;
    public boolean isBW;

    //Contrutor, recebe uma Buffered image
    public ManipulatedImage(BufferedImage image) {
        this.image = image;
        path = System.getProperty("user.home") + "/Desktop";
        createMatrixByImage();
        isBW = false;
    }

    public BufferedImage RGBtoMatrizGrises(BufferedImage imagenRGB){
        FingerPrintImage matrizGrises=new FingerPrintImage(imagenRGB.getHeight(), imagenRGB.getWidth());
        for(int x=0; x<imagenRGB.getWidth();x++){
            for (int y = 0; y < imagenRGB.getHeight(); ++y){
                int rgb = imagenRGB.getRGB(x, y);
                char r = (char)((rgb >> 16) & 0xFF);
                char g = (char)((rgb >> 8) & 0xFF);
                char b = (char)((rgb & 0xFF));
                char nivelGris = (char)((r + g + b) / 3);
                matrizGrises.setPixel(x, y, nivelGris);
            }
        }
        FingerPrintImage clon = matrizGrises.copyF();
        BufferedImage imagenMinutias = clon.copy();
        return imagenMinutias;
    }

    // refatorar
    public BufferedImage minucias(FingerPrintImage imagenentrada) {
        FingerPrintImage clon = imagenentrada.copyF();
        clon = clon.invertir();
        BufferedImage imagenMinutias = clon.copy();
        Graphics2D g2d = imagenMinutias.createGraphics();
        g2d.setColor(Color.GREEN);
        for (int i = 0; i < imagenentrada.getWidth() - 1; i++) {
            for (int j = 0; j < imagenentrada.getHeight() - 1; j++) {
                if (imagenentrada.getPixel(i, j) == 1) {
                    if (imagenentrada.detectarMinucias(i, j) == true) {
                        g2d.drawRect(i - 10, j - 10, 20, 20);
                    }
                }

            }
        }
        g2d.dispose();
        return imagenMinutias;
    }


    //Transforma a imagem em escala de cinza
    public void makeGrayscale() {
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
    public void makeBlackAndWhite(int threshold) {
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

    //aplica o afinamento de zhangSuen
    public void zhangSuen() {
        int connected, neighbor;
        Color bk = new Color(0, 0, 0);
        Color wh = new Color(255, 255, 255);

        List<Point> pointsToChange = new ArrayList<>();

        boolean hasChange = true;

        while (hasChange) {

            hasChange = false;

            //Primeira Iteração
            for (int x = 0; x < matrix.length; x++) {
                for (int y = 0; y < matrix[x].length; y++) {

                    try {
                        connected = numberOfConnections(x, y);
                        neighbor = blackNeighborns(x, y);

                        if ((matrix[x][y].equals(bk))
                                && (neighbor >= 2)
                                && (neighbor < 7)
                                && (connected == 1)

                                && (condition(matrix[x][y - 1].equals(wh)
                                , matrix[x + 1][y].equals(wh)
                                , matrix[x][y + 1].equals(wh)))

                                && (condition(matrix[x + 1][y].equals(wh)
                                , matrix[x][y + 1].equals(wh)
                                , matrix[x - 1][y].equals(wh)))) {

                            pointsToChange.add(new Point(x, y));
                            hasChange = true;
                        }
                    } catch (Exception ignored) {
                    }
                }
            }

            for (Point point : pointsToChange)
                matrix[point.getX()][point.getY()] = wh;

            pointsToChange.clear();

            //Segunda Iteração
            for (int x = 0; x < matrix.length; x++) {
                for (int y = 0; y < matrix[x].length; y++) {

                    try {
                        connected = numberOfConnections(x, y);
                        neighbor = blackNeighborns(x, y);

                        if ((matrix[x][y].equals(bk))
                                && (neighbor >= 2)
                                && (neighbor < 7)
                                && (connected == 1)

                                && (condition(matrix[x][y - 1].equals(wh)
                                , matrix[x + 1][y].equals(wh)
                                , matrix[x - 1][y].equals(wh)))

                                && (condition(matrix[x][y - 1].equals(wh)
                                , matrix[x][y + 1].equals(wh)
                                , matrix[x - 1][y].equals(wh)))) {

                            pointsToChange.add(new Point(x, y));
                            hasChange = true;
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
            for (Point point : pointsToChange)
                matrix[point.getX()][point.getY()] = wh;

            pointsToChange.clear();
        }

        createImageByMatrix(matrix);
        createMatrixByImage();
        System.out.println("Applied");

    }

    //descobre a quantos pixels pretos
    // um pixel está conectado
    private int numberOfConnections(int x, int y) {
        int count = 0;
        Color bk = new Color(0, 0, 0);
        Color wh = new Color(255, 255, 255);

        //p2 p3
        if (matrix[x][y - 1].equals(wh) && matrix[x + 1][y - 1].equals(bk))
            count++;

        //p3 p4
        if (matrix[x + 1][y - 1].equals(wh) && matrix[x + 1][y].equals(bk))
            count++;

        //p4 p5
        if (matrix[x + 1][y].equals(wh) && matrix[x + 1][y + 1].equals(bk))
            count++;

        //p5 p6
        if (matrix[x + 1][y + 1].equals(wh) && matrix[x][y + 1].equals(bk))
            count++;

        //p6 p7
        if (matrix[x][y + 1].equals(wh) && matrix[x - 1][y + 1].equals(bk))
            count++;

        //p7 p8
        if (matrix[x - 1][y + 1].equals(wh) && matrix[x - 1][y].equals(bk))
            count++;

        //p8 p9
        if (matrix[x - 1][y].equals(wh) && matrix[x - 1][y - 1].equals(bk))
            count++;

        //p9 p2
        if (matrix[x - 1][y - 1].equals(wh) && matrix[x][y - 1].equals(bk))
            count++;

        return count;
    }

    //testa se as condições dos tres pixels vizinhos são verdadeiras
    private boolean condition(boolean c1, boolean c2, boolean c3) {
        int a, b, c;

        a = (c1) ? 0 : 1;
        b = (c2) ? 0 : 1;
        c = (c3) ? 0 : 1;

        return (a * b * c) == 0;

    }

    //descobre os pixels pretos vizinhos de um pixel
    private int blackNeighborns(int x, int y) {
        return (255 - matrix[x][y - 1].getBlue()
                + 255 - matrix[x + 1][y - 1].getBlue()
                + 255 - matrix[x + 1][y].getBlue()
                + 255 - matrix[x + 1][y + 1].getBlue()
                + 255 - matrix[x][y + 1].getBlue()
                + 255 - matrix[x - 1][y + 1].getBlue()
                + 255 - matrix[x - 1][y].getBlue()
                + 255 - matrix[x - 1][y - 1].getBlue()) / 255;
    }

    //Transforma para o negativo da imagem
    public void makeNegative() {
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

    public BufferedImage filtroPassaBaixa(BufferedImage img){
        int largura, altura, a, r, g, b;
        int pixel, novoPixel, elemFiltro, xp, yp;

        double novoA, novoR, novoG, novoB;
        int somaA, somaR, somaG, somaB;

        int filtro[][] ={{ 1, 1, 1},
                         { 1, 2, 1},
                         { 1, 1, 1}};

        BufferedImage novaImg = null;
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();

            novaImg = new BufferedImage(largura-2, altura-2, BufferedImage.TYPE_INT_ARGB);
            for(int y = 1 ; y < altura-1 ; y++){
                for(int x = 1 ; x < largura-1 ; x++){
                    somaA = 0;   somaR = 0;   somaG = 0;   somaB = 0;

                    for(int i = 0 ; i < 3 ; i++){
                        for(int j = 0 ; j < 3 ; j++){
                            elemFiltro = filtro[i][j];
                            xp = x-(j-1);   yp = y-(i-1);

                            pixel = img.getRGB(xp,yp);

                            a = (pixel>>24) & 0xff;
                            somaA = somaA + (a * elemFiltro);
                            r = (pixel>>16) & 0xff;
                            somaR = somaR + (r * elemFiltro);
                            g = (pixel>>8) & 0xff;
                            somaG = somaG + (g * elemFiltro);
                            b = pixel & 0xff;
                            somaB = somaB + (b * elemFiltro);

                        }
                    }
                    novoA = somaA / 10;  novoR = somaR / 10;  novoG = somaG / 10;  novoB = somaB / 10;

                    novoPixel = ((int)novoA<<24) | ((int)novoR<<16) | ((int)novoG<<8) | (int)novoB;
                    novaImg.setRGB(x-1, y-1, novoPixel);
                }
            }
        }
        return novaImg;
    }

    public BufferedImage filtroPassaAlta(BufferedImage img){
        int largura, altura;
        int a, r, g, b, pixel, novoPixel, elemFiltro, xp, yp;
        int somaA, somaR, somaG, somaB;

        int filtro[][] = {{ 0, -1, 0},
                { -1, 4, -1},
                { 0, -1, 0}};

        BufferedImage novaImg = null;
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();

            novaImg = new BufferedImage(largura-2, altura-2, BufferedImage.TYPE_INT_ARGB);
            for(int y = 1 ; y < altura-1 ; y++){
                for(int x = 1 ; x < largura-1 ; x++){
                    somaA = 0;   somaR = 0;   somaG = 0;   somaB = 0;    a = 0;
                    for(int i = 0 ; i < 3 ; i++){
                        for(int j = 0 ; j < 3 ; j++){
                            elemFiltro = filtro[i][j];
                            xp = x-(j-1);   yp = y-(i-1);

                            pixel = img.getRGB(xp,yp);

                            a = (pixel>>24) & 0xff;
                            //somaA = somaA + (a * elemFiltro);
                            r = (pixel>>16) & 0xff;
                            somaR = somaR + (r * elemFiltro);
                            g = (pixel>>8) & 0xff;
                            somaG = somaG + (g * elemFiltro);
                            b = pixel & 0xff;
                            somaB = somaB + (b * elemFiltro);
                        }
                    }
                    novoPixel = (a<<24) | (somaR<<16) | (somaG<<8) | somaB;
                    novaImg.setRGB(x-1, y-1, novoPixel);
                }
            }
        }
        return novaImg;
    }

    public BufferedImage filtroVerde(BufferedImage img){
        int pixel, largura, altura;
        int a, g;
        if(img != null){
            largura = img.getWidth();
            altura = img.getHeight();
            for (int y = 0; y < altura; y++){
                for (int x = 0; x < largura; x++){
                    pixel = img.getRGB(x,y);
                    a = (pixel>>24) & 0xff;
                    g = (pixel>>8) & 0xff;
                    pixel = (a<<24) | (0<<16) | (g<<8) | 0;
                    img.setRGB(x, y, pixel);
                }
            }
        }
        return img;
    }

    //Cria a matrix de pixel a partir da imagem
    private void createMatrixByImage() {
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

        //System.err.printf("%d %d \n", edited.length, edited[0].length);

        for (int x = 0; x < edited.length; x++) {
            for (int y = 0; y < edited[0].length; y++) {
                //System.err.println(edited[x][y]);
                if (edited[x][y] != null)
                    newImage.setRGB(x, y, edited[x][y].getRGB());
            }
        }
        this.image = newImage;
    }
}
