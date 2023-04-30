package service.impl;

import service.FiltroService;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class FiltroPretoEBrancoServiceImpl implements FiltroService {

    private static final int THRESHOLD = 175;

    /**
     * Aplica um filtro de escala de cinza (para trabalhar melhor com imagens coloridas),
     * e depois converte para preto e branco
     *
     * @param imagemEntrada
     * @return BufferedImage imagemSaida
     */

    @Override
    public BufferedImage aplicarFiltro(BufferedImage imagemEntrada) {
        FiltroService preFiltros = new FiltroMelhorarContrasteServiceImpl();
        BufferedImage imagemIntermediaria = preFiltros.aplicarFiltro(imagemEntrada);


        int largura = imagemEntrada.getWidth();
        int altura = imagemEntrada.getHeight();

        BufferedImage imagemSaida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        try {

            for (int y = 0; y < altura; y++) {
                for (int x = 0; x < largura; x++) {
                    int p = imagemIntermediaria.getRGB(x, y);

                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    int media = (r + g + b) / 3; // media do valor do rgb

                    if (media < THRESHOLD) {
                        media = 0; // branco
                    } else {
                        media = 255; // preto
                    }

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
