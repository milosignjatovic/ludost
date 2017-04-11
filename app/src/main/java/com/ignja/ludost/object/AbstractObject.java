package com.ignja.ludost.object;

import com.ignja.ludost.renderable.AbstractRenderable;
import com.ignja.ludost.renderable.Cube;
import com.ignja.ludost.renderer.ObjectRenderer;
import com.ignja.ludost.util.Color;

import java.util.Random;

/**
 * Created by Ignja on 4/4/17.
 *
 */

abstract class AbstractObject {

    /**
     * Location in space
     */
    private Point point;

    /**
     * Renderable. TODO ArrayList?
     */
    protected AbstractRenderable object;

    /**
     * Object name
     */
    private String name = "";

    AbstractObject(float[] color) {
        Random rand = new Random();
//        this.object = new Cube(rand.nextFloat()/4 + 0.1f, Color.GRAY_LIGHT);
//        this.point = new Point(rand.nextFloat() + 0.3f, rand.nextFloat() + 0.5f);
        this.object = new Cube(0.2f, color);
        this.point = new Point(rand.nextFloat() + 1f, 0);
        this.name = "";
    }

    AbstractObject(Point point, float[] color) {
        this(color);
        this.point = point;
    }

    protected float getX() {
        return this.point.x;
    }

    protected float getY() {
        return this.point.y;
    }

    protected float getZ() {
        return this.point.z;
    }

    public void draw(float[] mvpMatrix, int glProgram) {
        ObjectRenderer objectRenderer = new ObjectRenderer();
        if (this.object != null) {
            objectRenderer.render(this.object, mvpMatrix, glProgram);
        }
    }

}
