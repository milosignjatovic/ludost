package com.ignja.ludost.logic;

import com.ignja.ludost.object.Color;
import com.ignja.ludost.object.Piece;
import com.ignja.ludost.renderer.ObjectRenderer;

/**
 * Created by milos on 01/02/17.
 */

public class Player {

    private Piece[] piece = new Piece[4];

    public Player() {
        piece[0] = new Piece(0.6f, Color.RED_DARK);
        // TODO
        //piece[1] = new Piece();
        //piece[2] = new Piece();
        //piece[3] = new Piece();
    }

    public void draw(float[] mvpMatrix, int glProgram) {
        ObjectRenderer objectRenderer = new ObjectRenderer();
        objectRenderer.render(this.piece[0], mvpMatrix, glProgram);
        // TODO
        //objectRenderer.render(this.piece[2], mvpMatrix, glProgram);
        //objectRenderer.render(this.piece[3], mvpMatrix, glProgram);
        //objectRenderer.render(this.piece[4], mvpMatrix, glProgram);
    }

}
