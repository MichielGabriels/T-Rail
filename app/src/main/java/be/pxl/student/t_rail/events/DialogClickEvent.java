package be.pxl.student.t_rail.events;

import android.content.DialogInterface;

import be.pxl.student.t_rail.interfaces.IDialogEvent;

public class DialogClickEvent extends DialogEvent implements DialogInterface.OnClickListener {

    public DialogClickEvent(IDialogEvent eventHandler){
        super(eventHandler);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        event.invoke(dialog,which);
    }


}
