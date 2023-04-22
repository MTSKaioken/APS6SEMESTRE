package service.impl;

import service.FiltroService;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class FiltroDetectarBordasServiceImpl implements FiltroService {

    /**
     * passsa Alta Sobel
     *
     * @param imagemEntrada
     * @return BufferedImage imagemSaida
     */
    @Override
    public BufferedImage aplicarFiltro(BufferedImage imagemEntrada) {
        int largura = imagemEntrada.getWidth();
        int altura = imagemEntrada.getHeight();

        // Aplicar o filtro
        BufferedImage saida = new BufferedImage(largura, altura, BufferedImage.TYPE_BYTE_GRAY);

        // Criar matrizes de pesos para o filtro de Sobel
        int[][] xKernel = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] yKernel = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        try {

            for (int x = 1; x < largura - 1; x++) {
                for (int y = 1; y < altura - 1; y++) {
                    int xGradiente = 0, yGradiente = 0;
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int rgb = imagemEntrada.getRGB(x + i, y + j);
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
            throw new RuntimeException(e);
        }
        return saida;
    }
}
