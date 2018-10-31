package be.pxl.student.t_rail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import be.pxl.student.t_rail.tasks.RouteDetailHttpTask;

public class RouteMasterDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_master);

        String connections = getIntent().getStringExtra("connections");

        Bundle dataBundle = new Bundle();
        dataBundle.putString("connections",connections);
        initializeMasterFragement(dataBundle);
    }

    private void initializeMasterFragement(Bundle dataBundle){
        RouteMasterFragment masterFragment = new RouteMasterFragment();
        masterFragment.setArguments(dataBundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentMasterHolder,masterFragment);
        transaction.commit();
    }

    public void initializeDetailFragment(String vehicleId,int orientation){
        new RouteDetailHttpTask(this,this,orientation).execute(vehicleId);
    }
}
