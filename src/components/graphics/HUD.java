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
    private JPanel rightSide;
    private JPanel leftSide;
    private String moveMessage;
    private String bonusMessage = " ";
    private JFrame frame;

    public HUD(JFrame frame, boolean inBattle) {

        width = Arena.getWIDTH() - 90;

        height = 90;

        this.frame = frame;

        setGraphics();

    }

    private void setGraphics() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));

        firstLine = new JLabel();
        firstLine.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 0));
        firstLine.setBackground(Color.GRAY);

        secondLine = new JLabel();
        secondLine.setBorder(BorderFactory.createEmptyBorder(0, 5, 20, 0));

        leftSide = new JPanel();
        leftSide.setLayout(new BorderLayout());
        leftSide.add(firstLine, "North");
        leftSide.add(secondLine, "South");
        leftSide.setBackground(Color.GRAY);

        keyImage = new JLabel();
        keyImage.setPreferredSize(new Dimension(80, 80));

        rightSide = new JPanel();
        rightSide.setBackground(Color.GRAY);
        rightSide.add(keyImage);

        this.add(rightSide, "East");
        this.add(leftSide, "Center");

        frame.add(this, "Center");
    }

    public void setMessage(Hero hero) {
        String heroMessage = "Hero (Lvl " + hero.getHeroLevel() + ") HP: " + hero.getCurrentHealth() +
                "/" + hero. getMaxHealth() + "  |  DP: " + hero.getDefense() + "  |  SP: " + hero.getStrike();

        firstLine.setText(heroMessage);
        secondLine.setText("");
    }

    public void setMessage(Hero hero, GameCharacter otherChar) {
        String heroMessage = "Hero (Lvl " + hero.getHeroLevel() + ") HP: " + hero.getCurrentHealth() +
                "/" + hero. getMaxHealth() + "  |  DP: " + hero.getDefense() + "  |  SP: " + hero.getStrike();

        firstLine.setText(heroMessage);

        String otherCharMessage;
        if (otherChar instanceof Boss) {
            otherCharMessage = "Boss ";
        } else {
            otherCharMessage = "Mob ";
        }
        otherCharMessage += "(Level " + Arena.getArenaLevel() + ") HP: " + otherChar.getCurrentHealth() + "/"
                + otherChar.getMaxHealth() + "  |  DP: " + otherChar.getDefense() + "  |  SP: "
                + otherChar.getStrike() + "  " + moveMessage;

        secondLine.setText(otherCharMessage);
    }

    public void setBattleMessage(Hero hero, GameCharacter otherChar) {
        String heroMessage = "Hero (Lvl " + hero.getHeroLevel() + ") HP: " + hero.getCurrentHealth() +
                "/" + hero. getMaxHealth() + "  |  DP: " + hero.getDefense() + "  |  SP: " + hero.getStrike()
                + "  " + bonusMessage;

        firstLine.setText(heroMessage);

        String otherCharMessage;
        if (otherChar instanceof Boss) {
            otherCharMessage = "Boss ";
        } else {
            otherCharMessage = "Mob ";
        }
        otherCharMessage += "(Lvl " + Arena.getArenaLevel() + ") HP: " + otherChar.getCurrentHealth() + "/"
                + otherChar.getMaxHealth() + "  |  DP: " + otherChar.getDefense() + "  |  SP: "
                + otherChar.getStrike() + "  " + moveMessage;

        secondLine.setText(otherCharMessage);
    }

    public void setKeyImage() {
        keyImage = new JLabel((new ImageIcon("img/key.png")), JLabel.CENTER);
        rightSide = new JPanel();
        rightSide.setBackground(Color.GRAY);
        rightSide.add(keyImage);
        this.add(rightSide, "East");
    }

    public void setMoveMessage(String moveMessage) {
        this.moveMessage = moveMessage;
    }

    public void setBonusMessage(String bonusMessage) {
        this.bonusMessage = bonusMessage;
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
