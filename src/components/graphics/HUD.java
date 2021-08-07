package components.graphics;

import components.characters.GameCharacter;
import components.characters.Hero;

import javax.swing.*;
import java.awt.*;

public class HUD extends JPanel {
    private int width;
    private int height;
    private JLabel left1 = new JLabel();
    private JLabel middle1 = new JLabel();
    private JLabel left2 = new JLabel();
    private JLabel middle2 = new JLabel();
    private JLabel left3 = new JLabel();
    private JLabel middle3 = new JLabel();
    private JLabel keyImage = new JLabel();
    private JPanel leftSide = new JPanel();
    private JPanel middleSide = new JPanel();
    private JPanel rightSide = new JPanel();
    private String moveMessage = "";
    private String bonusMessage = "";
    private JFrame frame;

    public HUD(JFrame frame, int width) {
        this.width = width;

        height = 90;

        this.frame = frame;

        setGraphics();
    }

    private void setGraphics() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));

        keyImage.setPreferredSize(new Dimension(80, 80));

        left1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        left2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        left3.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        middle2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        middle1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        middle3.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));

        leftSide.setLayout(new GridLayout(3,1));
        middleSide.setLayout(new GridLayout(3,1));

        leftSide.add(left1, "1");
        leftSide.add(left2, "3");
        leftSide.add(left3, "3");

        middleSide.add(middle1, "1");
        middleSide.add(middle2, "2");
        middleSide.add(middle3, "3");
        rightSide.add(keyImage);

        leftSide.setBackground(Color.GRAY);
        middleSide.setBackground(Color.GRAY);
        rightSide.setBackground(Color.GRAY);

        this.add(leftSide, "West");
        this.add(middleSide, "Center");
        this.add(rightSide, "East");

        frame.add(this, "South");
    }

    public void setHeroMessage(Hero hero) {
        left1.setText(String.valueOf(hero));
    }

    public void setEnemyMessage(GameCharacter otherChar) {
        left2.setText(String.valueOf(otherChar));
    }

    public void setBattleMessage(Hero hero, GameCharacter otherChar) {
        String heroMessage = hero + "  " + bonusMessage;

        left1.setText(heroMessage);

        String otherCharMessage = otherChar + "  " + moveMessage;

        left2.setText(otherCharMessage);
    }

    public void setKeyImage() {
        keyImage.setIcon(new ImageIcon("img/key.png"));
    }

    public void setKeyMessage(String moveMessage) {
        this.moveMessage = moveMessage;
    }

    public void setBonusMessage(String bonusMessage) {
        this.bonusMessage = bonusMessage;
    }

    public void clearKeyImage() {
        rightSide.remove(keyImage);
    }

}
