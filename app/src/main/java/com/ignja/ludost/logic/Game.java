package com.ignja.ludost.logic;

import android.util.Log;

import com.ignja.gl.core.TextureVo;
import com.ignja.gl.fsm.*;
import com.ignja.gl.object.AbstractObject;
import com.ignja.ludost.object.Board;
import com.ignja.ludost.object.Dice;
import com.ignja.ludost.object.Piece;
import com.ignja.ludost.object.Player;
import com.ignja.gl.util.LoggerConfig;

import java.util.ArrayList;

/**
 * Created by Ignja on 01/02/17.
 *
 */

public class Game extends AbstractObject {

    private Board board;

    private Player[] player;

    private Dice dice;

    private static class GameFlowContext extends StatefulContext {
        private String info = "Pomozi boze";
    }

    /**
     * Ludost game states
     */
    private final State<GameFlowContext> INIT_STATE = FlowBuilder.state();
    private final State<GameFlowContext> SELECT_PLAYER = FlowBuilder.state();
    private final State<GameFlowContext> ROLL_DICE = FlowBuilder.state();
    private final State<GameFlowContext> SELECT_PIECE = FlowBuilder.state();

    /**
     * Ludost game events
     */
    private final Event<GameFlowContext> onStart = FlowBuilder.event();
    private final Event<GameFlowContext> onExit = FlowBuilder.event();
    private final Event<GameFlowContext> onPlayerSelected = FlowBuilder.event();
    private final Event<GameFlowContext> onPieceSelected = FlowBuilder.event();
    private final Event<GameFlowContext> onDiceClick = FlowBuilder.event();

    private StateMachineFlow<GameFlowContext> flow;

    public Game(Board board, Player[] player) {
        this.TAG = "LUDOST GAME LOGIC";
        this.dice = new Dice();
        this.dice.setParent(this);
        this.board = board;
        this.board.setParent(this);

        TextureVo texture = new TextureVo("stonetexture");
        this.board.addTexture(texture);

        this.player = player;
        for (Player p: player) {
            p.setParent(this);
        }
        initFlow();
        bindFlow();
        this.flow.start(new GameFlowContext());
    }

    /**
     * Initialize FinalStateMachine
     */
    private void initFlow() {
        if (flow != null) {
            return;
        }
        flow = FlowBuilder.from(INIT_STATE).transit(
                onStart.to(SELECT_PLAYER).transit(
                        onPlayerSelected.to(ROLL_DICE).transit(
                                onDiceClick.to(SELECT_PIECE).transit(
                                    onPieceSelected.to(SELECT_PLAYER)
                                )
                        )
                ),
                onExit.to(INIT_STATE)
        ).executor(new UIThreadExecutor());
    }

    private void bindFlow() {
        INIT_STATE.whenEnter(new StateHandler<GameFlowContext>() {
            @Override
            public void call(State<GameFlowContext> state, GameFlowContext context) throws Exception {
                Log.i(TAG, "INIT_STATE entered");
                onStart.trigger(context);
            }
        }).whenLeave(new StateHandler<GameFlowContext>() {
            @Override
            public void call(State<GameFlowContext> state, GameFlowContext context) throws Exception {
                Log.i(TAG, "INIT_STATE exited");
            }
        });
        SELECT_PLAYER.whenEnter(new StateHandler<GameFlowContext>() {
            @Override
            public void call(State<GameFlowContext> state, GameFlowContext context) throws Exception {
                Log.i(TAG, "SELECT PLAYER entered");
            }
        });
        ROLL_DICE.whenEnter(new StateHandler<GameFlowContext>() {
            @Override
            public void call(State<GameFlowContext> state, GameFlowContext context) throws Exception {
                Log.i(TAG, "ROLL_DICE entered");
            }
        });
        onDiceClick.whenTriggered(new EventHandler<GameFlowContext>() {
            @Override
            public void call(Event<GameFlowContext> event, State<GameFlowContext> from, State<GameFlowContext> to, GameFlowContext context) throws Exception {
                int diceResult = (int)(Math.random()*6) + 1;
                Log.i(TAG, "Dice rolled: " + diceResult);
            }
        });

    }

    public void draw(float[] mvpMatrix, int glProgram) {
        this.board.draw(mvpMatrix, glProgram);
        this.dice.draw(mvpMatrix, glProgram);
        for (int i = 0; i < player.length; i++) {
            this.player[i].draw(mvpMatrix, glProgram);
        }
    }

    public void handleClickEvent(int screenWidth, int screenHeight, float touchX, float touchY, float[] viewMatrix, float[] projectionMatrix, float hAngle) {
        this.board.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
        this.dice.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
        for (int i = 0; i < player.length; i++) {
            this.player[i].handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
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
        if (LoggerConfig.ON) {
            Log.i("Clicked objects", String.valueOf(clickedObjects));
        }
        if (nearestHit != null) {
            if (LoggerConfig.ON) {
                Log.i("Nearest hit", nearestHit.toString());
            }
            if (nearestHit instanceof Piece) {
                double randomPosition = Math.random() * 72;
                ((Piece) nearestHit).moveTo(this.board.getPosition((int)randomPosition));
            } else if (nearestHit instanceof Dice) {
                try {
                    onDiceClick.trigger(this.flow.getContext());
                } catch (Exception e) {
                    // TODO
                }
            }
        }
    }

    private ArrayList<AbstractObject> getClicked() {
        ArrayList<AbstractObject> objects = new ArrayList<>();
        if (this.board.isClicked()) {
            objects.add(this.board);
            this.board.unClick();
        }
        if (this.dice.isClicked()) {
            objects.add(this.dice);
            this.dice.unClick();
        }
        for (Player p : player) {
            for (Piece piece : p.getPieces()) {
                if (piece.isClicked()) {
                    objects.add(piece);
                    piece.unClick();
                }
            }
        }
        return objects;
    }

}
