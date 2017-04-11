package com.ignja.ludost.object;

import com.ignja.ludost.util.Color;

/**
 * Created by milos on 4/4/17.

 */

public class BoardPosition extends AbstractObject {

    public BoardPosition(float x, float y) {
        super(new Point(x, y), Color.GRAY_DARK);
    }
}