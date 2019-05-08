package com.ignja.ludost.object;

import com.ignja.gl.object.Object3d;
import com.ignja.gl.object.Object3dContainer;
import com.ignja.gl.object.Point;
import com.ignja.gl.util.Color;
import com.ignja.gl.renderable.Cube;

/**
 * Created by Ignja on 01/02/17.
 *
 */

public class Dice extends Object3dContainer {

    private int value;

    public Dice() {
        super(new Point(0, 0, 0.35f));
        this.TAG = "DiceObject";
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
