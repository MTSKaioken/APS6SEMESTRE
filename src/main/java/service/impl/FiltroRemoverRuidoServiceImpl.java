package service.impl;

import service.FiltroService;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class FiltroRemoverRuidoServiceImpl implements FiltroService {

    /**
     * filtroPassaBaixaPeloFiltroMedianaRemovedorDeRuido
     * @param imagemEntrada
     * @return BufferedImage imagemSaida
     */

    @Override
    public BufferedImage aplicarFiltro(BufferedImage imagemEntrada) {
        int largura = imagemEntrada.getWidth();
        int altura = imagemEntrada.getHeight();

        BufferedImage saida = new BufferedImage(largura, altura, imagemEntrada.getType());

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
                                int color = imagemEntrada.getRGB(x + i, y + j);
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
            throw new RuntimeException(e);
        }
        return saida;
    }
}
