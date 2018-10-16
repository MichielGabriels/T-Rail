package be.pxl.student.t_rail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import be.pxl.student.t_rail.adapters.RoutePortraitAdapter;
import be.pxl.student.t_rail.domainClasses.Route;

public class RouteMasterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_master);

        Route route = new Route("Aarschot","Begijnendijk","12:21","12:45","+2","+5",3,2);
        Route secondRoute = new Route("Aarschot","Begijnendijk","12:27","13:00","+15","+13",3,9);
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(route);
        routes.add(secondRoute);

        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.recycleViewRoutes);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RoutePortraitAdapter adapter = new RoutePortraitAdapter(routes);
        recyclerView.setAdapter(adapter);

    }
}
