package com.ignja.ludost.logic.eventHandlers;

import com.ignja.core.util.Log;
import com.ignja.fsm.Event;
import com.ignja.fsm.EventHandler;
import com.ignja.fsm.State;
import com.ignja.fsm.StatefulContext;

/**
 * Created by milos on 6/9/20.
 */
public class DiceClickEventHandler<C extends StatefulContext> implements EventHandler<C> {

    String TAG = "DiceClickEventHandler";

    @Override
    public void call(Event<C> event, State<C> from, State<C> to, C context) throws Exception {
        Log.d(TAG, "DiceClick");
        // TODO start dice animation here
    }

}
