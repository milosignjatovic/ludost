package com.ignja.ludost.logic;

import android.os.SystemClock;

import com.ignja.fsm.Event;
import com.ignja.fsm.FlowBuilder;
import com.ignja.fsm.State;
import com.ignja.fsm.StateMachineFlow;
import com.ignja.fsm.StatefulContext;
import com.ignja.fsm.UIThreadExecutor;
import com.ignja.gl.core.TextureVo;
import com.ignja.gl.object.Object3d;
import com.ignja.core.util.Log;
import com.ignja.ludost.logic.eventHandlers.DiceClickEventHandler;
import com.ignja.ludost.logic.stateHandlers.InitStateEnterHandler;
import com.ignja.ludost.logic.stateHandlers.InitStateLeaveHandler;
import com.ignja.ludost.logic.stateHandlers.RollDiceStateEnterHandler;
import com.ignja.ludost.logic.stateHandlers.SelectPlayerStateEnterHandler;
import com.ignja.ludost.object.Board;
import com.ignja.ludost.object.Dice;
import com.ignja.ludost.object.Piece;
import com.ignja.ludost.object.Player;

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

    public static class GameFlowContext extends StatefulContext {
        private String info = "Pomozi boze";
    }

    /**
     * Ludost game states
     */
    // Initial game state (show splash screen, auto transit to next state)
    private final State<GameFlowContext> INIT = FlowBuilder.state();
    private final State<GameFlowContext> START_GAME = FlowBuilder.state();
    // select next player // WAIT_FOR_OBJECT_CLICK?
    private final State<GameFlowContext> SELECT_PLAYER = FlowBuilder.state();
    // wait for dice click event...
    private final State<GameFlowContext> ROLL_DICE = FlowBuilder.state();
    // choose one of available player pieces
    private final State<GameFlowContext> SELECT_PIECE = FlowBuilder.state();

    /**
     * General events
     */
    private final Event<GameFlowContext> onSettingsIconClick = FlowBuilder.event();

    /**
     * Ludost game events
     */
    public static final Event<GameFlowContext> onStartGame = FlowBuilder.event();
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
        this.dice.addTexture(texture);

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
     * Define transitions from State1 to State2, triggered by game Events
     */
    private void initFlow() {
        if (flow != null) {
            return;
        }
        flow = FlowBuilder.from(INIT).transit(
                onStartGame.to(ROLL_DICE).transit(
                        onPlayerSelected.to(ROLL_DICE).transit(
                                onDiceClick.to(SELECT_PIECE).transit(
                                    onPieceSelected.to(SELECT_PLAYER)
                                )
                        )
                ),
                onExit.to(INIT),
                onDiceClick.to(ROLL_DICE).transit(
                        onPieceSelected.to(SELECT_PLAYER)
                )
        ).executor(new UIThreadExecutor());
    }

    private void bindFlow() {
        // bind state handlers (enter, leave)
        INIT
                .whenEnter(new InitStateEnterHandler<GameFlowContext>())
                .whenLeave(new InitStateLeaveHandler<GameFlowContext>());

        //START_GAME.whenEnter()

        SELECT_PLAYER
                .whenEnter(new SelectPlayerStateEnterHandler<GameFlowContext>());

        ROLL_DICE
                .whenEnter(new RollDiceStateEnterHandler<GameFlowContext>());

        // bind event handlers
        onDiceClick
                .whenTriggered(new DiceClickEventHandler<GameFlowContext>());

    }


    /**
     * Drawing methods down
     *
     */

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
        ArrayList<Object3d> clickedObjects = getClickedObjects();
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
        Log.i("Clicked objects:", String.valueOf(clickedObjects));
        if (nearestHit != null) {
            Log.i("Nearest hit", nearestHit.toString());
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

    private ArrayList<Object3d> getClickedObjects() {
        ArrayList<Object3d> clickedObjects = new ArrayList<>();
        if (this.board.isClicked()) {
            clickedObjects.add(this.board);
            this.board.unClick();
        }
        if (this.dice.isClicked()) {
            clickedObjects.add(this.dice);
            this.dice.unClick();
        }
        for (Player p : player) {
            for (Piece piece : p.getPieces()) {
                if (piece.isClicked()) {
                    clickedObjects.add(piece);
                    piece.unClick();
                }
            }
        }
        return clickedObjects;
    }

}
