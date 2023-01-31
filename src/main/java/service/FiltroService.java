package service;


import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class FiltroService {

    public BufferedImage passaAltaSobel(BufferedImage imagem) {
        int largura = imagem.getWidth();
        int altura = imagem.getHeight();

        // Criar matrizes de pesos para o filtro de Sobel
        int[][] xKernel = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] yKernel = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        // Aplicar o filtro
        BufferedImage saida = new BufferedImage(largura, altura, BufferedImage.TYPE_BYTE_GRAY);

        try {

            for (int x = 1; x < largura - 1; x++) {
                for (int y = 1; y < altura - 1; y++) {
                    int xGradiente = 0, yGradiente = 0;
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int rgb = imagem.getRGB(x + i, y + j);
                            int gray = (rgb >> 16) & 0xff;
                            xGradiente += gray * xKernel[i + 1][j + 1];
                            yGradiente += gray * yKernel[i + 1][j + 1];
                        }
                    }
                    int gradiente = (int) Math.sqrt(xGradiente * xGradiente + yGradiente * yGradiente);
                    gradiente = Math.min(255, gradiente);
                    saida.setRGB(x, y, (gradiente << 16) | (gradiente << 8) | gradiente);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao aplicar o filtro!");
            e.printStackTrace();
        }
        return saida;
    }


    public BufferedImage equalizarHistogramaParaMelhorarContraste(BufferedImage imagem) {
        int largura = imagem.getWidth();
        int altura = imagem.getHeight();

        BufferedImage imagemEqualizada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        try {
            int[] histograma = new int[256];
            int[] funcaoDistribuicaoAcumulada = new int[256]; //cdf
            int[] valorEqualizado = new int[256];
            int totalPixels = largura * altura;
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {
                    int color = imagem.getRGB(x, y);
                    int red = (color >> 16) & 0xff;
                    histograma[red]++;
                }
            }
            funcaoDistribuicaoAcumulada[0] = histograma[0];
            for (int i = 1; i < 256; i++) {
                funcaoDistribuicaoAcumulada[i] = funcaoDistribuicaoAcumulada[i - 1] + histograma[i];
            }
            for (int i = 0; i < 256; i++) {
                valorEqualizado[i] = (int) (((double) funcaoDistribuicaoAcumulada[i] / totalPixels) * 255);
            }
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {
                    int color = imagem.getRGB(x, y);
                    int red = (color >> 16) & 0xff;
                    int green = (color >> 8) & 0xff;
                    int blue = color & 0xff;
                    red = valorEqualizado[red];
                    green = valorEqualizado[green];
                    blue = valorEqualizado[blue];
                    int novaCor = (red << 16) | (green << 8) | blue;
                    imagemEqualizada.setRGB(x, y, novaCor);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao aplicar o filtro!");
            e.printStackTrace();
        }
        return imagemEqualizada;
    }

    public BufferedImage filtroPassaBaixaPeloFiltroMedianaRemovedorDeRuido(BufferedImage imagem) {


        int largura = imagem.getWidth();
        int altura = imagem.getHeight();

        // Cria uma imagem para armazenar a imagem processada
        BufferedImage saida = new BufferedImage(largura, altura, imagem.getType());

        try {
            // Define o tamanho da janela deslizante (neste caso, 3x3)
            int tamanhoJanela = 3;
            int centroJanela = tamanhoJanela / 2;

            // Percorre cada pixel da imagem
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {

                    // Inicializa um array para armazenar os valores de cor dos pixels na janela deslizante
                    int[] valoresVermelhos = new int[tamanhoJanela * tamanhoJanela];
                    int[] valoresVerdes = new int[tamanhoJanela * tamanhoJanela];
                    int[] valoresAzuis = new int[tamanhoJanela * tamanhoJanela];
                    int pixels = 0;
                    for (int i = - centroJanela; i <=  centroJanela; i++) {
                        for (int j = - centroJanela; j <=  centroJanela; j++) {
                            // Verifica se o pixel está dentro dos limites da imagem
                            if (x + i >= 0 && x + i < largura && y + j >= 0 && y + j < altura) {
                                // Adiciona os valores de cor do pixel ao array
                                int color = imagem.getRGB(x + i, y + j);
                                valoresVermelhos[pixels] = (color >> 16) & 0xff;
                                valoresVerdes[pixels] = (color >> 8) & 0xff;
                                valoresAzuis[pixels] = color & 0xff;
                                pixels++;
                            }
                        }
                    }

                    // Ordena os arrays de valores de cor
                    Arrays.sort(valoresVermelhos, 0, pixels);
                    Arrays.sort(valoresVerdes, 0, pixels);
                    Arrays.sort(valoresAzuis, 0, pixels);

                    // Obtém a mediana dos valores de cor na janela deslizante
                    int medianaVermelho = valoresVermelhos[pixels / 2];
                    int medianaVerde = valoresVerdes[pixels / 2];
                    int medianaAzul = valoresAzuis[pixels / 2];

                    // Define os valores de cor medianos para o pixel atual
                    int novaCor = (medianaVermelho << 16) | (medianaVerde << 8) | medianaAzul;
                    saida.setRGB(x, y, novaCor);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao aplicar o filtro!");
            e.printStackTrace();
        }
        return saida;
    }

    public BufferedImage converteParaTonsDeCinza(BufferedImage imagem) {
        try {
            int largura = imagem.getWidth();
            int altura = imagem.getHeight();

            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                    int p = imagem.getRGB(x, y);

                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    int media = (r + g + b) / 3;

                    p = (a << 24) | (media << 16) | (media << 8) | media;

                    imagem.setRGB(x, y, p);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao aplicar o filtro!");
            e.printStackTrace();
        }
        return imagem;
    }

    public BufferedImage tornaImagemNegativa(BufferedImage imagem) {
        try {
            int largura = imagem.getWidth();
            int altura = imagem.getHeight();

            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                    int p = imagem.getRGB(x, y);

                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;

                    p = (a << 24) | (r << 16) | (g << 8) | b;

                    imagem.setRGB(x, y, p);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao aplicar o filtro!");
            e.printStackTrace();
        }

        return imagem;
    }

    public BufferedImage tornaImagemPretaBranca(BufferedImage imagem, int threshold) {
        try {
            int largura = imagem.getWidth();
            int altura = imagem.getHeight();

            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                    int p = imagem.getRGB(x, y);

                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    int media = (r + g + b) / 3; // media do valor do rgb

                    if (media < threshold) {
                        media = 0; // branco
                    } else {
                        media = 255; // preto
                    }

                    p = (a << 24) | (media << 16) | (media << 8) | media;

                    imagem.setRGB(x, y, p);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao aplicar o filtro!");
            e.printStackTrace();
        }
        return imagem;
    }
}
