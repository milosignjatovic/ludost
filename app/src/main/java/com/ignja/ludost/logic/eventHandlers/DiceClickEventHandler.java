package com.ignja.ludost.logic.eventHandlers;

import com.ignja.core.util.Log;
import com.ignja.fsm.Event;
import com.ignja.fsm.EventHandler;
import com.ignja.fsm.State;
import com.ignja.fsm.StatefulContext;
import com.ignja.ludost.logic.Game;
import com.ignja.ludost.object.Player;

/**
 * Created by milos on 6/9/20.
 */
public class DiceClickEventHandler<C extends StatefulContext> implements EventHandler<C> {

    String TAG = "DiceClickEventHandler";

    @Override
    public void call(Event<C> event, State<C> from, State<C> to, C context) throws Exception {
        Log.d(TAG, "DiceClick" + " "  + event);
        Player activePlayer = ((Game.GameFlowContext) context).getActivePlayer();
        ((Game.GameFlowContext) context).setActivePlayer(activePlayer);
        // TODO Trigger roll dice animation here
        // TODO Set current dice value after roll
        // TODO based on dice results, transit to next state?
        // (CHECK FOR AVAILABLE PIECES, then SELECT_PIECE)
    }

}
