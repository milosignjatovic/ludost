package com.ignja.ludost.object;

import com.ignja.ludost.util.Color;
import com.ignja.ludost.renderable.Cube;
import com.ignja.ludost.renderer.ObjectRenderer;

/**
 * Created by Ignja on 01/02/17.
 *
 */

public class Dice extends AbstractObject {

    private int value;

    public Dice() {
        super(new Point(0, 0, 0.35f));
        this.object = new Cube(0.7f, Color.WHITE);
        roll();
    }

    private int roll() {
        return 4;
    }

    private int getValue() {
        return this.value;
    }

}
