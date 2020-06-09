package com.ignja.ludost.logic.stateHandlers;

import com.ignja.core.util.Log;
import com.ignja.fsm.State;
import com.ignja.fsm.StateHandler;
import com.ignja.fsm.StatefulContext;

/**
 * Created by milos on 6/9/20.
 */
public class InitStateLeaveHandler<C extends StatefulContext> implements StateHandler<C> {

    String TAG = "InitStateLeaveHandler";

    @Override
    public void call(State state, StatefulContext context) throws Exception {
        Log.d(TAG, "call");
    }
}
