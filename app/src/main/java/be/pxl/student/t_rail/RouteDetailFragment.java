package be.pxl.student.t_rail;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.pxl.student.t_rail.adapters.RouteDetailAdapter;
import be.pxl.student.t_rail.dialogs.OptionsDialog;
import be.pxl.student.t_rail.domainClasses.RouteDetailCollection;
import be.pxl.student.t_rail.events.DialogClickEvent;
import be.pxl.student.t_rail.events.LongClickEvent;
import be.pxl.student.t_rail.domainClasses.Route;
import be.pxl.student.t_rail.domainClasses.RouteDetail;

public class RouteDetailFragment extends Fragment {

    private Route mSelectedRoute;
    private RouteDetailCollection mRouteDetails;

    public RouteDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_detail, container, false);
        Bundle dataBundle = getArguments();

        if(dataBundle != null){
            mRouteDetails = (RouteDetailCollection) dataBundle.getSerializable("routeDetails");
            mSelectedRoute = (Route) dataBundle.getSerializable("selectedRoute");
        }

        if(mRouteDetails != null){
            initializeRecyclerView(view,mRouteDetails);
        }
        return view;
    }

    //TODO: add notification service to dialogClickEvent
    private void initializeRecyclerView(View view,RouteDetailCollection detailsCollection){
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewRouteDetails);
        DialogClickEvent dialogClickEvent = new DialogClickEvent((dialog,which) ->{
            Toast.makeText(getContext(),String.format("%s->%s",mSelectedRoute.getStationDeparture(),mSelectedRoute.getStationArrival()),Toast.LENGTH_SHORT).show();
        });
        String[] dialogValues = new String[]{"Volg route"};
        LongClickEvent longClickEvent = new LongClickEvent((v) ->{
            OptionsDialog dialog = new OptionsDialog(getActivity(),dialogValues,dialogClickEvent);
            dialog.show();
        });

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RouteDetailAdapter adapter = new RouteDetailAdapter(detailsCollection.getRouteDetails(),longClickEvent);
        recyclerView.setAdapter(adapter);
    }
}
