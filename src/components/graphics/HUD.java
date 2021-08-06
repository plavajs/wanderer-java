package components.graphics;

import components.characters.Boss;
import components.characters.GameCharacter;
import components.characters.Hero;

import javax.swing.*;
import java.awt.*;

public class HUD extends JPanel {
    private int width;
    private int height;
    private JLabel firstLine;
    private JLabel secondLine;
    private JLabel keyImage;

    public HUD(Frame frame) {
        width = Arena.getWIDTH() - 90;
        height = 90;

        setGraphics(frame);
    }

    private void setGraphics(Frame frame) {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));

        firstLine = new JLabel();
        firstLine.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 0));
        firstLine.setBackground(Color.GRAY);

        secondLine = new JLabel();
        secondLine.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 0));

        JPanel leftSide = new JPanel();
        leftSide.setLayout(new BorderLayout());
        leftSide.add(firstLine, "North");
        leftSide.add(secondLine, "South");
        leftSide.setBackground(Color.GRAY);

        keyImage = new JLabel();
        keyImage.setPreferredSize(new Dimension(80, 80));

        JPanel rightSide = new JPanel();
        rightSide.setBackground(Color.GRAY);
        rightSide.add(keyImage);

        this.add(leftSide, "Center");
        this.add(rightSide, "East");

        frame.add(this, "Center");
    }

    public void setMessage(Hero hero) {
        String heroMessage = "Hero (Level " + hero.getHeroLevel() + ")   HP: " + hero.getCurrentHealth() +
                "/" + hero. getMaxHealth() + "  |  DP: " + hero.getDefense() + "  |  SP: " + hero.getStrike();

        firstLine.setText(heroMessage);
        secondLine.setText("");
    }

    public void setMessage(Hero hero, GameCharacter otherChar) {
        String heroMessage = "Hero (Level " + hero.getHeroLevel() + ")   HP: " + hero.getCurrentHealth() +
                "/" + hero. getMaxHealth() + "  |  DP: " + hero.getDefense() + "  |  SP: " + hero.getStrike();

        firstLine.setText(heroMessage);

        String otherCharMessage;
        if (otherChar instanceof Boss) {
            otherCharMessage = "Boss ";
        } else {
            otherCharMessage = "Enemy Mob ";
        }
        otherCharMessage += "(Level " + Arena.getArenaLevel() + ")   HP: " + otherChar.getMaxHealth() + "/"
                + otherChar. getMaxHealth() + "  |  DP: " + otherChar.getDefense() + "  |  SP: "
                + otherChar.getStrike() + "      Press Enter to fight...";

        secondLine.setText(otherCharMessage);
    }

    public void setKeyImage(Frame frame) {
        keyImage = new JLabel((new ImageIcon("img/key.png")), JLabel.CENTER);
        JPanel rightSide = new JPanel();
        rightSide.setBackground(Color.GRAY);
        rightSide.add(keyImage);
        frame.add(rightSide, "East");
    }

    public void clearKeyImage(Frame frame) {
        keyImage = new JLabel();
        JPanel rightSide = new JPanel();
        rightSide.setBackground(Color.GRAY);
        rightSide.add(keyImage);
        frame.add(rightSide, "East");
    }

    public int getHEIGHT() {
        return height;
    }
}
