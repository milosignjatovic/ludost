package com.ignja.ludost.logic;

import android.opengl.Matrix;
import android.util.Log;

import com.ignja.ludost.object.AbstractObject;
import com.ignja.ludost.object.Board;
import com.ignja.ludost.object.Dice;
import com.ignja.ludost.object.Piece;
import com.ignja.ludost.object.Player;
import com.ignja.ludost.renderer.ObjectRenderer;

import java.util.ArrayList;

/**
 * Created by Ignja on 01/02/17.
 *
 */

public class Game extends AbstractObject {

    private Board board;

    private Player[] player;

    private Dice dice;

    public Game(Board board, Player[] player) {
        this.dice = new Dice();
        this.dice.setParent(this);
        this.board = board;
        this.board.setParent(this);
        this.player = player;
        for (Player p: player) {
            p.setParent(this);
        }
    }

    public void draw(float[] mvpMatrix, int glProgram) {
        this.board.draw(mvpMatrix, glProgram);
        this.dice.draw(mvpMatrix, glProgram);
        for (int i = 0; i < player.length; i++) {
            this.player[i].draw(mvpMatrix, glProgram);
        }
    }

    public void handleClickEvent(int screenWidth, int screenHeight, float touchX, float touchY, float[] viewMatrix, float[] projMatrix, float hAngle) {
        this.board.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projMatrix, hAngle);
        this.dice.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projMatrix, hAngle);
        for (int i = 0; i < player.length; i++) {
            this.player[i].handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projMatrix, hAngle);
        }
        ArrayList<AbstractObject> clickedObjects = getClicked();
        AbstractObject nearestHit = null;
        for (AbstractObject clickedObject: clickedObjects) {
            if (null == nearestHit) {
                nearestHit = clickedObject;
            } else {
                if (nearestHit.getClickDistance() > clickedObject.getClickDistance()) {
                    nearestHit = clickedObject;
                }
            }
        }
        Log.i("CLICKED OBJECTS: ", String.valueOf(clickedObjects));
        if (nearestHit != null) {
            Log.i("NEAREST HIT!!!: ", nearestHit.toString());
            if (nearestHit instanceof Piece) {
                //nearestHit.object = null;
                double randomPosition = Math.random() * 72;
                ((Piece) nearestHit).moveTo(this.board.getPosition((int)randomPosition));
            }
        }
    }

    private ArrayList<AbstractObject> getClicked() {
        ArrayList<AbstractObject> objects = new ArrayList<>();
        if (this.board.isClicked()) {
            objects.add(this.board);
            this.board.unclick();
        }
        if (this.dice.isClicked()) {
            objects.add(this.dice);
            this.dice.unclick();
        }
        for (Player p : player) {
            for (Piece piece : p.getPieces()) {
                if (piece.isClicked()) {
                    objects.add(piece);
                    piece.unclick();
                }
            }
        }
        return objects;
    }

}
