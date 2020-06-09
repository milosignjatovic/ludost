package com.ignja.fsm;

/**
 * Created by milos on 5/6/19.
 */
public interface StateHandler<C extends StatefulContext> {

    String TAG = "StateHandler";

    void call(State<C> state, C context) throws Exception;
}
