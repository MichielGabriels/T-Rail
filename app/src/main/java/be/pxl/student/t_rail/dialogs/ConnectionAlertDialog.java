package be.pxl.student.t_rail.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import be.pxl.student.t_rail.LaunchActivity;

public class ConnectionAlertDialog {

    public ConnectionAlertDialog(Context context, DialogInterface.OnClickListener positiveEvent, DialogInterface.OnClickListener negativeEvent){
        new AlertDialog.Builder(context).setTitle("Netwerk error").setMessage("Deze applicatie heeft een mobiele verbinding nodig.")
                .setPositiveButton("Afsluiten",positiveEvent).setNegativeButton("Probeer Opnieuw",negativeEvent).setCancelable(true).show();
    }

}
