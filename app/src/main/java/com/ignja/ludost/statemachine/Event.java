package com.ignja.ludost.statemachine;

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

    public Event whenTriggered(EventHandler onTriggered) {
        onTriggeredHandler = onTriggered;
        return this;
    }

    /**
     * Trigger this evenr on current FSM state
     * @throws Exception
     */
    public void trigger() throws Exception {
        State fromState = fsm.getState();
        State toState = transitions.get(fromState);
        onTriggeredHandler.call(this, fromState, toState);
    }

}
