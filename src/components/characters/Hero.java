package components.characters;

import components.Dice;
import components.graphics.Arena;
import components.graphics.Tile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Hero extends GameCharacter {
    private int heroLevel;
    private int currentHealth;
    private boolean hasKey;

    public int getHeroLevel() {
        return heroLevel;
    }

    public boolean hasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public Hero(int x0, int y0) {
        super(x0, y0);
        filename = "img/hero-down.png";
        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dice = new Dice();
        maxHealth = 20 + 3 * dice.roll();
        currentHealth = maxHealth;
        hasKey = false;
        heroLevel = 1;
        defencePoints = 2 * dice.roll();
        strikePoints = 5 + dice.roll();
    }

    public void changeDirectionX(int directionX) {
        if (directionX > 0) {
            filename = "img/hero-right.png";
        } else {
            filename = "img/hero-left.png";
        }

        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeDirectionY(int directionY) {
        if (directionY > 0) {
            filename = "img/hero-down.png";
        } else {
            filename = "img/hero-up.png";
        }

        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validateStepX(int directionX, Arena arena) {
        int indexX = posX / arena.getSTEP() + directionX;
        int indexY = posY / arena.getSTEP();
        if (indexX >= 0 && indexX < arena.getWidthBySteps()) {
            Tile buffTile = arena.getLinesOfTiles().get(indexY).get(indexX);
            return buffTile.isPassAble();
        }
        return false;
    }

    public boolean validateStepY(int directionY, Arena arena) {
        int indexX = posX / arena.getSTEP();
        int indexY = posY / arena.getSTEP() + directionY;
        if (indexY >= 0 && indexY < arena.getHeightBySteps()) {
            Tile buffTile = arena.getLinesOfTiles().get(indexY).get(indexX);
            return buffTile.isPassAble();
        }
        return false;
    }

    @Override
    public boolean moveX(int directionX, Arena arena) {
        changeDirectionX(directionX);
        if (validateStepX(directionX, arena)) {
            posX += arena.getSTEP() * directionX;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveY(int directionY, Arena arena) {
        changeDirectionY(directionY);
        if (validateStepY(directionY, arena)) {
            posY += arena.getSTEP() * directionY;
            return true;
        }
        return false;
    }

    @Override
    public Hero clone(int x, int y) {
        Hero heroClone = new Hero(x, y);

        heroClone.filename = "img/hero-right.png";
        try {
            heroClone.image = ImageIO.read(new File(heroClone.filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        heroClone.maxHealth = maxHealth;
        heroClone.currentHealth = currentHealth;
        heroClone.heroLevel = heroLevel;
        heroClone.defencePoints = defencePoints;
        heroClone.strikePoints = strikePoints;
        return heroClone;
    }

    public void levelUp() {
        heroLevel++;
        maxHealth += dice.roll();
        defencePoints += dice.roll();
        strikePoints += dice.roll();
    }

    public void setCurrentHealth(int newHealth) {
        currentHealth = newHealth;
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
    public void strike(GameCharacter anotherChar) {
        int damage = strikePoints + 2 * dice.roll();
        System.out.println(damage);
        if (anotherChar instanceof EnemyMob) {
            if (damage > anotherChar.defencePoints) {
                anotherChar.getHit(damage);
            }
        }
    }
}
