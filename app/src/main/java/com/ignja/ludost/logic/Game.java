package com.ignja.ludost.logic;

import com.ignja.ludost.renderer.MyGLRenderer;

/**
 * Created by milos on 01/02/17.
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

}
