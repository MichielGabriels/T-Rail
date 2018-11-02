package be.pxl.student.t_rail.events;

import be.pxl.student.t_rail.interfaces.IDialogEvent;

public abstract class DialogEvent {

    protected IDialogEvent event;

    public DialogEvent(IDialogEvent event){
        this.event = event;
    }

}
