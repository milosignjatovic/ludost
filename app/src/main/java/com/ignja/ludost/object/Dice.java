package com.ignja.ludost.object;

import android.os.SystemClock;

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
        super(new Number3d(0, 0, 0.5f));
        this.TAG = "DiceObject";
        this.object = new Cube(1.0f, Color.ALMOND);
    }

    private int getValue() {
        return this.value;
    }

    @Override
    protected void update() {
        super.update();
        if (this.clicked) {
            float dT = SystemClock.uptimeMillis() - this.clickedAt();
            float dZ = -0.000002f * dT*dT + 0.0016f * dT;
            this.translateZ(dZ);
            if (this.getZ() <= 0.5f) {
                this.clicked = false;
                this.position().z = 0.5f;
            }
        };
    }
}
