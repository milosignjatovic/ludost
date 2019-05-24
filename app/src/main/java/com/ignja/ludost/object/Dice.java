package com.ignja.ludost.object;

import android.os.SystemClock;

import com.ignja.gl.object.Object3d;
import com.ignja.gl.renderable.Square;
import com.ignja.gl.util.Color;
import com.ignja.gl.renderable.Cube;
import com.ignja.gl.util.Shared;
import com.ignja.gl.util.Utils;
import com.ignja.gl.vo.Number3d;

/**
 * Created by Ignja on 01/02/17.
 *
 */

public class Dice extends Object3d {

    private int value;

    public Dice() {
        super(new Number3d(0, 0, 0.4f));
        this.TAG = "DiceObject";
        this.addRenderable(
                new Cube(0.8f, Color.WHITE), "diceTexture"
        );
        this.renderables.get(0).setTextureCoords(new float[]{
                0.25f, 0.67f, /* Top - 6 */
                0.0f, 0.67f,
                0.0f, 0.33f,
                0.25f, 0.33f,

                0.5f, 0.67f, /* Bottom - 4 */
                0.25f, 0.67f,
                0.25f, 0.33f,
                0.5f, 0.33f,

                0.75f, 0.67f, /* Left - 1 */
                0.5f, 0.67f,
                0.5f, 0.33f,
                0.75f, 0.33f,

                1.0f, 0.67f, /* Right - 3 */
                0.75f, 0.67f,
                0.75f, 0.33f,
                1.0f, 0.33f,

                0.75f, 1.0f, /* Back - 5 */
                0.5f, 1.0f,
                0.5f, 0.67f,
                0.75f, 0.67f,

                0.75f, 0.33f, /* Front - 2 */
                0.5f, 0.33f,
                0.5f, 0.0f,
                0.75f, 0.0f});
        float dotR = 0.08f;
        // CAMERA TOP 1
//        this.addRenderable(new Square(new float[]{
//                -dotR, dotR, 0.51f,
//                -dotR, -dotR, 0.51f,
//                dotR, -dotR, 0.51f,
//                dotR, dotR, 0.51f
//        }, Color.GRAY_DARK));
        // CAMERA BACK 3
//        this.addRenderable(new Square(new float[]{
//                dotR, 0.51f, dotR,
//                dotR, 0.51f, -dotR,
//                -dotR, 0.51f, -dotR,
//                -dotR, 0.51f, dotR
//        }, Color.GRAY_DARK));
//        this.addRenderable(new Square(new float[]{
//                dotR + 0.3f, 0.51f, dotR + 0.3f,
//                dotR + 0.3f, 0.51f, -dotR + 0.3f,
//                -dotR + 0.3f, 0.51f, -dotR + 0.3f,
//                -dotR + 0.3f, 0.51f, dotR + 0.3f
//        }, Color.GRAY_DARK));
//        this.addRenderable(new Square(new float[]{
//                dotR - 0.3f, 0.51f, dotR - 0.3f,
//                dotR - 0.3f, 0.51f, -dotR - 0.3f,
//                -dotR - 0.3f, 0.51f, -dotR - 0.3f,
//                -dotR - 0.3f, 0.51f, dotR - 0.3f
//        }, Color.GRAY_DARK));
    }

    private int getValue() {
        return this.value;
    }

    private float rotateX = 0f;
    private float rotateY = 0f;
    private float rotateZ = 0f;

    @Override
    public void clickedAt(long uptimeMillis) {
        super.clickedAt(uptimeMillis);
        this.rotateX = (float)((int)(Math.random()*3) * Math.PI / 800);
        this.rotateY = (float)((int)(Math.random()*3) * Math.PI / 800);
        this.rotateZ = (float)((int)(Math.random()*3) * Math.PI / 800);
    }

    @Override
    protected void update() {
        super.update();
        if (this.clicked) {
            float dT = SystemClock.uptimeMillis() - this.clickedAt();
            float dZ = -0.000002f * dT*dT + 0.0016f * dT;
            this.translateZ(dZ);
            // TODO druga f-ja
            this.rotation().x += dT * this.rotateX;
            this.rotation().y += dT * this.rotateY;
            this.rotation().z += dT * this.rotateZ;
            if (this.getZ() <= 0.4f) {
                this.clicked = false;

                // normalize Z position
                this.position().z = 0.4f;

                // normalize rotation angle
                this.rotation().x = (int)(this.rotation().x / 90) * 90;
                this.rotation().y = (int)(this.rotation().y / 90) * 90;
                this.rotation().z = (int)(this.rotation().z / 90) * 90;
            }
        };
    }
}
