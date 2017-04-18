package com.ignja.ludost.object;

import com.ignja.ludost.renderable.AbstractRenderable;
import com.ignja.ludost.renderable.Square;
import com.ignja.ludost.util.Color;

/**
 * Created by milos on 4/4/17.
 *
 * Represents position on the board
 */

public class BoardPosition extends AbstractObject {

    Board board;

    public BoardPosition(Board board, float x, float y) {
        super(new Point(x, y), Color.GRAY_DARK);
        this.object = createBlackSquare();
        this.board = board;
    }

    private AbstractRenderable createBlackSquare() {
        float a = 0.4f;
        return new Square(new float[]{
                -a, a, 0.1f,
                -a, -a, 0.1f,
                a, -a, 0.1f,
                a, a, 0.1f
        }, Color.GRAY_DARK);
    }
}