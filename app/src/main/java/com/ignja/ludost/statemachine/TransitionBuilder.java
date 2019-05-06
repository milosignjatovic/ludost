package com.ignja.ludost.statemachine;

/**
 * Created by milos on 5/6/19.
 */
public class TransitionBuilder<C extends StatefulContext> {

    private Event<C> event;
    private State<C> stateTo;

    protected TransitionBuilder(Event<C> event, State<C> stateTo) {
        this.event = event;
        this.stateTo = stateTo;
    }

    protected Event<C> getEvent() {
        return event;
    }

    protected State<C> getStateTo() {
        return stateTo;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public TransitionBuilder transit(TransitionBuilder... transitions) {
        for (TransitionBuilder<C> transition : transitions) {
            transition.getEvent().addTransition(stateTo, transition.getStateTo());
            stateTo.addEvent(transition.getEvent(), transition.getStateTo());
        }

        return this;
    }
}