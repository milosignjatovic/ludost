package com.ignja.ludost.object;

import com.ignja.ludost.renderable.AbstractRenderable;
import com.ignja.ludost.util.Color;
import com.ignja.ludost.renderable.Square;
import com.ignja.ludost.renderer.ObjectRenderer;

import java.util.ArrayList;

/**
 * Created by Ignja on 01/02/17.
 *
 */

public class Board extends AbstractObject {

    private static final int POSITIONS = 8;

    private ArrayList<BoardPosition> boardPositions = new ArrayList<>();

    public Board() {
        super();
        this.object = this.createBlueDarkSquare();
        for (int i = 1; i <= POSITIONS; i++) {
            this.boardPositions.add(new BoardPosition(i/5, i/5));
        }
    }

    private AbstractRenderable createBlueDarkSquare() {
        float a = 4.0f;
        return new Square(new float[]{
                -a, a, 0.15f,
                -a, -a, 0.15f,
                a, -a, 0.15f,
                a, a, 0.15f
        }, Color.BLUE);
    }

}
