package components.graphics;

import components.characters.EnemyMob;
import components.characters.GameCharacter;
import components.characters.Hero;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BattleArena extends Arena {
    private Hero heroClone;
    private GameCharacter enemyClone;
    private Hero hero;
    private GameCharacter enemy;

    public Hero getHeroClone() {
        return heroClone;
    }

    public GameCharacter getEnemyClone() {
        return enemyClone;
    }

    public GameCharacter getBattledEnemy() {
        return enemy;
    }

    public BattleArena(Hero hero, GameCharacter enemy, JFrame frame) {
        linesOfTiles = loadArena("arenas/battle-arena.txt");

        WIDTH = this.linesOfTiles.get(0).size() *STEP;
        HEIGHT = this.linesOfTiles.size() * STEP;
        WIDTH_BY_STEPS = WIDTH / STEP;
        HEIGHT_BY_STEPS = HEIGHT / STEP;

        this.frame = frame;
        this.hero = hero;
        this.enemy = enemy;
        heroClone = hero.clone(2 * STEP, 2 * STEP);
        enemyClone = enemy.clone(4 * STEP, 2 * STEP);
        gameCharacters = new ArrayList<>();
        gameCharacters.add(heroClone);
        gameCharacters.add(enemyClone);
        hud = new HUD(frame, true);
        hud.setMoveMessage("Press SPACE to strike...");
        hud.setBattleMessage(heroClone, enemyClone);

        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.frame.add(this, "North");
    }

    public void winnBattle() {
        if (enemy instanceof EnemyMob) {
            if (((EnemyMob) enemy).isKeyHolder()) {
                hero.setHasKey(true);
                setKey();
                hud.setBonusMessage("You found the key!");
                hud.setMessage(hero);
            }
        }
        hud.setMoveMessage("Press ESC to exit battle...");
        hero.setCurrentHealth(heroClone.getCurrentHealth());
        hero.levelUp();
    }

    public void rewriteHud() {
        hud.setBattleMessage(heroClone, enemyClone);
    }

    public void lostBattle() {

    }
}
