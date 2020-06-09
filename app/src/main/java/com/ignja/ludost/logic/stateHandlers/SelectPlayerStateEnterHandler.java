package com.ignja.ludost.logic.stateHandlers;

import com.ignja.core.util.Log;
import com.ignja.fsm.State;
import com.ignja.fsm.StateHandler;
import com.ignja.fsm.StatefulContext;

/**
 * Created by milos on 6/9/20.
 */
public class SelectPlayerStateEnterHandler<C extends StatefulContext> implements StateHandler<C> {

    String TAG = "SelectPlayerStateEnterHandler";

    @Override
    public void call(State state, StatefulContext context) throws Exception {
        Log.d(TAG, "call");
    }
}
