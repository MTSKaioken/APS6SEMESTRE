package model;

import java.awt.image.BufferedImage;

public class ImageModel {
    private BufferedImage imagemEntrada;
    private BufferedImage imagemSaida;

    public BufferedImage getImagemEntrada() {
        return imagemEntrada;
    }

    public void setImagemEntrada(BufferedImage imagemEntrada) {
        this.imagemEntrada = imagemEntrada;
    }
}
