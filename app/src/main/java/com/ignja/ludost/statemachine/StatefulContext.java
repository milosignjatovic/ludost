package com.ignja.ludost.statemachine;

import java.io.Serializable;

/**
 * Created by milos on 5/6/19.
 */
public class StatefulContext implements Serializable {

    private State state;

    public void setState(State state){
        this.state = state;
    }

    public State getState() {
        return state;
    }

}
