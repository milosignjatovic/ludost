package com.ignja.ludost.object;

import com.ignja.gl.object.Object3d;

import java.util.ArrayList;

/**
 * Created by Ignja on 01/02/17.
 *
 */
public class Player extends Object3d {

    private ArrayList<Piece> pieces = new ArrayList<>();

    private Board board;

    private float[] color;

    private float[] res;

    public Player(Board board, float[] color, int index) {
        super(color);
        this.board = board;
        this.color = color;
        this.addPiece(4 * index);
        this.addPiece(4 * index + 1);
        this.addPiece(4 * index + 2);
        this.addPiece(4 * index + 3);
    }

    private void addPiece(int positionIndex) {
        Piece p = new Piece(this.board.getPosition(positionIndex), this.color);
        p.setParent(this.board);
        pieces.add(p);
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void draw(float[] mvpMatrix, int glProgram, float[] modelViewMatrix, float[] projectionMatrix) {
        super.draw(mvpMatrix, glProgram, modelViewMatrix, projectionMatrix);
        for (Piece piece : pieces) {
            piece.draw(mvpMatrix, glProgram, modelViewMatrix, projectionMatrix);
        }
    }

    public void handleClickEvent(int screenWidth, int screenHeight, float touchX, float touchY, float[] viewMatrix, float[] projectionMatrix, float hAngle) {
        for (Piece piece : pieces) {
            piece.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
        }
    }

}
