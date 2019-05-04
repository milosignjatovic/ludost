package com.ignja.ludost.logic;

import com.ignja.ludost.object.Board;
import com.ignja.ludost.object.Dice;
import com.ignja.ludost.object.Player;

/**
 * Created by Ignja on 01/02/17.
 *
 */

public class Game {

    private Board board;

    private Player[] player;

    private Dice dice;

    public Game(Board board, Player[] player) {
        this.dice = new Dice();
        this.board = board;
        this.player = player;
    }

    public void draw(float[] mvpMatrix, int glProgram) {
        this.board.draw(mvpMatrix, glProgram);
        this.dice.draw(mvpMatrix, glProgram);
        for (int i = 0; i < player.length; i++) {
            this.player[i].draw(mvpMatrix, glProgram);
        }
    }

    public void handleClickEvent(int screenWidth, int screenHeight, float touchX, float touchY, float[] viewMatrix, float[] projMatrix) {
        this.board.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projMatrix);
        this.dice.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projMatrix);
        for (int i = 0; i < player.length; i++) {
            this.player[i].handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projMatrix);
        }
    }
}
