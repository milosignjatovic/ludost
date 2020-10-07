package com.ignja.ludost.logic.stateHandlers;

import com.ignja.core.util.Log;
import com.ignja.fsm.State;
import com.ignja.fsm.StateHandler;
import com.ignja.fsm.StatefulContext;

/**
 * Created by milos on 6/9/20.
 */
public class RollDiceStateEnterHandler<C extends StatefulContext> implements StateHandler<C> {

    String TAG = "RollDiceStateEnterHandler";

    @Override
    public void call(State state, StatefulContext context) throws Exception {
        Log.d(TAG, "call");

        // TODO highlight the dice in current player color
        // TODO wait for user interaction (click Dice)
        // highlight clickable items (in this case only dice)
        // onClick ->
        //   enter DiceIsRolling state
        // onDiceDrop ->
        //   if there are possible user actions (clickable items)
        //     highlight clickable items (pieces)
        //     (+ highlight end positions)
        //     wait for user action (click)
        //     onPieceClick ->
        //       move piece to end position
        //       (+ if possible )
        //     enterEndPlayerMoveState
        //   else
        //     no available actions state
        //     nextPlayer
        //   GameStatusCheck... if game finished -> EndGame
        //  nextPlayer
    }
}
