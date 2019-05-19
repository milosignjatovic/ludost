package com.ignja.ludost.object;

import android.os.SystemClock;

import com.ignja.gl.object.Object3d;
import com.ignja.gl.renderable.Square;
import com.ignja.gl.util.Color;
import com.ignja.gl.renderable.Cube;
import com.ignja.gl.util.Utils;
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
        this.addRenderable(new Cube(1.0f, Color.ALMOND));
        float dotR = 0.08f;
        // CAMERA TOP 1
        this.addRenderable(new Square(new float[]{
                -dotR, dotR, 0.51f,
                -dotR, -dotR, 0.51f,
                dotR, -dotR, 0.51f,
                dotR, dotR, 0.51f
        }, Color.GRAY_DARK));
        // CAMERA BACK 3
        this.addRenderable(new Square(new float[]{
                dotR, 0.51f, dotR,
                dotR, 0.51f, -dotR,
                -dotR, 0.51f, -dotR,
                -dotR, 0.51f, dotR
        }, Color.GRAY_DARK));
        this.addRenderable(new Square(new float[]{
                dotR + 0.3f, 0.51f, dotR + 0.3f,
                dotR + 0.3f, 0.51f, -dotR + 0.3f,
                -dotR + 0.3f, 0.51f, -dotR + 0.3f,
                -dotR + 0.3f, 0.51f, dotR + 0.3f
        }, Color.GRAY_DARK));
        this.addRenderable(new Square(new float[]{
                dotR - 0.3f, 0.51f, dotR - 0.3f,
                dotR - 0.3f, 0.51f, -dotR - 0.3f,
                -dotR - 0.3f, 0.51f, -dotR - 0.3f,
                -dotR - 0.3f, 0.51f, dotR - 0.3f
        }, Color.GRAY_DARK));
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
            // TODO druga f-ja
            this.rotation().x += dZ / Utils.DEG;
            this.rotation().y += dZ * 1.5 / Utils.DEG;
            this.rotation().z += dZ * 1.5/ Utils.DEG;
            if (this.getZ() <= 0.5f) {
                this.clicked = false;
                this.position().z = 0.5f;
                this.rotation().x = 0;
                this.rotation().y = 0;
                this.rotation().z = 0;
            }
        };
    }
}
