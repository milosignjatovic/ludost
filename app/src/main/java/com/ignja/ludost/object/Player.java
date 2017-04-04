package com.ignja.ludost.object;

import com.ignja.ludost.util.Color;
import com.ignja.ludost.renderer.ObjectRenderer;

import java.util.ArrayList;

/**
 * Created by Ignja on 01/02/17.
 *
 */
public class Player extends AbstractObject {

    private ArrayList<Piece> pieces = new ArrayList<>();

    public Player() {
        super();
        this.addPiece();
        this.addPiece();
        this.addPiece();
        this.addPiece();
    }

    private void addPiece() {
        pieces.add(new Piece());
    }

    public void draw(float[] mvpMatrix, int glProgram) {
        super.draw(mvpMatrix, glProgram);
        ObjectRenderer objectRenderer = new ObjectRenderer();
        for (Piece piece : pieces) {
//            objectRenderer.render(piece, mvpMatrix, glProgram);
        }
    }

}
