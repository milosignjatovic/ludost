package com.ignja.ludost.statemachine;

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

    private StateHandler onEnterHandler;
    private StateHandler onLeaveHandler;

    public State() {
        this.name = "State+" + (idCounter++);
    }

    public State(String name) {
        this.name = name;
    }

    public State whenEnter(StateHandler onEnterHandler) {
        this.onEnterHandler = onEnterHandler;
        return this;
    }

    public State whenLeave(StateHandler onLeaveHandler) {
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

    protected void enter() throws Exception {
        onEnterHandler.call(this);
    }

    protected void leave() throws Exception {
        // TODO try catch
        onLeaveHandler.call(this);
    }
}
