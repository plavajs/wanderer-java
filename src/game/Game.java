package game;

import components.characters.Boss;
import components.characters.EnemyMob;
import components.characters.GameCharacter;
import components.characters.Hero;
import components.graphics.Arena;
import components.graphics.BattleArena;
import components.graphics.HUD;
import components.graphics.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JFrame implements KeyListener {
    private Arena arena;
    private Hero hero;
    private Boss boss;
    private ArrayList<GameCharacter> allGameCharacters;
    private ArrayList<GameCharacter> enemyGameCharacters;
    private JFrame mainFrame;
    private int heroStepCounter;
    private Random rnd;
    private JFrame battleFrame;
    private BattleArena battle;
    private boolean inBattle;
    private HUD mainHud;
    private HUD battleHud;

    public Game() {
        allGameCharacters = new ArrayList<>();
        enemyGameCharacters = new ArrayList<>();
        heroStepCounter = 0;
        inBattle = false;

        initGraphics();
        initCharacters();
        mainHud.setHeroMessage(hero);

        checkForMeeting();
        giveRandomMobTheKey();
    }

    private void initCharacters() {
        hero = new Hero(0, 0);
        allGameCharacters.add(hero);

        Tile buffTile = generateValidSpawnTile();
        boss = new Boss(buffTile.getPosX(), buffTile.getPosY());
        enemyGameCharacters.add(boss);
        allGameCharacters.add(boss);

        rnd = new Random();
        for (int i = 0; i < 5 + arena.getArenaLevel(); i++) {
            buffTile = generateValidSpawnTile();
            EnemyMob buffChar = new EnemyMob(buffTile.getPosX(), buffTile.getPosY());
            enemyGameCharacters.add(buffChar);
            allGameCharacters.add(buffChar);
        }
    }

    private void giveRandomMobTheKey() {
        rnd = new Random();
        int index = rnd.nextInt(enemyGameCharacters.size() - 1) + 1;
        ((EnemyMob)enemyGameCharacters.get(index)).setKeyHolder();
    }

    private Tile generateValidSpawnTile() {
        rnd = new Random();
        Tile buffTile;
        do {
            int rndX = rnd.nextInt(arena.getWidthBySteps());
            int rndY = rnd.nextInt(arena.getWidthBySteps());
            buffTile = arena.getLinesOfTiles().get(rndY).get(rndX);
        } while (!buffTile.isPassAble());
        return buffTile;
    }

    private GameCharacter checkForMeeting() {
        GameCharacter enemy = null;
        boolean meet = false;
        for (GameCharacter c : enemyGameCharacters) {
            if (c.getPosX() == hero.getPosX() && c.getPosY() == hero.getPosY()) {
                mainHud.setEnemyMessage(c);
                enemy = c;
                meet = true;
            }
        }
         if (!meet) {
            mainHud.setEnemyMessage("");
        }
        return enemy;
    }

    public void initGraphics() {
        mainFrame = new JFrame("Wanderer");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        arena = new Arena(allGameCharacters, mainFrame);
        mainHud = new HUD(mainFrame, arena.getWidth());

        battleFrame = new JFrame("Battle");
        battleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        battleFrame.setLayout(new BorderLayout());
        battleFrame.setVisible(false);

        mainFrame.setLocation(300, 0);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

        // Here is how you set up a new window and adding our board to it

        // Here is how you can add a key event listener
        // The board object will be notified when hitting any key
        // with the system calling one of the below 3 methods
        mainFrame.addKeyListener(this);

        // Notice (at the top) that we can only do this
        // because this Board class (the type of the board object) is also a KeyListener
    }

    // To be a KeyListener the class needs to have these 3 methods in it
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    // But actually we can use just this one for our goals here
    @Override
    public void keyReleased(KeyEvent e) {

        // When the up or down keys hit, we change the position of our box
        if (inBattle) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                closeBattle();
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (battle.getHeroClone().isAlive() && battle.getEnemyClone().isAlive()) {
                    battle.getHeroClone().strike(battle.getEnemyClone());
                    if (battle.getEnemyClone().isAlive()) {
                        battle.getEnemyClone().strike(battle.getHeroClone());
                    } else {
                        winnBattle();
                        allGameCharacters.remove(battle.getEnemy());
                        enemyGameCharacters.remove(battle.getEnemy());
                    }
                } else if (!battle.getHeroClone().isAlive()) {
                    battle.getHeroClone().die();
                    lossBattle();
                    battle.repaint();

                    if (e.getKeyCode() == KeyEvent.VK_R) {
                        restartGame();
                        closeBattle();
                    }
                }

            }
        } else {
            boolean heroMoved = false;
            checkKey();
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                heroMoved = hero.moveY(-1, arena);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                heroMoved = hero.moveY(1, arena);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                heroMoved = hero.moveX(-1, arena);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                heroMoved = hero.moveX(1, arena);
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER && checkForMeeting() != null) {
                inBattle = true;
                initBattleArena(checkForMeeting());
                battle.repaint();
            }
            if (heroMoved) {
                heroStepCounter++;
                if (heroStepCounter == 2) {
                    moveEnemies();
                    heroStepCounter = 0;
                }
            }
        }

// and redraw to have a new picture with the new coordinates
//        System.out.println(hero.hasKey());
        checkForMeeting();

        arena.repaint();
    }

    public void moveEnemies() {
        rnd = new Random();
        boolean enemyMoved = false;
        for (GameCharacter c : enemyGameCharacters) {
            while (!enemyMoved) {
                int direction = rnd.nextInt(3) - 1;
                int xOrY = rnd.nextInt(2);
                if (xOrY == 1) {
                    enemyMoved = c.moveX(direction, arena);
                    if (!enemyMoved) {
                        enemyMoved = c.moveY(direction, arena);
                    }
                } else {
                    enemyMoved = c.moveY(direction, arena);
                }
            }
            enemyMoved = false;
        }
    }

    public void initMainArena() {
        mainFrame.remove(arena);
        mainFrame.remove(mainHud);
        arena = new Arena(allGameCharacters, mainFrame);
        mainHud = new HUD(mainFrame, arena.getWidth());
        mainHud.setHeroMessage(hero);
        checkKey();
        checkForMeeting();
    }

    public void initBattleArena(GameCharacter enemy) {
        inBattle = true;
        mainFrame.setVisible(false);

        battle = new BattleArena(hero, enemy, battleFrame);

        battleHud = new HUD(battleFrame, battle.getWidth());
        battleHud.setHeroMessage(hero);
        battleHud.setEnemyMessage(enemy);

        battleFrame.setLocation(400,100);
        battleFrame.pack();
        battleFrame.setVisible(true);

        battleFrame.addKeyListener(this);
    }

    public void closeBattle() {
        inBattle = false;

        battleFrame.remove(battleHud);
        battleFrame.remove(battle);
        battleFrame.dispose();
        battleFrame.setVisible(false);

        mainFrame.setVisible(true);

        initMainArena();
        mainFrame.pack();
    }

    public void checkKey() {
        if (hero.hasKey()) {
            mainHud.setKeyImage();
        }
    }

    public void winnBattle() {
        GameCharacter enemy = battle.getEnemy();

        battle.getEnemyClone().die();
        battle.repaint();
        battle.getHeroClone().levelUp();

        if (enemy instanceof EnemyMob) {
            if (((EnemyMob) enemy).isKeyHolder()) {
                battle.getHeroClone().setHasKey(true);
                battleHud.setKeyImage();
                battleHud.setBonusMessage("You found the KEY!");
            }
        }
        battleHud.setBattleMessage("You won the battle!");

        battleHud.repaint();
        hero.copyStats(battle.getHeroClone());
    }

//    public void rewriteBattleHud() {
//        battleHud.setBattleMessage(battle.getHeroClone(), battle.getEnemyClone());
//    }

    public void lossBattle() {
        battleHud.setBattleMessage("You lost the battle...");
        battleHud.setBonusMessage("Press 'R' to restart game!");
    }

    public void restartGame() {
        allGameCharacters = new ArrayList<>();
        enemyGameCharacters = new ArrayList<>();
        heroStepCounter = 0;
        inBattle = false;

        initGraphics();
        initCharacters();
        mainHud.setHeroMessage(hero);

        checkForMeeting();
        giveRandomMobTheKey();
    }
}
