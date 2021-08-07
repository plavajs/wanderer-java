package components.graphics;

import components.characters.GameCharacter;
import components.characters.Hero;

import javax.swing.*;
import java.awt.*;

public class HUD extends JPanel {
    private int width;
    private int height;
    private JLabel x1y1 = new JLabel();
    private JLabel x2y1 = new JLabel();
    private JLabel x1y2 = new JLabel();
    private JLabel x2y2 = new JLabel();
    private JLabel x1y3 = new JLabel();
    private JLabel x2y3 = new JLabel();
    private JLabel keyImage = new JLabel();
    private JPanel rightSide = new JPanel();
    private JPanel leftSide = new JPanel();
    private String moveMessage;
    private String bonusMessage = " ";
    private JFrame frame;

    public HUD(JFrame frame, int width) {
        this.width = width;

        height = 100;

        this.frame = frame;

        setGraphics();
    }

    private void setGraphics() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));

        x1y1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        x2y1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        x1y2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        x2y2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        x1y3.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        x2y3.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));

        leftSide.setLayout(new GridLayout(3,2));

        leftSide.add(x1y1, "1");
        leftSide.add(x2y1, "2");
        leftSide.add(x1y2, "3");
        leftSide.add(x2y2, "4");
        leftSide.add(x1y3, "5");
        leftSide.add(x2y3, "6");
        leftSide.setBackground(Color.GRAY);

        keyImage.setPreferredSize(new Dimension(80, 80));

        rightSide.setBackground(Color.GRAY);
        rightSide.add(keyImage);

        this.add(rightSide, "East");
        this.add(leftSide, "Center");

        frame.add(this, "South");
    }

    public void setMessage(Hero hero) {
        String heroMessage = String.valueOf(hero);

        x1y1.setText(heroMessage);
        x1y2.setText("");
    }

    public void setMessage(Hero hero, GameCharacter otherChar) {
        String heroMessage = String.valueOf(hero);

        x1y1.setText(heroMessage);

        String otherCharMessage = otherChar + "  " + moveMessage;

        x1y2.setText(otherCharMessage);
    }

    public void setBattleMessage(Hero hero, GameCharacter otherChar) {
        String heroMessage = hero + "  " + bonusMessage;

        x1y1.setText(heroMessage);

        String otherCharMessage = otherChar + "  " + moveMessage;

        x1y2.setText(otherCharMessage);
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

//    public void clearKeyImage(Frame frame) {
//        keyImage = ;
//    }

}
