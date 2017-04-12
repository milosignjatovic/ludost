package com.ignja.ludost.object;

import android.opengl.Matrix;
import android.renderscript.Matrix4f;

import com.ignja.ludost.util.Color;
import com.ignja.ludost.renderer.ObjectRenderer;

import java.util.ArrayList;

/**
 * Created by Ignja on 01/02/17.
 *
 */
public class Player extends AbstractObject {

    private ArrayList<Piece> pieces = new ArrayList<>();

    private Board board;

    private float[] color;

    private float[] res;

    public Player(Board board, float[] color) {
        super(color);
        this.board = board;
        this.color = color;
        this.addPiece(0);
        this.addPiece(1);
        this.addPiece(2);
        this.addPiece(3);
    }

    private void addPiece(int positionIndex) {
        pieces.add(new Piece(this.board.getPosition(positionIndex), this.color));
    }

    public void draw(float[] mvpMatrix, int glProgram) {
        super.draw(mvpMatrix, glProgram);
        ObjectRenderer objectRenderer = new ObjectRenderer();
        for (Piece piece : pieces) {
            Matrix.translateM(mvpMatrix, 0,
                    piece.boardPosition.getX(),
                    piece.boardPosition.getY(),
                    piece.boardPosition.getZ());
            objectRenderer.render(piece.object, mvpMatrix, glProgram);
        }
    }

}
