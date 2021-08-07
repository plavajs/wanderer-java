package components.graphics;

import components.characters.GameCharacter;
import components.characters.Hero;

import javax.swing.*;
import java.awt.*;

public class HUD extends JPanel {
    private int width;
    private int height;

    private JFrame frame;

    private JPanel leftSide = new JPanel();
    private JPanel centerSide = new JPanel();
    private JPanel rightSide = new JPanel();
    private JPanel bottomSide = new JPanel();

    private JLabel left1 = new JLabel();
    private JLabel middle1 = new JLabel();
    private JLabel left2 = new JLabel();
    private JLabel middle2 = new JLabel();
    private JLabel left3 = new JLabel();
    private JLabel middle3 = new JLabel();
    private JLabel keyImage = new JLabel();
    private JLabel bottom = new JLabel();

    public HUD(JFrame frame, int width) {
        this.width = width;

        height = 120;

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

        bottom.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        bottom.setText("ARROWS = move | ENTER = start battle | SPACE = strike in battle | ESC = exit battle");

        leftSide.setLayout(new GridLayout(3,1));
        centerSide.setLayout(new GridLayout(3,1));

        leftSide.add(left1, "1");
        leftSide.add(left2, "3");
        leftSide.add(left3, "3");

        centerSide.add(middle1, "1");
        centerSide.add(middle2, "2");
        centerSide.add(middle3, "3");
        rightSide.add(keyImage);
        bottomSide.add(bottom);

        leftSide.setBackground(Color.GRAY);
        centerSide.setBackground(Color.GRAY);
        rightSide.setBackground(Color.GRAY);
        bottomSide.setBackground(Color.GRAY);

        this.add(leftSide, "West");
        this.add(centerSide, "Center");
        this.add(rightSide, "East");
        this.add(bottomSide, "South");

        frame.add(this, "South");
    }

    public void setHeroMessage(Hero hero) {
        left1.setText(String.valueOf(hero));
    }

    public void setEnemyMessage(GameCharacter otherChar) {
        left2.setText(String.valueOf(otherChar));
    }

    public void setEnemyMessage(String message) {
        left2.setText(message);
    }

    public void setBattleMessage(String message) {
        left3.setText(message);
    }

    public void setHeroStrikeMessage(String message) {
        middle1.setText(message);
    }

    public void setEnemyStrikeMessage(String message) {
        middle2.setText(message);
    }

    public void setBonusMessage(String message) {
        middle3.setText(message);
    }

    public void setKeyImage() {
        keyImage.setIcon(new ImageIcon("img/key.png"));
    }

    public void clearKeyImage() {
        rightSide.remove(keyImage);
    }

}
