package com.ignja.gl.renderable;

/**
 * A two-dimensional square for use as a drawn object in OpenGL ES 2.0.
 */
public class Square extends AbstractRenderable {

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    public Square(float[] coords, float[] color) {
        super(coords, new float[]{
                0.5f, 0.5f, 0.9f, color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                color[0], color[1], color[2], color[3],
                0.7f, 0.8f, 1.0f, color[3],
                color[0], color[1], color[2], color[3]
        }, new short[]{0,1,2,0,2,3});
    }

}