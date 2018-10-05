package be.pxl.student.t_rail.domainClasses;

import android.view.View;

import be.pxl.student.t_rail.interfaces.IEvent;

public abstract class Event{

    protected IEvent eventHandler;

    public Event(IEvent eventHandler){
        setEventhandler(eventHandler);
    }

    private void setEventhandler(IEvent eventhandler) {
        this.eventHandler = eventhandler;
    }



}
