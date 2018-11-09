package be.pxl.student.t_rail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import be.pxl.student.t_rail.domainClasses.Route;
import be.pxl.student.t_rail.domainClasses.RouteCollection;
import be.pxl.student.t_rail.tasks.RouteDetailHttpTask;

public class RouteMasterDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_master);

        RouteCollection routes = (RouteCollection) getIntent().getSerializableExtra("routes");

        Bundle dataBundle = new Bundle();
        dataBundle.putSerializable("routes",routes);
        initializeMasterFragement(dataBundle);
    }

    private void initializeMasterFragement(Bundle dataBundle){
        RouteMasterFragment masterFragment = new RouteMasterFragment();
        masterFragment.setArguments(dataBundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentMasterHolder,masterFragment);
        transaction.commit();
    }

    public void initializeDetailFragment(Route selectedRoute, int orientation){
        new RouteDetailHttpTask(this,this,orientation).execute(selectedRoute);
    }
}
