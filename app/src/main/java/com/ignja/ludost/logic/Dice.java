package com.ignja.ludost.logic;

/**
 * Created by milos on 01/02/17.
 */

public class Dice {

    private int value;

    public Dice() {
        roll();
    }

    public int roll() {
        return 4;
    }

    public int getValue() {
        return this.value;
    }

}
