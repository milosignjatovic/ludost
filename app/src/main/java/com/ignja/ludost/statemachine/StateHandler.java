package com.ignja.ludost.statemachine;

/**
 * Created by milos on 5/6/19.
 */
public interface StateHandler {

    void call(State state) throws Exception;
}
