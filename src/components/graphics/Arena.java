package components.graphics;

import components.characters.GameCharacter;
import components.characters.Hero;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Arena extends JComponent {
    protected static final int STEP = 72;
    protected static int WIDTH;
    protected static int WIDTH_BY_STEPS;
    protected static int HEIGHT;
    protected static int HEIGHT_BY_STEPS;
    protected List<List<Tile>> linesOfTiles;
    protected List<GameCharacter> gameCharacters;
    protected JFrame frame;
    private static int arenaLevel = 1;

    public static int getSTEP() {
        return STEP;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWidthBySteps() {
        return WIDTH_BY_STEPS;
    }

    public static int getHeightBySteps() {
        return HEIGHT_BY_STEPS;
    }

    public static int getArenaLevel() {
        return arenaLevel;
    }

    public List<List<Tile>> getLinesOfTiles() {
        return linesOfTiles;
    }

    public Arena(ArrayList<GameCharacter> gameCharacters, JFrame frame) {
        this.gameCharacters = gameCharacters;
        this.frame = frame;

        linesOfTiles = loadArena("arenas/lvl-" + arenaLevel + ".txt");
        WIDTH = linesOfTiles.get(0).size() * STEP;
        HEIGHT = linesOfTiles.size() * STEP;
        WIDTH_BY_STEPS = WIDTH / STEP;
        HEIGHT_BY_STEPS = HEIGHT / STEP;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.frame.add(this, "Center");
    }

    protected Arena() {}

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        // here you have a 720x720 canvas
        // you can create and draw an image using the class below e.g.

        for (List<Tile> l : linesOfTiles) {
            for (Tile t : l) {
                t.draw(graphics);
            }
        }
        if (!gameCharacters.isEmpty()) {
            for (GameCharacter c : gameCharacters) {
                c.draw(graphics);
            }
        }
    }

    protected List<String> readFile(String file) {
        Path filePath = Paths.get(file);
        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("Can't read file " + file);
        }

        return lines;
    }

    protected List<List<Tile>> loadArena(String file) {
        List<List<Tile>> linesOfTiles = new ArrayList<>();
        if (!readFile(file).isEmpty()) {
            ArrayList<String> fileLines = (ArrayList<String>) readFile(file);

            for (int i = 0; i < fileLines.size(); i++) {
                List<Tile> line = new ArrayList<>();
                for (int j = 0; j < fileLines.get(0).length(); j++) {
                    line.add(new Tile(j * STEP, i * STEP, fileLines.get(i).charAt(j) == '1'));
                }
                linesOfTiles.add(line);
            }

            return linesOfTiles;
        }
        return new ArrayList<>();
    }
}
