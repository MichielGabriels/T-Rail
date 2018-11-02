package be.pxl.student.t_rail.domainClasses;

import android.content.DialogInterface;

import be.pxl.student.t_rail.interfaces.IDialogEvent;
import be.pxl.student.t_rail.interfaces.IEvent;

public class DialogClickEvent extends DialogEvent implements DialogInterface.OnClickListener {

    public DialogClickEvent(IDialogEvent eventHandler){
        super(eventHandler);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        event.invoke(dialog,which);
    }
}
