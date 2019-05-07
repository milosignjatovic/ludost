package com.ignja.ludost.object;

import com.ignja.gles.renderable.AbstractRenderable;
import com.ignja.gles.renderable.Square;
import com.ignja.ludost.util.Color;

/**
 * Created by milos on 4/4/17.
 *
 * Represents position on the board
 */

public class BoardPosition extends AbstractObject {

    private Board board;

    public BoardPosition(Board board, float x, float y) {
        super(new Point(x, y));
        this.TAG = "BoardPosition";
        this.object = createBlackSquare();
        this.setParent(board);
    }

    private AbstractRenderable createBlackSquare() {
        float a = 0.22f;
        return new Square(new float[]{
                -a, a, 0.01f,
                -a, -a, 0.01f,
                a, -a, 0.01f,
                a, a, 0.01f
        }, Color.GRAY_DARK);
    }

}