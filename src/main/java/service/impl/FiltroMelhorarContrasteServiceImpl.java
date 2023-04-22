package service.impl;

import service.FiltroService;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class FiltroMelhorarContrasteServiceImpl implements FiltroService {

    /**
     * equalizarHistogramaParaMelhorarContraste
     * @param imagemEntrada
     * @return BufferedImage imagemSaida
     */
    @Override
    public BufferedImage aplicarFiltro(BufferedImage imagemEntrada) {
        int largura = imagemEntrada.getWidth();
        int altura = imagemEntrada.getHeight();

        BufferedImage imagemSaida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        try {
            int[] histograma = new int[256];
            int[] funcaoDistribuicaoAcumulada = new int[256]; //cdf
            int[] valorEqualizado = new int[256];
            int totalPixels = largura * altura;
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {
                    int color = imagemEntrada.getRGB(x, y);
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
                    int color = imagemEntrada.getRGB(x, y);
                    int red = (color >> 16) & 0xff;
                    int green = (color >> 8) & 0xff;
                    int blue = color & 0xff;
                    red = valorEqualizado[red];
                    green = valorEqualizado[green];
                    blue = valorEqualizado[blue];
                    int novaCor = (red << 16) | (green << 8) | blue;
                    imagemSaida.setRGB(x, y, novaCor);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao aplicar o filtro!");
            throw new RuntimeException(e);
        }
        return imagemSaida;
    }
}
