package com.ignja.gl.renderable;

/**
 * Created by Milos on 01/02/17.

 */

public class Cube extends AbstractRenderable {


    public Cube(float a, float[] color) {
        super(new float[]{
                 a/2f,-a/2f, a/2f, // Front
                 a/2f, a/2f, a/2f,
                -a/2f, a/2f, a/2f,
                -a/2f,-a/2f, a/2f,

                -a/2f, -a/2f, -a/2f, // Back
                -a/2f,  a/2f, -a/2f,
                 a/2f,  a/2f, -a/2f,
                 a/2f, -a/2f, -a/2f,

                -a/2f, -a/2f,  a/2f, // Left
                -a/2f,  a/2f,  a/2f,
                -a/2f,  a/2f, -a/2f,
                -a/2f, -a/2f, -a/2f,

                 a/2f, -a/2f, -a/2f, // Right
                 a/2f,  a/2f, -a/2f,
                 a/2f,  a/2f,  a/2f,
                 a/2f, -a/2f,  a/2f,

                 a/2f,  a/2f,  a/2f, // Top
                 a/2f,  a/2f, -a/2f,
                -a/2f,  a/2f, -a/2f,
                -a/2f,  a/2f,  a/2f,

                 a/2f, -a/2f, -a/2f, // Bottom
                 a/2f, -a/2f,  a/2f,
                -a/2f, -a/2f,  a/2f,
                -a/2f, -a/2f, -a/2f,


        }, new float[]{
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3]
        }, new short[] {
                // Front
                0, 1, 2,
                2, 3, 0,

                // Back
                4, 5, 6,
                6, 7, 4,

                // Left
                8, 9, 10,
                10, 11, 8,

                // Right
                12, 13, 14,
                14, 15, 12,

                // Top
                16, 17, 18,
                18, 19, 16,

                // Bottom
                20, 21, 22,
                22, 23, 20
        });
    }
}
