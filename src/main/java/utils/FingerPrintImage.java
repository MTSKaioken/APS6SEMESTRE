package utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FingerPrintImage {
    private char[][] imagen;
    private int Height; //alto
    private int Width;  //ancho

    public FingerPrintImage(int Height, int Width) {
        imagen = new char[Width][Height];
        this.Height = Height;
        this.Width = Width;
    }

    public int getHeight() {
        return Height;
    }

    public int getWidth() {
        return Width;
    }

    public char getPixel(int x, int y) {
        return imagen[x][y];
    }

    public void setPixel(int x, int y, char gris) {
        imagen[x][y] = gris;
    }

    public boolean Iteration(int iter){
        char[] c= new char[9];
        FingerPrintImage aux = copyF();
        int count=0;
        int negros=0;
        boolean cambio=false;
        for (int i = 10; i < Width-1; i++)
        {
            for (int j = 10; j < Height-1; j++)
            {
                if(imagen[i][j]==255){
                    count=0;
                    negros=0;
                    c[0] = imagen[i-1][j];
                    c[1] = imagen[i-1][j+1];
                    c[2] = imagen[i][j+1];
                    c[3] = imagen[i+1][j+1];
                    c[4] = imagen[i+1][j];
                    c[5] = imagen[i+1][j-1];
                    c[6] = imagen[i][j-1];
                    c[7] = imagen[i-1][j-1];
                    c[8] = imagen[i-1][j];
                    for(int k=0; k<c.length-1;k++){
                        if(c[k]==255 && c[k+1]==0) count++;
                        if(c[k]==0) negros++;
                    }

                    int A  = count;
                    int B  = negros;
                    int m1 = iter == 0 ? (c[0] * c[2] * c[4]) : (c[0] * c[2] * c[6]);
                    int m2 = iter == 0 ? (c[2] * c[4] * c[6]) : (c[0] * c[4] * c[6]);

                    if (A == 1 && (B >= 2 && B <= 6) && m1 == 0 && m2 == 0){
                        aux.setPixel(i, j, (char)255);
                        cambio=true;
                    }
                    else aux.setPixel(i, j, (char)0);
                }
            }
        }
        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {

                int tmp = 255 - aux.getPixel(i, j);
                if (imagen[i][j] == tmp && imagen[i][j] == 255) {
                    imagen[i][j] = 255;
                } else {
                    imagen[i][j] = 0;
                }

            }
        }
        return cambio;
    }

    public boolean detectarMinucias(int x, int y) {
        int[][] nbrs = {{1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}};
        int counter = 0;
        for (int i = 0; i < nbrs.length - 1; i++) {
            counter += Math.abs(imagen[x + nbrs[i][1]][y + nbrs[i][0]] - imagen[x + nbrs[i + 1][1]][y + nbrs[i + 1][0]]);
        }
        if (counter / 2 == 1 || counter / 2 == 3) {
            return true;
        } else {
            return false;
        }

    }

    public FingerPrintImage copyF() {
        FingerPrintImage copiamatriz = new FingerPrintImage(Height, Width);

        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                copiamatriz.setPixel(i, j, imagen[i][j]);
            }
        }

        return copiamatriz;

    }

    public BufferedImage copy() {
        BufferedImage copiamatriz = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                int a = imagen[i][j];
                Color newColor = new Color(a, a, a);
                copiamatriz.setRGB(i, j, newColor.getRGB());
            }
        }

        return copiamatriz;

    }

    public FingerPrintImage invertir() {
        FingerPrintImage salida = new FingerPrintImage(getHeight(), getWidth());
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                char valor = getPixel(x, y);
                if (valor == 255) {
                    char C = 1;
                    salida.setPixel(x, y, C);
                } else if (valor == 1) {
                    char C = 255;
                    salida.setPixel(x, y, C);
                } else if (valor == 0) {
                    char C = 0;
                    salida.setPixel(x, y, C);
                }
            }
        }
        return salida;
    }

}
