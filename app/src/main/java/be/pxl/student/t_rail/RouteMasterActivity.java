package be.pxl.student.t_rail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import be.pxl.student.t_rail.adapters.RoutePortraitAdapter;
import be.pxl.student.t_rail.domainClasses.Route;

public class RouteMasterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_master);

        Route route = new Route("Aarschot","Hasselt","12:21","12:45","+2","+5",3,2);
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(route);

        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.recycleViewRoutes);
        RoutePortraitAdapter adapter = new RoutePortraitAdapter(routes);
        recyclerView.setAdapter(adapter);

    }
}
