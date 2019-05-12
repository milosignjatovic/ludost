package com.ignja.ludost.object;

import com.ignja.gl.object.Object3d;
import com.ignja.gl.renderable.AbstractRenderable;
import com.ignja.gl.renderable.Square;
import com.ignja.gl.util.Color;
import com.ignja.gl.vo.Number3d;

/**
 * Created by milos on 4/4/17.
 *
 * Represents position on the board
 */

public class BoardPosition extends Object3d {

    private Board board;

    public BoardPosition(Board board, float x, float y) {
        super(new Number3d(x, y, 0f));
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
        }, Color.BLUE_STEEL);
    }

}