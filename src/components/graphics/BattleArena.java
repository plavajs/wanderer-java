package components.graphics;

import components.characters.EnemyMob;
import components.characters.GameCharacter;
import components.characters.Hero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BattleArena extends Arena {
    private Hero heroClone;
    private GameCharacter enemyClone;
    private GameCharacter enemy;

    public Hero getHeroClone() {
        return heroClone;
    }

    public GameCharacter getEnemyClone() {
        return enemyClone;
    }

    public GameCharacter getEnemy() {
        return enemy;
    }

    public BattleArena(Hero hero, GameCharacter enemy, JFrame frame) {
        linesOfTiles = loadArena("arenas/battle-arena.txt");

        WIDTH = linesOfTiles.get(0).size() * STEP;
        HEIGHT = linesOfTiles.size() * STEP;
        WIDTH_BY_STEPS = WIDTH / STEP;
        HEIGHT_BY_STEPS = HEIGHT / STEP;

        this.frame = frame;
        this.enemy = enemy;
        heroClone = hero.clone(2 * STEP, 2 * STEP);
        enemyClone = enemy.clone(4 * STEP, 2 * STEP);
        gameCharacters = new ArrayList<>();
        gameCharacters.add(heroClone);
        gameCharacters.add(enemyClone);

        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.frame.add(this, "North");
    }

}
