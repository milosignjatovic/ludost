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

class BoardPosition extends Object3d {

    private boolean visible = false;

    BoardPosition(Board board, float x, float y) {
        super(new Number3d(x, y, 0f));
        this.TAG = "BoardPosition";
        if (visible) {
            addRenderable(this.createPositionSquare());
        }
        this.setParent(board);
    }

    /**
     * If needed, board positions could be rendered, but it's faster if they are part of board texture
     * (we need them renderable if we want to add some "effect", like Position highlighting etc. )
     * @return AbstractRenderable
     */
    private AbstractRenderable createPositionSquare() {
        float a = 0.12f;
        return new Square(new float[]{
                -a, a, 0.01f,
                -a, -a, 0.01f,
                a, -a, 0.01f,
                a, a, 0.01f
        }, Color.BLUE_STEEL);
    }

}