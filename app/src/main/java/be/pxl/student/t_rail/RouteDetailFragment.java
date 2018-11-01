package be.pxl.student.t_rail;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.pxl.student.t_rail.adapters.RouteDetailAdapter;
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
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewRouteDetails);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                RouteDetailAdapter adapter = new RouteDetailAdapter(details);
                recyclerView.setAdapter(adapter);
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
