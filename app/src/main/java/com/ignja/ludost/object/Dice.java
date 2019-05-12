package com.ignja.ludost.object;

import com.ignja.gl.object.Object3d;
import com.ignja.gl.util.Color;
import com.ignja.gl.renderable.Cube;
import com.ignja.gl.vo.Number3d;

/**
 * Created by Ignja on 01/02/17.
 *
 */

public class Dice extends Object3d {

    private int value;

    public Dice() {
        super(new Number3d(0, 0, 0.35f));
        this.TAG = "DiceObject";
        this.object = new Cube(0.7f, Color.WHITE);
    }

    private int getValue() {
        return this.value;
    }

}
