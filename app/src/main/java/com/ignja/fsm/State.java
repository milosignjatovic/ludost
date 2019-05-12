package com.ignja.fsm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by milos on 5/6/19.
 */
public class State<C extends StatefulContext> {

    private static final String TAG = State.class.getSimpleName();
    private static long idCounter = 1;
    private boolean isFinal = false;
    private String name;
    private Map<Event, State> transitions = new HashMap<Event, State>();

    private StateMachineFlow<C> runner;

    private StateHandler<C> onEnterHandler;
    private StateHandler<C> onLeaveHandler;

    public State() {
        this.name = "State_" + (idCounter++);
    }

    public State(String name) {
        this.name = name;
    }

    public State<C> whenEnter(StateHandler<C> onEnterHandler) {
        this.onEnterHandler = onEnterHandler;
        return this;
    }

    public State<C> whenLeave(StateHandler<C> onLeaveHandler) {
        this.onLeaveHandler = onLeaveHandler;
        return this;
    }

    protected void addEvent(Event event, State stateTo) {
        transitions.put(event, stateTo);
    }

    public boolean hasEvent(Event event) {
        return transitions.containsKey(event);
    }

    public boolean hasTransitionTo(State state) {
        return transitions.containsValue(state);
    }

    public Map<Event, State> getTransitions() {
        return transitions;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal() {
        this.isFinal = true;
    }

    public String toString() {
        return name;
    }

    protected void enter(final C context) {
        try {
            onEnterHandler.call(State.this, context);
        } catch (Exception e) {
            // TODO
        }
    }

    protected void leave(final C context) {
        try {
            onLeaveHandler.call(State.this, context);
        } catch (Exception e) {
            // TODO
        }
    }

    protected void setStateMachine(StateMachineFlow<C> runner) {
        this.runner = runner;
        for (Event<C> event : transitions.keySet()) {
            event.setStateMachine(runner);
        }
        for (State<C> nextState : transitions.values()) {
            if (nextState.runner == null) {
                nextState.setStateMachine(runner);
            }
        }
    }
}
