package components.characters;

import components.Dice;
import components.ResourceReader;
import components.graphics.Arena;
import components.graphics.Tile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class EnemyMob extends GameCharacter {
    private boolean keyHolder;

    public EnemyMob(int posX, int posY) {
        super(posX, posY);
        filename = "img/skeleton.png";
        image = ResourceReader.readImage(filename);

        dice = new Dice();
        maxHealth = 3 * Arena.getArenaLevel() * dice.roll();
        currentHealth = maxHealth;
        defencePoints = Arena.getArenaLevel()  * dice.roll();
        strikePoints = 2 * Arena.getArenaLevel() * dice.roll();

        strikeMessage = "";
    }

    public boolean isKeyHolder() {
        return keyHolder;
    }

    public void setKeyHolder() {
        keyHolder = true;
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
    public EnemyMob clone(int x, int y) {
        EnemyMob enemyMobClone = new EnemyMob(x, y);

        enemyMobClone.filename = "img/skeleton.png";
        enemyMobClone.image = ResourceReader.readImage(enemyMobClone.filename);

        enemyMobClone.maxHealth = maxHealth;
        enemyMobClone.currentHealth = currentHealth;
        enemyMobClone.defencePoints = defencePoints;
        enemyMobClone.strikePoints = strikePoints;
        return enemyMobClone;
    }

    @Override
    public void strike(GameCharacter anotherChar) {
        int damage = strikePoints + dice.roll();
        strikeMessage = "strikes for " + damage + " damage";
        if (damage > anotherChar.defencePoints) {
            anotherChar.getHit(damage);
        }
    }

    @Override
    public void getHit(int damage) {
        currentHealth -= damage - defencePoints;
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
    public String toString() {
        return "Skeleton      HP: " + currentHealth + "/" + maxHealth + " | DP: " + defencePoints + " | SP: " + strikePoints;
    }

}
