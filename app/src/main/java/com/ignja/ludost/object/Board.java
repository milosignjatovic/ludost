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
        super(Color.BLUE);
        this.object = this.createBlueDarkSquare();
        for (int i = 0; i <= POSITIONS; i++) {
            this.boardPositions.add(i, new BoardPosition(i/16f, i/16f));
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

    public BoardPosition getPosition(int index) {
        return this.boardPositions.get(index);
    }

}
