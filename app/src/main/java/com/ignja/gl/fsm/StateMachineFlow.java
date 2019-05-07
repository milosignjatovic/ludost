package com.ignja.gl.fsm;

import android.util.Log;

import com.ignja.gl.util.LoggerConfig;

import java.util.concurrent.Executor;

/**
 * Created by milos on 5/6/19.
 */
public class StateMachineFlow<C extends StatefulContext> {

    private static final String TAG = StateMachineFlow.class.getSimpleName();

    private State<C> initialState;

    private State currentState;

    private Executor executor;

    private C context;

    private EventHandler<C> onEventTriggeredHandler;

    public StateMachineFlow(State<C> initialState, TransitionBuilder<C>... transitions) {
        this.initialState = initialState;
        for (TransitionBuilder<C> transition : transitions) {
            initialState.addEvent(transition.getEvent(), transition.getStateTo());
        }
    }

    public C getContext() {
        return context;
    }

    private void prepare() {
        initialState.setStateMachine(this);
        if (executor == null) {
            executor = new AsyncExecutor();
        }

//        if (onError == null) {
//            onError = new DefaultErrorHandler();
//        }
    }

    public void start(final C context) {
        // validate ... Implement logic validator
        prepare();
        this.context = context;
        if (context.getState() == null) {
            setCurrentState(initialState, context);
        }
    }

    protected void setCurrentState(final State<C> state, final C context) {
        execute(new Runnable() {
            @Override
            public void run() {
                if (LoggerConfig.ON)
                    Log.d(TAG, String.format("setting current state to %s for %s <<<", state, context));

                State<C> prevState = context.getState();
                if (prevState != null) {
                    prevState.leave(context);
                }

                context.setState(state);
                context.getState().enter(context);

                if (LoggerConfig.ON)
                    Log.d(TAG, String.format("setting current state to %s for %s >>>", state, context));
            }
        });
    }

    public StateMachineFlow<C> executor(Executor executor) {
        this.executor = executor;
        return this;
    }

    protected void execute(Runnable task) {
        executor.execute(task);
    }

    public State getState() {
        return currentState;
    }

    public StateMachineFlow<C> whenEventTriggered(EventHandler<C> onEventTriggered) {
        this.onEventTriggeredHandler = onEventTriggered;
        return this;
    }

    protected void callOnEventTriggered(Event<C> event, State<C> from, State<C> to, C context) throws Exception {
        if (onEventTriggeredHandler != null) {
            try {
                if (LoggerConfig.ON)
                    Log.d(TAG, String.format("when triggered %s in %s for %s <<<", event, from, context));

                onEventTriggeredHandler.call(event, from, to, context);

                if (LoggerConfig.ON)
                    Log.d(TAG, String.format("when triggered %s in %s for %s >>>", event, from, context));
            } catch (Exception e) {
                // TODO
            }
        }
    }

}
