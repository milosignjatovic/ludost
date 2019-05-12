package com.ignja.ludost.logic;

import android.os.SystemClock;
import android.util.Log;

import com.ignja.fsm.Event;
import com.ignja.fsm.EventHandler;
import com.ignja.fsm.FlowBuilder;
import com.ignja.fsm.State;
import com.ignja.fsm.StateHandler;
import com.ignja.fsm.StateMachineFlow;
import com.ignja.fsm.StatefulContext;
import com.ignja.fsm.UIThreadExecutor;
import com.ignja.gl.core.TextureVo;
import com.ignja.gl.object.Object3d;
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

public class Game extends StatefulContext {

    protected String TAG = "Game (Flow? Logic?)";

    private Board board;

    private Player[] player;

    public Dice dice;

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
        this.dice.setParent(board);
        this.board = board;

        TextureVo texture = new TextureVo("stonetexture");
        this.board.addTexture(texture);

        this.player = player;
        for (Player p: player) {
            p.setParent(board);
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
                onStart.to(ROLL_DICE).transit(
                        onPlayerSelected.to(ROLL_DICE).transit(
                                onDiceClick.to(SELECT_PIECE).transit(
                                    onPieceSelected.to(SELECT_PLAYER)
                                )
                        )
                ),
                onExit.to(INIT_STATE),
                onDiceClick.to(ROLL_DICE).transit(
                        onPieceSelected.to(SELECT_PLAYER)
                )
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
                // TODO
                // - check if there are available players
                // - select first available players
                // - wait for user to role the dice (timeout -> next available player)
                // - dice rolled -> animate, random number
                // - check for available pieces
                // - Info message if there are no avail pieces, or Wait for player to select piece (timeout -> next available player)
                // - piece selected -> animate -> movement result (won game?)
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
                Log.i(TAG, from + ", to: " + to + ",  Dice rolled: " + diceResult);
            }
        });

    }

    public void draw(float[] mvpMatrix, int glProgram, float[] modelViewMatrix, float[] projectionMatrix) {
        this.board.draw(mvpMatrix, glProgram, modelViewMatrix, projectionMatrix);
        this.dice.draw(mvpMatrix, glProgram, modelViewMatrix, projectionMatrix);
        for (int i = 0; i < player.length; i++) {
            this.player[i].draw(mvpMatrix, glProgram, modelViewMatrix, projectionMatrix);
        }
    }

    public void handleClickEvent(int screenWidth, int screenHeight, float touchX, float touchY, float[] viewMatrix, float[] projectionMatrix, float hAngle) {
        this.board.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
        this.dice.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
        for (int i = 0; i < player.length; i++) {
            this.player[i].handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
        }
        ArrayList<Object3d> clickedObjects = getClicked();
        Object3d nearestHit = null;
        for (Object3d clickedObject: clickedObjects) {
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
                    dice.clickedAt(SystemClock.uptimeMillis());
                } catch (Exception e) {
                    // TODO
                }
            }
        }
    }

    private ArrayList<Object3d> getClicked() {
        ArrayList<Object3d> objects = new ArrayList<>();
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
