package com.ignja.ludost.statemachine;

/**
 * Created by milos on 5/6/19.
 */
public interface StateHandler<C extends StatefulContext> {

    void call(State<C> state, C context) throws Exception;
}
