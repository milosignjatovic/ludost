package com.ignja.ludost.object;

/**
 * Created by milos on 01/02/17.
 */

public class Cube extends AbstractObject {


    public Cube(float a, float[] color) {
        super(new float[]{
                a/2f, a/2f, a/2f, // TOP
                a/2f, -a/2f, a/2f,
                -a/2f, -a/2f, a/2f,
                -a/2f, a/2f, a/2f,

                -a/2f, a/2f, a/2f, // LEFT
                -a/2f, a/2f, -a/2f,
                -a/2f, -a/2f, -a/2f,
                -a/2f, -a/2f, a/2f,

                a/2f, a/2f, a/2f, // RIGHT
                a/2f, a/2f, -a/2f,
                a/2f, -a/2f, -a/2f,
                a/2f, -a/2f, a/2f,


        }, color, new short[] {
                0, 1, 2, 0, 2, 3, // Top
                4, 5, 6, 4, 6, 7, // Left
                8, 9, 10, 8, 10, 11, // Right
        });
    }
}
