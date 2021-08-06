package components;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PositionedImage {

    protected BufferedImage image;
    protected int posX, posY;
    protected String filename;

    protected PositionedImage(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void draw(Graphics graphics) {
        if (image != null) {
            graphics.drawImage(image, posX, posY, null);
        }
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}


