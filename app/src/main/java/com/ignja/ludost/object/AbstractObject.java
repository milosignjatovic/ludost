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

    AbstractObject() {
        Random rand = new Random();
        this.object = new Cube(rand.nextFloat()/3, Color.GRAY_LIGHT);
        this.point = new Point(2 * (rand.nextFloat() - 0.5f), 2 * (rand.nextFloat() - 0.5f));
        this.name = "";
    }

    AbstractObject(Point point) {
        this();
        this.point = point;
    }

    AbstractObject(Point point, AbstractRenderable object) {
        this(point);
        this.object = object;
    }

    AbstractObject(Point point, AbstractRenderable object, String name) {
        this(point, object);
        this.name = name;
    }

    public void draw(float[] mvpMatrix, int glProgram) {
        ObjectRenderer objectRenderer = new ObjectRenderer();
        if (this.object != null) {
            objectRenderer.render(this.object, mvpMatrix, glProgram);
        }
    }

}
