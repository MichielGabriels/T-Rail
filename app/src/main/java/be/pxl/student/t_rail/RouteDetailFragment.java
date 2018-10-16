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

import be.pxl.student.t_rail.adapters.RouteDetailAdapter;
import be.pxl.student.t_rail.domainClasses.RouteDetail;

public class RouteDetailFragment extends Fragment {

    public RouteDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_detail, container, false);

        RouteDetail routeDetail = new RouteDetail("Aarschot", "Diest", "06:57", "07:12", "3", "1");
        RouteDetail secondRouteDetail = new RouteDetail("Diest", "Hasselt", "07:12", "07:22", "1", "2");
        List<RouteDetail> routeDetails = new ArrayList<>();
        routeDetails.add(routeDetail);
        routeDetails.add(secondRouteDetail);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewRouteDetails);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RouteDetailAdapter adapter = new RouteDetailAdapter(routeDetails);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
