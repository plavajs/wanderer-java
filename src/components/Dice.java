package components;

import java.util.Random;

public class Dice {
    private Random rnd;
    private int sides = 6;

    public Dice() {
        rnd = new Random();
    }

    public Dice(int sides) {
        rnd = new Random();
        this.sides = sides;
    }

    public int roll() {
        return rnd.nextInt(sides) + 1;
    }
}
