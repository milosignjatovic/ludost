package com.ignja.ludost.statemachine;

import android.util.Log;

import com.ignja.ludost.util.LoggerConfig;

import java.nio.InvalidMarkException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by milos on 5/6/19.
 */
public class Event <C extends StatefulContext>{

    private StateMachine<C> fsm;

    private static final String TAG = Event.class.getSimpleName();

    private static long idCounter = 1;

    private String name;

    private Map<State, State> transitions = new HashMap<State, State>();

    private EventHandler onTriggeredHandler;

    public Event() {
        this.name = "Event_" + (idCounter++);
    }

    public Event(String name) {
        this.name = name;
    }

    protected void setStateMachine(StateMachine<C> fsm) {
        this.fsm = fsm;
    }

    public TransitionBuilder<C> to(State<C> stateTo) {
        return new TransitionBuilder<C>(this, stateTo);
    }

    protected void addTransition(State from, State to) {
        State existingTransitionState = transitions.get(from);
        if (existingTransitionState != null) {
            if (existingTransitionState == to) {
                throw new IllegalStateException("Duplicate transition[" + this + "] from " + from + " to " + to);
            } else {
                throw new IllegalStateException("Ambiguous transition[" + this + "] from " + from + " to " + to + " and " +
                        existingTransitionState);

            }
        }
        transitions.put(from, to);
    }

    public Event<C> whenTriggered(EventHandler<C> onTriggered) {
        onTriggeredHandler = onTriggered;
        return this;
    }

    private void callOnTriggered(C context, State<C> from, State<C> to) throws Exception {
        if (onTriggeredHandler != null) {
            onTriggeredHandler.call(Event.this, from, to, context);
        }
    }

    /**
     * Trigger this event on current FSM state
     * @throws Exception
     */
    public void trigger(final C context) throws Exception {
        try {
            if (null == fsm) {
                throw new IllegalStateException("Invalid Event: " + Event.this.toString() +
                        " triggered while in State: " + context.getState() + " for " + context);
            }
        } catch (IllegalStateException logicViolationError) {
            Log.e(TAG, String.format("Event has no State to map to. You must define event %s transition in map Flow!", this), logicViolationError);
            return;
        }
        if (LoggerConfig.ON) {
            Log.d(TAG, String.format("trigger %s for %s", this, context));
        }
        State fromState = fsm.getState();
        State toState = transitions.get(fromState);
        this.fsm.execute(new Runnable() {
            @Override
            public void run() {
                State<C> stateFrom = context.getState();
                State<C> stateTo = transitions.get(stateFrom);

                try {
                    if (stateTo == null) {
                        throw new IllegalStateException("Invalid Event: " + Event.this.toString() +
                                " triggered while in State: " + context.getState() + " for " + context);
                    } else {
                        callOnTriggered(context, stateFrom, stateTo);
                        fsm.callOnEventTriggered(Event.this, stateFrom, stateTo, context);
                        fsm.setCurrentState(stateTo, context);
                    }
                } catch (Exception e) {
                    // TODO
                }

            }
        });
        onTriggeredHandler.call(Event.this, fromState, toState, context);
    }

}
