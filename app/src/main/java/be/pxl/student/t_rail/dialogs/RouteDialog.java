package be.pxl.student.t_rail.dialogs;

import android.app.ProgressDialog;
import android.content.Context;

public class RouteDialog {

    private ProgressDialog dialog;

    public RouteDialog(Context context){
        dialog = new ProgressDialog(context);
        dialog.setTitle("Route ophalen");
        dialog.setCancelable(false);
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
    }

    public void show(){
        dialog.show();
    }

    public void close(){
        dialog.hide();
    }
}
