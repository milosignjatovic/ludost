package com.ignja.ludost.object;

import com.ignja.gl.object.Object3d;
import com.ignja.gl.object.Object3dContainer;
import com.ignja.gl.object.Point;
import com.ignja.gl.renderable.Cuboid;

/**
 * Created by milos on 4/4/17.

 */

public class Piece extends Object3dContainer {


    BoardPosition boardPosition;

    Piece(BoardPosition boardPosition, float[] color) {
        super(new Point(boardPosition.getX(), boardPosition.getY(), 0.35f), color);
        this.TAG = "PieceObject";
        this.object = new Cuboid(0.2f, 0.2f, 0.7f, color);
        this.moveTo(boardPosition); // Not needed here?
    }

    public void moveTo(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
        this.setPoint(new Point(boardPosition.getX(), boardPosition.getY(), 0.35f));
    }

}
