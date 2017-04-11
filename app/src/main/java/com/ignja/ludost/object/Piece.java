package com.ignja.ludost.object;

import com.ignja.ludost.renderable.AbstractRenderable;

/**
 * Created by milos on 4/4/17.

 */

public class Piece extends AbstractObject {

    BoardPosition boardPosition;

    Piece(BoardPosition boardPosition, float[] color) {
        super(new Point(boardPosition.getX(), boardPosition.getY()), color);
        this.moveTo(boardPosition); // Not needed here?
    }

    public void moveTo(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
    }

}
