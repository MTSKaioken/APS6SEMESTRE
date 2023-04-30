package service.impl;

import service.FiltroService;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class FiltroNegativoServiceImpl implements FiltroService {

    /**
     * Aplica um filtro negativo sob a imagem de entrada e retorna uma imagem de saida
     *
     * @param imagemEntrada
     * @return BufferedImage imagemSaida
     */
    @Override
    public BufferedImage aplicarFiltro(BufferedImage imagemEntrada) {
        int largura = imagemEntrada.getWidth();
        int altura = imagemEntrada.getHeight();

        BufferedImage imagemSaida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);

        try {
            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                    int p = imagemEntrada.getRGB(x, y);

                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;

                    p = (a << 24) | (r << 16) | (g << 8) | b;

                    imagemSaida.setRGB(x, y, p);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao aplicar o filtro!");
            throw new RuntimeException(e);
        }

        return imagemSaida;
    }
}
