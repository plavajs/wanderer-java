package components.characters;

import components.Dice;
import components.graphics.Arena;
import components.graphics.Tile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class EnemyMob extends GameCharacter {
    private boolean keyHolder;
    private int currentHealth;

    public EnemyMob(int posX, int posY) {
        super(posX, posY);
        filename = "img/skeleton.png";
        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dice = new Dice();
        maxHealth = 2 * Arena.getArenaLevel() * dice.roll();
        currentHealth = maxHealth;
        defencePoints = Arena.getArenaLevel() / 2 * dice.roll();
        strikePoints = Arena.getArenaLevel() * dice.roll();
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
        try {
            enemyMobClone.image = ImageIO.read(new File(enemyMobClone.filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        enemyMobClone.maxHealth = this.maxHealth;
        enemyMobClone.currentHealth = this.currentHealth;
        enemyMobClone.defencePoints = this.defencePoints;
        enemyMobClone.strikePoints = this.strikePoints;
        return enemyMobClone;
    }

    @Override
    public void strike(GameCharacter anotherChar) {
        int damage = strikePoints + 2 * dice.roll();
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
        System.out.println(currentHealth);
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public boolean isAlive() {
        return currentHealth > 0;
    }

}
