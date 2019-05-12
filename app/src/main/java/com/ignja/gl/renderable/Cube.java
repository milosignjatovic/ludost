package com.ignja.gl.renderable;

/**
 * Created by Milos on 01/02/17.

 */

public class Cube extends AbstractRenderable {


    public Cube(float a, float[] color) {
        super(new float[]{
                -a/2f, a/2f, a/2f, // TOP
                -a/2f, -a/2f, a/2f,
                a/2f, -a/2f, a/2f,
                a/2f, a/2f, a/2f,

                -a/2f, a/2f, a/2f, // LEFT
                -a/2f, a/2f, -a/2f,
                -a/2f, -a/2f, -a/2f,
                -a/2f, -a/2f, a/2f,

                a/2f, a/2f, a/2f, // RIGHT
                a/2f, a/2f, -a/2f,
                a/2f, -a/2f, -a/2f,
                a/2f, -a/2f, a/2f,

                a/2f, a/2f, -a/2f, // BOTTOM
                a/2f, -a/2f, -a/2f,
                -a/2f, -a/2f, -a/2f,
                -a/2f, a/2f, -a/2f,

                a/2f, -a/2f, a/2f, // FRONT
                a/2f, -a/2f, -a/2f,
                -a/2f, -a/2f, -a/2f,
                -a/2f, -a/2f, a/2f,

                a/2f, a/2f, a/2f, // REAR
                a/2f, a/2f, -a/2f,
                -a/2f, a/2f, -a/2f,
                -a/2f, a/2f, a/2f,


        }, new float[]{
                1.0f, color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                1.0f, color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                color[0], 0.3f, color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], 0.6f, color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                color[0], color[1], 0.3f, color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], 0.6f, color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                color[0], color[1], color[2], color[3],
                0.4f, color[1], 0.6f, color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                0.6f, color[1], 0.4f, color[3],
                color[0], color[1], color[2], color[3],

                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                color[0], color[1], color[2], color[3],
                color[0], 0.0f, color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], 0, color[2], color[3],
                color[0], color[1], color[2], color[3]
        }, new short[] {
                0, 1, 2, 0, 2, 3, // Top
                4, 5, 6, 4, 6, 7, // Left
                8, 9, 10, 8, 10, 11, // Right
                12, 13, 14, 12, 14, 15, // Bottom
                16, 17, 18, 16, 18, 19, // Front
                20, 21, 22, 20, 22, 23, // Rear
        });
    }
}
