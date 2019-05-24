package com.ignja.ludost.object;

import com.ignja.gl.object.Object3d;
import com.ignja.gl.renderable.AbstractRenderable;
import com.ignja.gl.util.Color;
import com.ignja.gl.renderable.Square;

import java.util.ArrayList;

/**
 * Created by Ignja on 01/02/17.
 *
 */

public class Board extends Object3d {


    private static final int POSITIONS = 8;

    private float a = 10f;

    private ArrayList<BoardPosition> boardPositions = new ArrayList<>();

    public Board() {
        super(Color.BLUE);
        this.TAG = "BoardObject";
        this.addRenderable(this.createBoardSquare(), "boardTexture");

        // start - player 1 - green
        this.boardPositions.add(0, new BoardPosition(this, -4.15f, 4.15f));
        this.boardPositions.add(1, new BoardPosition(this, -3.3f, 4.15f));
        this.boardPositions.add(2, new BoardPosition(this, -4.15f, 3.3f));
        this.boardPositions.add(3, new BoardPosition(this, -3.3f, 3.3f));

        // start - player 2 - yellow
        this.boardPositions.add(4, new BoardPosition(this, -3.3f, -4.15f));
        this.boardPositions.add(5, new BoardPosition(this, -4.15f, -4.15f));
        this.boardPositions.add(6, new BoardPosition(this, -4.15f, -3.3f));
        this.boardPositions.add(7, new BoardPosition(this, -3.3f, -3.3f));

        // start 2 - player 3 - blue
        this.boardPositions.add(8, new BoardPosition(this, 3.3f, -4.15f));
        this.boardPositions.add(9, new BoardPosition(this, 4.15f, -4.15f));
        this.boardPositions.add(10, new BoardPosition(this, 4.15f, -3.3f));
        this.boardPositions.add(11, new BoardPosition(this, 3.3f, -3.3f));

        // start 3 - player 4 - red
        this.boardPositions.add(12, new BoardPosition(this, 3.3f, 4.15f));
        this.boardPositions.add(13, new BoardPosition(this, 4.15f, 4.15f));
        this.boardPositions.add(14, new BoardPosition(this, 4.15f, 3.3f));
        this.boardPositions.add(15, new BoardPosition(this, 3.3f, 3.3f));




        // end - player 1 - green
        this.boardPositions.add(16, new BoardPosition(this, 0f, 3.362f));
        this.boardPositions.add(17, new BoardPosition(this, 0f, 2.522f));
        this.boardPositions.add(18, new BoardPosition(this, 0f, 1.682f));
        this.boardPositions.add(19, new BoardPosition(this, 0f, 0.842f));

        // end- player 3 - blue
        this.boardPositions.add(20, new BoardPosition(this, 0f, -3.32f));
        this.boardPositions.add(21, new BoardPosition(this, 0f, -2.48f));
        this.boardPositions.add(22, new BoardPosition(this, 0f, -1.64f));
        this.boardPositions.add(23, new BoardPosition(this, 0f, -0.842f));

        // end - player 2 - yellow
        this.boardPositions.add(24, new BoardPosition(this, -3.32f, 0f));
        this.boardPositions.add(25, new BoardPosition(this, -2.48f, 0f));
        this.boardPositions.add(26, new BoardPosition(this, -1.64f, 0f));
        this.boardPositions.add(27, new BoardPosition(this, -0.8f, 0f));

        // end - player 4 - red
        this.boardPositions.add(28, new BoardPosition(this, 3.362f, 0f));
        this.boardPositions.add(29, new BoardPosition(this, 2.522f, 0f));
        this.boardPositions.add(30, new BoardPosition(this, 1.682f, 0f));
        this.boardPositions.add(31, new BoardPosition(this, 0.842f, 0f));


        // road - red->green flat line
        this.boardPositions.add(32, new BoardPosition(this, -0.8f, 4.16f));
        this.boardPositions.add(33, new BoardPosition(this, 0f, 4.16f));
        this.boardPositions.add(34, new BoardPosition(this, 0.8f, 4.16f));

        // player 1 - circle start
        this.boardPositions.add(35, new BoardPosition(this, -0.8f, 3.362f));
        this.boardPositions.add(36, new BoardPosition(this, -0.8f, 2.522f));
        this.boardPositions.add(37, new BoardPosition(this, -0.8f, 1.682f));
        this.boardPositions.add(38, new BoardPosition(this, -0.8f, 0.842f));
        this.boardPositions.add(39, new BoardPosition(this, -1.64f, 0.842f));
        this.boardPositions.add(40, new BoardPosition(this, -2.48f, 0.842f));
        this.boardPositions.add(41, new BoardPosition(this, -3.32f, 0.842f));

        // green->yellow flat line
        this.boardPositions.add(42, new BoardPosition(this, -4.16f, -0.842f));
        this.boardPositions.add(43, new BoardPosition(this, -4.16f, 0f));
        this.boardPositions.add(44, new BoardPosition(this, -4.16f, 0.842f));


        // player 2 - yellow - circle start
        this.boardPositions.add(45, new BoardPosition(this, -3.32f, -0.842f));
        this.boardPositions.add(46, new BoardPosition(this, -2.48f, -0.842f));
        this.boardPositions.add(47, new BoardPosition(this, -1.64f, -0.842f));
        this.boardPositions.add(48, new BoardPosition(this, -0.842f, -0.842f));
        this.boardPositions.add(49, new BoardPosition(this, -0.842f, -1.682f));
        this.boardPositions.add(50, new BoardPosition(this, -0.842f, -2.522f));
        this.boardPositions.add(51, new BoardPosition(this, -0.842f, -3.362f));

        // yellow->blue flat line
        this.boardPositions.add(52, new BoardPosition(this, -0.842f, -4.16f));
        this.boardPositions.add(53, new BoardPosition(this, 0f, -4.16f));
        this.boardPositions.add(54, new BoardPosition(this, 0.842f, -4.16f));

        // player 3 - blue - circle start
        this.boardPositions.add(55, new BoardPosition(this, 0.842f, -3.362f));
        this.boardPositions.add(56, new BoardPosition(this, 0.842f, -2.522f));
        this.boardPositions.add(57, new BoardPosition(this, 0.842f, -1.682f));
        this.boardPositions.add(58, new BoardPosition(this, 0.842f, -0.842f));
        this.boardPositions.add(59, new BoardPosition(this, 1.64f, -0.842f));
        this.boardPositions.add(60, new BoardPosition(this, 2.48f, -0.842f));
        this.boardPositions.add(61, new BoardPosition(this, 3.32f, -0.842f));


        // blue->red flat line
        this.boardPositions.add(62, new BoardPosition(this, 4.16f, 0.8f));
        this.boardPositions.add(63, new BoardPosition(this, 4.16f, 0f));
        this.boardPositions.add(64, new BoardPosition(this, 4.16f, -0.8f));

        // player 4 - red - circle start
        this.boardPositions.add(65, new BoardPosition(this, 3.32f, 0.842f));
        this.boardPositions.add(66, new BoardPosition(this, 2.48f, 0.842f));
        this.boardPositions.add(67, new BoardPosition(this, 1.64f, 0.842f));
        this.boardPositions.add(68, new BoardPosition(this, 0.842f, 3.3626f));
        this.boardPositions.add(69, new BoardPosition(this, 0.842f, 2.522f));
        this.boardPositions.add(70, new BoardPosition(this, 0.842f, 1.682f));
        this.boardPositions.add(71, new BoardPosition(this, 0.842f, 0.842f));





    }

    private AbstractRenderable createBoardSquare() {
        return new Square(new float[]{
                -a/2, a/2, 0f,
                -a/2, -a/2, 0f,
                a/2, -a/2, 0f,
                a/2, a/2, 0f
        }, Color.WHITE);
    }

    public BoardPosition getPosition(int index) {
        return this.boardPositions.get(index);
    }

    public void draw(float[] mvpMatrix, int glProgram, float[] modelViewMatrix, float[] projectionMatrix) {
        super.draw(mvpMatrix, glProgram, modelViewMatrix, projectionMatrix);
        for (BoardPosition boardPosition: boardPositions)
        boardPosition.draw(mvpMatrix, glProgram, modelViewMatrix, projectionMatrix);
    }

    public void handleClickEvent(int screenWidth, int screenHeight, float touchX, float touchY, float[] viewMatrix, float[] projectionMatrix, float hAngle) {
        super.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
        for (BoardPosition boardPosition : boardPositions) {
            boardPosition.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
        }
    }

}
