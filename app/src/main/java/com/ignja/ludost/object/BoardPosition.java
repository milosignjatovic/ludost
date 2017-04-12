package com.ignja.ludost.object;

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
        this.board = board;
    }
}