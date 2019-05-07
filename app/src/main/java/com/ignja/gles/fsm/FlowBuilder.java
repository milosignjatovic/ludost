package com.ignja.gles.fsm;

import java.util.concurrent.Executor;

/**
 * Created by milos on 5/6/19.
 */
public class FlowBuilder<C extends StatefulContext> {
    private State<C> startState;

    private Executor executor;

    protected FlowBuilder(State<C> startState) {
        this.startState = startState;
    }

    public static <C extends StatefulContext> FlowBuilder<C> from(State<C> startState) {
        return new FlowBuilder<C>(startState);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public StateMachine<C> transit(TransitionBuilder... transitions) {
        for (TransitionBuilder<C> transition : transitions) {
            transition.getEvent().addTransition(startState, transition.getStateTo());
        }

        return new StateMachine<C>(startState, transitions);
    }

    public static <C extends StatefulContext> Event<C> event(String name) {
        return new Event<C>(name);
    }

    public static <C extends StatefulContext> Event<C> event() {
        return new Event<C>();
    }

    public static <C extends StatefulContext> State<C> state(String name) {
        return new State<C>(name);
    }

    public static <C extends StatefulContext> State<C> state() {
        return new State<C>();
    }

    protected void execute(Runnable task) {
        executor.execute(task);
    }
}
