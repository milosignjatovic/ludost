package com.ignja.ludost.statemachine;

/**
 * Created by milos on 5/6/19.
 */
public interface EventHandler {

    void call(Event event, State from, State to) throws Exception;
}
