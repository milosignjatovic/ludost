package com.ignja.ludost.logic;

import com.ignja.ludost.object.AbstractObject;
import com.ignja.ludost.object.Color;
import com.ignja.ludost.object.Cube;
import com.ignja.ludost.renderer.ObjectRenderer;

/**
 * Created by milos on 01/02/17.
 */

public class Dice {

    private int value;

    private AbstractObject object;

    public Dice() {
        this.object = new Cube(0.3f, Color.YELLOW);
        roll();
    }

    public int roll() {
        return 4;
    }

    public int getValue() {
        return this.value;
    }

    public void draw(float[] mvpMatrix, int glProgram) {
        ObjectRenderer objectRenderer = new ObjectRenderer();
        objectRenderer.render(this.object, mvpMatrix, glProgram);
    }

}
