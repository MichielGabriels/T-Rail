package be.pxl.student.t_rail.domainClasses;

import android.view.View;

import be.pxl.student.t_rail.interfaces.IEvent;

public class ClickEvent extends Event implements View.OnClickListener {

    public ClickEvent(IEvent eventHandler) {
        super(eventHandler);
    }

    @Override
    public void onClick(View v) {
        eventHandler.invoke(v);
    }
}
