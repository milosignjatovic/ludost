package com.ignja.ludost.object;

import android.os.SystemClock;

import com.ignja.core.util.Log;
import com.ignja.gl.object.Object3d;
import com.ignja.gl.renderable.Square;
import com.ignja.gl.util.Color;
import com.ignja.gl.renderable.Cube;
import com.ignja.gl.util.Shared;
import com.ignja.gl.util.Utils;
import com.ignja.gl.vo.Number3d;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ignja on 01/02/17.
 *
 */

public class Dice extends Object3d {

    private int value = 6;

    private int[] diceValue = new int[]{0, 0};

    public Dice() {
        super(new Number3d(0, 0, 0.4f));
        this.TAG = "DiceObject";
        this.addRenderable(
                new Cube(0.8f, Color.WHITE), "diceTexture"
        );

        // TODO texture is wrongly applied, sum of opposite sides should equal to 7
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

        // TODO Matematika nije dobra :/ ... na poslu u emulatoru se malo vise vrti kockica nego kod kuce na xiaomiju i ne "pogadjaju" se brojevi :/
        // TODO disable multiple click (if Dice is already rolling)

        int translateX = ThreadLocalRandom.current().nextInt(0, 5); // (min, max+1)
        int translateY = ThreadLocalRandom.current().nextInt(0, 5);; //(int)(Math.random()*9) - 4; // 6 - 1 - 4 - 3 (horizontale)
        int translateZ = 0; // komplikuje racun, ne mora da rotira po trecoj osi za sada

        // TODO Arrays are not correct (it is adopted to applied texture). When texture is fixed, fix array values as well
        int[][] diceValues = new int[][]{
            {6, 5, 4, 2}, // nije dobro zalepljena textura na kocku?... al nema veze to je "ignja" kocka (trebalo bi [6, 4, 1, 3]... kako je na dice_texture_transparent_01.png)
            {1, 5, 3, 2},
            {4, 5, 6, 2},
            {3, 5, 1, 2},
        };



        // primeti da se [x,y] koordinatama pristupa kao: diceValues[y][x] // x=ti element u y-tom redu (prvo se asocira red... tako je definisan diceValues array)


        // gledajuci matricu, koordinate trenutne vrednosti su malo kontra, [y, x]


        // starting diceCoordinate = [0, 0] //x, y

        // calculate final number:
        //       4
        //       |
        //       5
        //       |
        // X 1 - 6 - 3
        //       |
        //       2
        //       Y

        int beforeRollX = this.diceValue[1];
        int beforeRollY = this.diceValue[0];
        int beforeRollValue = diceValues[beforeRollY][beforeRollX];

        int afterRollY = (beforeRollY + translateY) % 4;
        int afterRollX = (beforeRollX + translateX) % 4;
        int afterRollValue = diceValues[afterRollY][afterRollX];
        Log.d(TAG, "BEFORE [" + beforeRollX + ", " + beforeRollY + "] = " + beforeRollValue +  " + "
                + "ROLL ["  + translateX + ", " + translateY + "] = "
                + "AFTER [" + afterRollX + ", " + afterRollY + "] = " + afterRollValue);

        this.diceValue[0] = afterRollY;
        this.diceValue[1] = afterRollX;

        this.rotateX = (float)(translateX * Math.PI / 800);
        this.rotateY = (float)(translateY * Math.PI / 800);
        this.rotateZ = (float)(translateZ * Math.PI / 800);
    }

    @Override
    protected void update() {
        super.update();
        if (this.clicked) {
            handleClickAnimation();
        };
    }

    void handleClickAnimation() {
        float dT = SystemClock.uptimeMillis() - this.clickedAt();
        float dZ = -0.000001f * dT*dT + 0.0008f * dT;
        this.translateZ(dZ);
        // TODO druga f-ja
        this.rotation().x += dT * this.rotateX;
        this.rotation().y += dT * this.rotateY;
        this.rotation().z += dT * this.rotateZ;
        if (this.getZ() <= 0.4f) {
            finishClickAnimation();
        }
    }

    void finishClickAnimation() {
        this.clicked = false;

        // normalize Z position
        this.position().z = 0.4f;

        // normalize rotation angle
        //this.rotation().x += 0 * this.rotateX;
        //this.rotation().y += 0 * this.rotateY;
        //this.rotation().z += 0 * this.rotateZ;
        this.rotation().x = (int)(this.rotation().x / 90) * 90;
        this.rotation().y = (int)(this.rotation().y / 90) * 90;
        this.rotation().z = (int)(this.rotation().z / 90) * 90;

    }
}
