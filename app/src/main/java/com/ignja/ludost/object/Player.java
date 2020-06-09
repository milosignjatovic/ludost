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
        addPiece(4 * index);
        addPiece(4 * index + 1);
        addPiece(4 * index + 2);
        addPiece(4 * index + 3);
    }

    private void addPiece(int positionIndex) {
        Piece p = new Piece(board.getPosition(positionIndex), color);
        p.setParent(board);
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
