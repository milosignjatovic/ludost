package com.ignja.ludost.object;

import com.ignja.gl.object.Object3d;
import com.ignja.gl.renderable.Cuboid;
import com.ignja.gl.vo.Number3d;

/**
 * Created by milos on 4/4/17.
 */

public class Piece extends Object3d {

    BoardPosition boardPosition;

    Piece(BoardPosition boardPosition, float[] color) {
        super(new Number3d(boardPosition.getX(), boardPosition.getY(), 0.35f), color);
        this.TAG = "PieceObject";
        this.object = new Cuboid(0.25f, 0.25f, 0.7f, color);
        this.moveTo(boardPosition); // Not needed here?
    }

    public void moveTo(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
        this.position(new Number3d(boardPosition.getX(), boardPosition.getY(), 0.35f));
    }

}
