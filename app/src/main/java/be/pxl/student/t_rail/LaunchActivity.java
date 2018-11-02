package be.pxl.student.t_rail;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import be.pxl.student.t_rail.dialogs.ConnectionAlertDialog;
import be.pxl.student.t_rail.services.ConnectionService;
import be.pxl.student.t_rail.tasks.StationsHttpTask;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        checkConnection();

    }



    private void checkConnection(){
        DialogInterface.OnClickListener negativeEvent = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                checkConnection();
            }
        };
        DialogInterface.OnClickListener positiveEvent = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LaunchActivity.this.finishAffinity();
            }
        };

        if(ConnectionService.hasActiveInternetConnection(LaunchActivity.this)){
            StationsHttpTask task = new StationsHttpTask(LaunchActivity.this);
            task.execute();
        }

        else{
           new ConnectionAlertDialog(LaunchActivity.this,positiveEvent,negativeEvent);
        }

    }
}
