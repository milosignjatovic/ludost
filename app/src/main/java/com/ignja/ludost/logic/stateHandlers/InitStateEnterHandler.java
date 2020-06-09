package com.ignja.ludost.logic.stateHandlers;

import com.ignja.core.util.Log;
import com.ignja.fsm.State;
import com.ignja.fsm.StateHandler;
import com.ignja.fsm.StatefulContext;
import com.ignja.ludost.logic.Game;

/**
 * Created by milos on 6/9/20.
 */
public class InitStateEnterHandler<C extends StatefulContext> implements StateHandler<C> {

    String TAG = "InitStateEnterHandler";

    @Override
    public void call(State state, StatefulContext context) throws Exception {
        // Transitional state
        // show splash screen
        // Load resources if needed
        // etc.
        // switch to START_GAME state (trigger onStartGameEvent?)

        Log.d(TAG, "call");
        Game.onStartGame.trigger((Game.GameFlowContext) context);
    }
}
