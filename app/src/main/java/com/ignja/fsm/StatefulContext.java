package com.ignja.fsm;

import java.io.Serializable;

/**
 * Created by milos on 5/6/19.
 */
public class StatefulContext implements Serializable {

    private static final long serialVersionUID = 2324535129909715649L;

    private static long idCounter = 1;

    private final String id;

    private State state;

    public StatefulContext() {
        id = newId() + ":" + getClass().getSimpleName();
    }

    protected long newId() {
        return idCounter++;
    }

    public StatefulContext(String aId) {
        id = aId + ":" + getClass().getSimpleName();
    }

    public String getId() {
        return id;
    }

    public void setState(State state){
        this.state = state;
    }

    public <C extends StatefulContext> State<C> getState() {
        return state;
    }

}
