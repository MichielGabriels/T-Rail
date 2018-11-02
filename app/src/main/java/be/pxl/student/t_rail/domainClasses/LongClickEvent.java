package be.pxl.student.t_rail.domainClasses;

import android.view.View;

import be.pxl.student.t_rail.interfaces.IEvent;

public class LongClickEvent extends Event implements View.OnLongClickListener {

    public LongClickEvent(IEvent eventHandler){
        super(eventHandler);
    }

    @Override
    public boolean onLongClick(View v) {
        this.eventHandler.invoke(v);
        return true;
    }
}
