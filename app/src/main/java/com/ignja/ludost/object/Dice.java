package com.ignja.ludost.object;

import android.os.SystemClock;

import com.ignja.core.util.Log;
import com.ignja.gl.object.Object3d;
import com.ignja.gl.util.Color;
import com.ignja.gl.renderable.Cube;
import com.ignja.gl.vo.Number3d;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ignja on 01/02/17.
 *
 */

public class Dice extends Object3d {

    // todo Add Dice Random Initial position

    private int[][][] diceValues = new int[4][4][4];

    // clickedAt dice rotation angle (starting position)
    private float startingRotX;
    private float startingRotY;
    private float startingRotZ;

    public Dice() {
        super(new Number3d(0, 0, 0.4f));
        TAG = "DiceObject";
        addRenderable(
                new Cube(0.8f, Color.WHITE), "diceTexture"
        );
        setCubeTexture();

        initDiceValues();

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

    private void initDiceValues() {
        diceValues[0][0][0] = 6;
        diceValues[0][0][1] = 6;
        diceValues[0][0][2] = 6;
        diceValues[0][0][3] = 6;
        diceValues[2][2][0] = 6;
        diceValues[2][2][1] = 6;
        diceValues[2][2][2] = 6;
        diceValues[2][2][3] = 6;

        diceValues[0][1][0] = 4;
        diceValues[0][3][2] = 4;
        diceValues[1][0][3] = 4;
        diceValues[1][1][3] = 4;
        diceValues[1][2][3] = 4;
        diceValues[1][3][3] = 4;
        diceValues[2][3][0] = 4;
        diceValues[2][1][2] = 4;
        diceValues[3][0][1] = 4;
        diceValues[3][1][1] = 4;
        diceValues[3][2][1] = 4;
        diceValues[3][3][1] = 4;

        diceValues[0][2][0] = 1;
        diceValues[0][2][1] = 1;
        diceValues[0][2][2] = 1;
        diceValues[0][2][3] = 1;
        diceValues[2][0][0] = 1;
        diceValues[2][0][1] = 1;
        diceValues[2][0][2] = 1;
        diceValues[2][0][3] = 1;

        diceValues[0][1][2] = 3;
        diceValues[0][3][0] = 3;
        diceValues[1][0][1] = 3;
        diceValues[1][1][1] = 3;
        diceValues[1][2][1] = 3;
        diceValues[1][3][1] = 3;
        diceValues[2][3][2] = 3;
        diceValues[3][0][3] = 3;
        diceValues[3][1][3] = 3;
        diceValues[3][2][3] = 3;
        diceValues[3][3][3] = 3;
        diceValues[2][1][0] = 3;

        diceValues[0][1][3] = 2;
        diceValues[0][3][1] = 2;
        diceValues[1][0][2] = 2;
        diceValues[1][1][2] = 2;
        diceValues[1][2][2] = 2;
        diceValues[1][3][2] = 2;
        diceValues[2][1][1] = 2;
        diceValues[2][3][3] = 2;
        diceValues[3][0][0] = 2;
        diceValues[3][1][0] = 2;
        diceValues[3][2][0] = 2;
        diceValues[3][3][0] = 2;

        diceValues[0][1][1] = 5;
        diceValues[0][3][3] = 5;
        diceValues[1][0][0] = 5;
        diceValues[1][1][0] = 5;
        diceValues[1][2][0] = 5;
        diceValues[1][3][0] = 5;
        diceValues[2][1][3] = 5;
        diceValues[2][3][1] = 5;
        diceValues[3][0][2] = 5;
        diceValues[3][1][2] = 5;
        diceValues[3][2][2] = 5;
        diceValues[3][3][2] = 5;
    }

    private void setCubeTexture() {
        renderables.get(0).setTextureCoords(new float[]{
                0.25f, 0.67f, /* Top - 6 */
                0.0f, 0.67f,
                0.0f, 0.33f,
                0.25f, 0.33f,

                0.75f, 0.67f, /* Bottom - 1 */
                0.5f, 0.67f,
                0.5f, 0.33f,
                0.75f, 0.33f,

                0.5f, 0.67f, /* Left- 4 */
                0.25f, 0.67f,
                0.25f, 0.33f,
                0.5f, 0.33f,

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
    }

    // dice roll (requested/move) rotation
    private float rotateX = 0f;
    private float rotateY = 0f;
    private float rotateZ = 0f;

    @Override
    public void clickedAt(long uptimeMillis) {
        super.clickedAt(uptimeMillis);
        startingRotX = rotation().x;
        startingRotY = rotation().y;
        startingRotZ = rotation().z;

        // TODO Matematika nije dobra :/ ... na poslu u emulatoru se malo vise vrti kockica nego kod kuce na xiaomiju i ne "pogadjaju" se brojevi :/
        // TODO disable multiple click (if Dice is already rolling)

        // roll rotation count
        int translateX = ThreadLocalRandom.current().nextInt(-4, 5); // (min, max+1)
        int translateY = ThreadLocalRandom.current().nextInt(-4, 5);
        int translateZ = ThreadLocalRandom.current().nextInt(-4, 5);

        // requested roll rotation angle (degrees)
        // (radian angle, count * 90deg : 180deg = Xrad : PI -> Xrad = count * PI / 2 )
        rotateX = (float)(translateX * 90);
        rotateY = (float)(translateY * 90);
        rotateZ = (float)(translateZ * 90);
    }

    @Override
    protected void update() {
        super.update();
        if (clicked) {
            handleClickAnimation();
        };
    }

    void handleClickAnimation() {
        float dT = SystemClock.uptimeMillis() - clickedAt(); //ms
        // kvadratna f-ja skoka kockice, 0-le su [0ms, 800ms/1200ms]
        float dZ = -0.000001f * dT*dT + 0.0008f * dT;
        translateZ(dZ);
        // TODO druga f-ja?
        rotation().x = startingRotX + (dT / 1200) * rotateX; // normalized to flight time (~1200ms)
        rotation().y = startingRotY + (dT / 1200) * rotateY;
        rotation().z = startingRotZ + (dT / 1200) * rotateZ;

        if (getZ() <= 0.4f) {
            finishClickAnimation();
        }
    }

    void finishClickAnimation() {
        clicked = false;

        // normalize Z position
        position().z = 0.4f;

        // normalize rotation angle
        // (normalized on 90 degrees (to ensure dice is landed correctly after roll))
        // (normalize to 0..360 degrees range since we have negative rotations (opposite direction))
        // (to simplify final value calculation)
        this.rotation().x = (Math.round(this.rotation().x / 90) * 90 + 360) % 360;
        this.rotation().y = (Math.round(this.rotation().y / 90) * 90 + 360) % 360;
        this.rotation().z = (Math.round(this.rotation().z / 90) * 90 + 360) % 360;

        Log.i(TAG, "Dice rolled: " + getDiceValue());

        if (getDiceValue() == 6) {
            this.renderables.clear();
            addRenderable(
                    new Cube(0.8f, getRandomColor()), "diceTexture"
            );
            setCubeTexture();
        }
    }

    private float[] getRandomColor() {
        float[] color;
        switch (ThreadLocalRandom.current().nextInt(0, 10)) {
            case 0:
                color = Color.ALMOND;
                break;
            case 1:
                color = Color.YELLOW;
                break;
            case 2:
                color = Color.BLUE_STEEL;
                break;
            case 3:
                color = Color.BLUE_LIGHT;
                break;
            case 4:
                color = Color.GREEN_PALE;
                break;
            case 5:
                color = Color.GRAY_LIGHT;
                break;
            case 6:
                color = Color.BLUE_NAVY;
                break;
            case 7:
                color = Color.RED_DARK;
                break;
            case 8:
                color = Color.GRAY_DARK;
                break;
            default:
                color = Color.WHITE;
        }
        return color;
    }

    /**
     * Read (Calculate) Dice Top value based on rotation angle
     * starting position is
     * - 6 is on top
     *
     */
    public int getDiceValue() {
        // normalize rotation angle to array index
        // [0, 90, 180, 270] -> [0, 1, 2, 3]
        int x = (int) (rotation().x/90);
        int y = (int) (rotation().y/90);
        int z = (int) (rotation().z/90);

        return diceValues[x][y][z];
    }

}
