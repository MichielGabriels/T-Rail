package be.pxl.student.t_rail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import be.pxl.student.t_rail.adapters.RouteMasterAdapter;
import be.pxl.student.t_rail.domainClasses.Route;

public class RouteMasterFragment extends Fragment {

    public RouteMasterFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_master, container, false);

        Route route = new Route("Aarschot","Hasselt","06:57","07:22","+2","+5",3,2);
        //Route secondRoute = new Route("Aarschot","Hasselt","12:27","13:00","+15","+13",3,9);
        List<Route> routes = new ArrayList<>();
        routes.add(route);
        //routes.add(secondRoute);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewRouteMaster);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RouteMasterAdapter adapter = new RouteMasterAdapter(routes);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
