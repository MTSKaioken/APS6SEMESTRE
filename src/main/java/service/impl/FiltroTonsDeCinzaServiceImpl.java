package service.impl;

import service.FiltroService;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class FiltroTonsDeCinzaServiceImpl implements FiltroService {

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

                    int media = (r + g + b) / 3;

                    p = (a << 24) | (media << 16) | (media << 8) | media;

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
