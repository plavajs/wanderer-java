package components.characters;

import components.Dice;
import components.graphics.Arena;
import components.graphics.Tile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Boss extends GameCharacter {

    public Boss(int posX, int posY) {
        super(posX, posY);
        filename = "resources/img/boss.png";
        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dice = new Dice();
        maxHealth = 3 + 4 * Arena.getArenaLevel() + dice.roll() * 3 * Arena.getArenaLevel();
        currentHealth = maxHealth;
        defencePoints = 1 + Arena.getArenaLevel() * 2 + dice.roll() * Arena.getArenaLevel();
        strikePoints = 2 + Arena.getArenaLevel() * 3 + dice.roll() * Arena.getArenaLevel();

        strikeMessage = "";
    }

    @Override
    public boolean validateStepX(int directionX, Arena arena) {
        int indexX = posX / Arena.getSTEP() + directionX;
        int indexY = posY / Arena.getSTEP();
        if (indexX >= 0 && indexX < Arena.getWidthBySteps()) {
            Tile buffTile = arena.getLinesOfTiles().get(indexY).get(indexX);
            return buffTile.isPassAble();
        }
        return false;
    }

    @Override
    public boolean validateStepY(int directionY, Arena arena) {
        int indexX = posX / Arena.getSTEP();
        int indexY = posY / Arena.getSTEP() + directionY;
        if (indexY >= 0 && indexY < Arena.getHeightBySteps()) {
            Tile buffTile = arena.getLinesOfTiles().get(indexY).get(indexX);
            return buffTile.isPassAble();
        }
        return false;
    }

    @Override
    public boolean moveX(int directionX, Arena arena) {
        if (validateStepX(directionX, arena)) {
            posX += Arena.getSTEP() * directionX;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveY(int directionY, Arena arena) {
        if (validateStepY(directionY, arena)) {
            posY += Arena.getSTEP() * directionY;
            return true;
        }
        return false;
    }

    @Override
    public Boss clone(int x, int y) {
        Boss bossClone = new Boss(x, y);

        bossClone.filename = "resources/img/boss.png";
        try {
            bossClone.image = ImageIO.read(new File(bossClone.filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        bossClone.maxHealth = maxHealth;
        bossClone.currentHealth = currentHealth;
        bossClone.defencePoints = defencePoints;
        bossClone.strikePoints = strikePoints;
        return bossClone;
    }

    @Override
    public void getHit(int damage) {
        currentHealth -= (damage - defencePoints);
        if (currentHealth < 0) {
            currentHealth = 0;
        }
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public boolean isAlive() {
        return currentHealth > 0;
    }

    @Override
    public void strike(GameCharacter anotherChar) {
        int damage = strikePoints + dice.roll() / 2;
        strikeMessage = "strikes for " + damage + " damage";
        if (damage > anotherChar.defencePoints) {
            anotherChar.getHit(damage);
        }
    }

    @Override
    public String toString() {
        return "Boss             HP: " + currentHealth + "/" + maxHealth + " | DP: " + defencePoints + " | SP: " + strikePoints;
    }
}
