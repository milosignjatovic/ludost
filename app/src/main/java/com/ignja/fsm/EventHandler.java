package com.ignja.fsm;

/**
 * Created by milos on 5/6/19.
 */
public interface EventHandler<C extends StatefulContext> {

    String TAG = "EventHandler";

    void call(Event<C> event, State<C> from, State<C> to, C context) throws Exception;
}
