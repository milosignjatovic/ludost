package com.ignja.gl.renderable;

import com.ignja.gl.util.Color;

/**
 * Created by Ignja on 01/02/17.

 */

public class Cuboid extends AbstractRenderable {


    public Cuboid(float a, float b, float c, float[] color) {
        super(new float[]{
                 a/2f,-b/2f, c/2f, // Front
                 a/2f, b/2f, c/2f,
                -a/2f, b/2f, c/2f,
                -a/2f,-b/2f, c/2f,

                -a/2f, -b/2f, -c/2f, // Back
                -a/2f,  b/2f, -c/2f,
                 a/2f,  b/2f, -c/2f,
                 a/2f, -b/2f, -c/2f,

                -a/2f, -b/2f,  c/2f, // Left
                -a/2f,  b/2f,  c/2f,
                -a/2f,  b/2f, -c/2f,
                -a/2f, -b/2f, -c/2f,

                 a/2f, -b/2f, -c/2f, // Right
                 a/2f,  b/2f, -c/2f,
                 a/2f,  b/2f,  c/2f,
                 a/2f, -b/2f,  c/2f,

                 a/2f,  b/2f,  c/2f, // Top
                 a/2f,  b/2f, -c/2f,
                -a/2f,  b/2f, -c/2f,
                -a/2f,  b/2f,  c/2f,

                 a/2f, -b/2f, -c/2f, // Bottom
                 a/2f, -b/2f,  c/2f,
                -a/2f, -b/2f,  c/2f,
                -a/2f, -b/2f, -c/2f,
        }, new float[]{
                // TOP?
                Color.GRAY_LIGHT[0], Color.GRAY_LIGHT[1], Color.GRAY_LIGHT[2], Color.GRAY_LIGHT[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],

                // BOTTOM?
                Color.WHITE[0], Color.WHITE[1], Color.WHITE[2], Color.WHITE[3],
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
        }, new float[]{
                //Front normal
                0f, 0f, 1f,
                0f, 0f, 1f,
                0f, 0f, 1f,
                0f, 0f, 1f,
                0f, 0f, 1f,
                0f, 0f, 1f,

                //Back normal
                0f, 0f, -1f,
                0f, 0f, -1f,
                0f, 0f, -1f,
                0f, 0f, -1f,
                0f, 0f, -1f,
                0f, 0f, -1f,

                //Left normal
                -1f, 0f, 0f,
                -1f, 0f, 0f,
                -1f, 0f, 0f,
                -1f, 0f, 0f,
                -1f, 0f, 0f,
                -1f, 0f, 0f,

                //Right normal
                1f, 0f, 0f,
                1f, 0f, 0f,
                1f, 0f, 0f,
                1f, 0f, 0f,
                1f, 0f, 0f,
                1f, 0f, 0f,

                //Top normal
                0f, 1f, 0f,
                0f, 1f, 0f,
                0f, 1f, 0f,
                0f, 1f, 0f,
                0f, 1f, 0f,
                0f, 1f, 0f,

                //Bottom normal
                0f, -1f, 0f,
                0f, -1f, 0f,
                0f, -1f, 0f,
                0f, -1f, 0f,
                0f, -1f, 0f,
                0f, -1f, 0f
        });

    }
}
