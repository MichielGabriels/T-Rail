package be.pxl.student.t_rail.dialogs;

import android.app.Activity;
import android.app.AlertDialog;

import be.pxl.student.t_rail.events.DialogClickEvent;

public class OptionsDialog {

    private AlertDialog.Builder mDialogBuilder;


    public OptionsDialog(Activity activity, String[] values, DialogClickEvent dialogClickEvent){
        mDialogBuilder = new AlertDialog.Builder(activity);
        mDialogBuilder.setTitle("Opties");
        mDialogBuilder.setItems(values,dialogClickEvent);
    }

    public void show(){
        mDialogBuilder.create().show();
    }

}
