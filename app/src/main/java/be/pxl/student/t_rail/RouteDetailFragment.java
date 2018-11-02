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
import be.pxl.student.t_rail.events.DialogClickEvent;
import be.pxl.student.t_rail.events.LongClickEvent;
import be.pxl.student.t_rail.domainClasses.Route;
import be.pxl.student.t_rail.domainClasses.RouteDetail;

public class RouteDetailFragment extends Fragment {

    private Route mSelectedRoute;

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
            String routeDetails = dataBundle.getString("routeDetails");
            mSelectedRoute = (Route) dataBundle.getSerializable("selectedRoute");
            try{
                JSONObject jsonObject = new JSONObject(routeDetails);
                JSONArray stops = jsonObject.getJSONObject("stops").getJSONArray("stop");
                ArrayList<RouteDetail> details = extractRouteDetailsFromJsonArray(stops);
                initializeRecyclerView(view,details);
            }

            catch (JSONException ex){
                System.out.println(ex.getMessage());
            }
        }
        return view;
    }

    private ArrayList<RouteDetail> extractRouteDetailsFromJsonArray(JSONArray jsonArray) throws JSONException{
        ArrayList<RouteDetail> details = new ArrayList<>();
        for(int index = 0 ;index<jsonArray.length();index++){
            JSONObject currentObject = jsonArray.getJSONObject(index);
            details.add(new RouteDetail(currentObject));
        }
        return details;
    }

    //TODO: add notification service to dialogClickevent
    private void initializeRecyclerView(View view,List<RouteDetail> details){
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
        RouteDetailAdapter adapter = new RouteDetailAdapter(details,longClickEvent);
        recyclerView.setAdapter(adapter);
    }

    /*@Override
    public void onPause() {
        super.onPause();
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(getContext(),"CHANNEL_ID")
                .setSmallIcon(R.drawable.t_rail_logo)
                .setContentTitle("Running")
                .setContentText("App is still running in background")
                .setDefaults(Notification.DEFAULT_ALL)
                .build();
        notificationManager.notify(0,notification);

    }*/
}
