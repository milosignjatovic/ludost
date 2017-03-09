package com.ignja.ludost.logic;

import com.ignja.ludost.object.AbstractObject;
import com.ignja.ludost.object.Color;
import com.ignja.ludost.object.Square;
import com.ignja.ludost.renderer.ObjectRenderer;

/**
 * Created by milos on 01/02/17.
 */

public class Board {

    private AbstractObject object;

    public Board() {
        this.object = this.createBlueDarkSquare();
    }

    private AbstractObject createBlueDarkSquare() {
        float a = 4.0f;
        return new Square(new float[]{
                -a, a, 0.15f,
                -a, -a, 0.15f,
                a, -a, 0.15f,
                a, a, 0.15f
        }, Color.BLUE);
    }

    public void draw(float[] mvpMatrix, int glProgram) {
        ObjectRenderer objectRenderer = new ObjectRenderer();
        objectRenderer.render(this.object, mvpMatrix, glProgram);
    }

}
