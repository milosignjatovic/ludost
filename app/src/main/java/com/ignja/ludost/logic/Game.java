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

    private Player[] players;

    public Dice dice;

    public static class GameFlowContext extends StatefulContext {
        private String info = "Pomozi boze";

        private Integer diceValue = null;

        private Player activePlayer = null;

        public Player getActivePlayer() {
            return activePlayer;
        }

        public void setActivePlayer(Player player) {
            activePlayer = player;
        }
    }

    /**
     * Ludost game states
     */
    // Initial game state (show splash screen, auto transit to next state)
    private final State<GameFlowContext> INIT = FlowBuilder.state("INIT");
    private final State<GameFlowContext> START_GAME = FlowBuilder.state("START_GAME");
    // select next player // WAIT_FOR_OBJECT_CLICK?
    private final State<GameFlowContext> SELECT_NEXT_PLAYER = FlowBuilder.state("SELECT_NEXT_PLAYER");
    // wait for dice click event...
    private final State<GameFlowContext> ROLL_DICE = FlowBuilder.state("ROLL_DICE");
    // choose one of available player pieces
    private final State<GameFlowContext> SELECT_PIECE = FlowBuilder.state("SELECT_PIECE");

    /**
     * General events
     */
    private final Event<GameFlowContext> onSettingsIconClick = FlowBuilder.event();
    public static final Event<GameFlowContext> onStartGame = FlowBuilder.event("onStartGame");
    private final Event<GameFlowContext> onExit = FlowBuilder.event("on_EXIT");

    /**
     * Ludost game events
     */
    private final Event<GameFlowContext> onPlayerSelected = FlowBuilder.event("onPlayerSelected");
    private final Event<GameFlowContext> onPieceSelected = FlowBuilder.event("onPieceSelected");
    private final Event<GameFlowContext> onDiceClick = FlowBuilder.event("onDiceClick");

    private StateMachineFlow<GameFlowContext> flow;

    public Game(Board board, Player[] players) {
        TAG = "LUDOST GAME LOGIC";
        dice = new Dice();
        dice.setParent(board);
        this.board = board;

        TextureVo texture = new TextureVo("stonetexture");
        dice.addTexture(texture);

        this.players = players;
        for (Player player: players) {
            player.setParent(board);
        }
        initFlow();
        bindFlow();
        flow.start(new GameFlowContext());
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
                onStartGame.to(SELECT_NEXT_PLAYER).transit(
                        onPlayerSelected.to(ROLL_DICE).transit(
                                onDiceClick.to(SELECT_PIECE).transit(
                                    onPieceSelected.to(SELECT_NEXT_PLAYER)
                                )
                        )
                ),
                onExit.to(INIT),
                onDiceClick.to(ROLL_DICE).transit(
                        onPieceSelected.to(SELECT_NEXT_PLAYER)
                )
        ).executor(new UIThreadExecutor());
    }

    private void bindFlow() {
        // bind state handlers (enter, leave)
        INIT
                .whenEnter(new InitStateEnterHandler<GameFlowContext>())
                .whenLeave(new InitStateLeaveHandler<GameFlowContext>());

        //START_GAME.whenEnter()

        SELECT_NEXT_PLAYER
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
        board.draw(mvpMatrix, glProgram, modelViewMatrix, projectionMatrix);
        dice.draw(mvpMatrix, glProgram, modelViewMatrix, projectionMatrix);
        for (Player p : players) {
            p.draw(mvpMatrix, glProgram, modelViewMatrix, projectionMatrix);
        }
    }

    public void handleClickEvent(int screenWidth, int screenHeight, float touchX, float touchY, float[] viewMatrix, float[] projectionMatrix, float hAngle) {
        board.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
        dice.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
        for (Player p : players) {
            p.handleClickEvent(screenWidth, screenHeight, touchX, touchY, viewMatrix, projectionMatrix, hAngle);
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
                ((Piece) nearestHit).moveTo(board.getPosition((int)randomPosition));
            } else if (nearestHit instanceof Dice) {
                try {
                    onDiceClick.trigger(flow.getContext());
                    dice.clickedAt(SystemClock.uptimeMillis());
                } catch (Exception e) {
                    // TODO
                }
            }
        }
    }

    private ArrayList<Object3d> getClickedObjects() {
        ArrayList<Object3d> clickedObjects = new ArrayList<>();
        if (board.isClicked()) {
            clickedObjects.add(board);
            board.unClick();
        }
        if (dice.isClicked()) {
            clickedObjects.add(dice);
            dice.unClick();
        }
        for (Player p : players) {
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
