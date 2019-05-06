package com.ignja.ludost.statemachine;

/**
 * Created by milos on 5/6/19.
 */
public class StateMachine<C extends StatefulContext> {

    private State<C> initialState;

    private State currentState;

    private C context;

    public StateMachine(State<C> initialState, TransitionBuilder<C>... transitions) {
        this.initialState = initialState;
        for (TransitionBuilder<C> transition : transitions) {
            initialState.addEvent(transition.getEvent(), transition.getStateTo());
        }
        //this.run();
    }

    private void run() {
        if (currentState == null) {
            currentState = initialState;
        }
    }

    public State getState() {
        return currentState;
    }

}
