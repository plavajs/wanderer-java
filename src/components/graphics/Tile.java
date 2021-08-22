package components.graphics;

import components.PositionedImage;
import components.ResourceReader;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Tile extends PositionedImage {
    private boolean passAble;
    private String filename;

    public Tile(int posX, int posY, boolean passAble) {
        super(posX, posY);
        if (passAble) {
            filename = "img/floor.png";
        } else {
            filename = "img/wall.png";
        }

        image = ResourceReader.readImage(filename);
        this.passAble = passAble;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    public boolean isPassAble() {
        return passAble;
    }
}
