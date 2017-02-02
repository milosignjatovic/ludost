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


        }, new float[]{
                0.3f, 0f, 0.5f, color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                0.2f, 0f, 0.1f, color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                0.5f, 0.1f, 0.2f, color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                0.2f, 0.2f, 0f, color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                0.4f, 0f, 0f, color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                0f, 0f, 0f, color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3]
        }, new short[] {
                0, 1, 2, 0, 2, 3, // Top
                4, 5, 6, 4, 6, 7, // Left
                8, 9, 10, 8, 10, 11, // Right
        });
    }
}
