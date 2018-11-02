package be.pxl.student.t_rail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.pxl.student.t_rail.adapters.RouteMasterAdapter;
import be.pxl.student.t_rail.events.ClickEvent;
import be.pxl.student.t_rail.dialogs.ConnectionAlertDialog;
import be.pxl.student.t_rail.domainClasses.Route;
import be.pxl.student.t_rail.services.ConnectionService;

public class RouteMasterFragment extends Fragment {

    private String mConnectionJsonString;
    private String mDate;
    private HashMap<String,String> vehicleIdMap;
    private RouteMasterDetailActivity parentActivity;

    public RouteMasterFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle dataBundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_route_master, container, false);
        parentActivity = (RouteMasterDetailActivity) getActivity();
        vehicleIdMap = new HashMap<>();

        //orientation changed
        if(savedInstanceState != null){
            mConnectionJsonString = savedInstanceState.getString("connections");
            mDate = savedInstanceState.getString("date");
            convertStringToVehicleIdMap(savedInstanceState.getString("vehicleIdMap"));
        }

        //first time fragment created
        else if(dataBundle != null){
            mConnectionJsonString = dataBundle.getString("connections");
            mDate = dataBundle.getString("date");
        }

        if(mConnectionJsonString != null){
           try{
               initializeData(view, mConnectionJsonString);
           }

           catch (JSONException ex){
               System.out.println(ex.getMessage());
           }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("connections", mConnectionJsonString);
        outState.putString("vehicleIdMap",convertVehicleIdMapToString());
        outState.putString("date",mDate);
    }

    private ArrayList<Route> extractRoutesFromJsonArray(JSONArray jsonData){
            ArrayList<Route> routes = new ArrayList<>();
        try{
            for(int index = 0;index < jsonData.length();index++){
                JSONObject currentObject = jsonData.getJSONObject(index);
                Route currentRoute = new Route(currentObject);
                routes.add(currentRoute);
                vehicleIdMap.put(currentRoute.getTimeDeparture(),currentRoute.getVehicle().getVehicleId());
            }
        }

        catch (JSONException ex){
            System.out.println(ex.getMessage());
        }

        return routes;
    }

    private void initializeData(View view,String jsonString) throws JSONException{
        JSONArray connections = new JSONObject(jsonString).getJSONArray("connection");
        ArrayList<Route> routes = extractRoutesFromJsonArray(connections);

        ClickEvent itemClick = new ClickEvent((v) ->{
            search(v);
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewRouteMaster);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RouteMasterAdapter adapter = new RouteMasterAdapter(routes,itemClick);
        recyclerView.setAdapter(adapter);
    }

    private String convertVehicleIdMapToString(){
        String result = "";
        for(Map.Entry<String,String> entry : vehicleIdMap.entrySet()){
            String element = String.format("%s=>%s;",entry.getKey(),entry.getValue());
            result += element;
        }
        return result;
    }

    private void convertStringToVehicleIdMap(String mapString){
        String[] elements = mapString.split(";");
        for(int index =0; index<elements.length;index++){
            String[] subElements = elements[index].split("=>");
            vehicleIdMap.put(subElements[0],subElements[1]);
        }
    }

    private void search(View v){
        DialogInterface.OnClickListener negativeEvent = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                search(v);
            }
        };
        DialogInterface.OnClickListener positiveEvent = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                parentActivity.finishAffinity();
            }
        };

        if(ConnectionService.hasActiveInternetConnection(parentActivity)){
            performSearch(v);
        }

        else{
            new ConnectionAlertDialog(parentActivity,positiveEvent,negativeEvent);
        }
    }

    private void performSearch(View v){
        /*TextView departTime  = (TextView) v.findViewById(R.id.routeListTimeStation1);
        TextView departStation = (TextView) v.findViewById(R.id.routeListStation1);
        String time = departTime.getText().toString();
        String departStationText = departStation.getText().toString();
        String vehicleId = vehicleIdMap.get(time);*/
        Route selectedRoute = getRouteFromClickedView(v);
        parentActivity.initializeDetailFragment(selectedRoute,getResources().getConfiguration().orientation);
    }

    private Route getRouteFromClickedView(View view){
        TextView departureTime = (TextView) view.findViewById(R.id.routeListTimeStation1);
        TextView arrivalTime = (TextView) view.findViewById(R.id.routeListTimeStation2);
        TextView departureStation = (TextView) view.findViewById(R.id.routeListStation1);
        TextView arrivalStation = (TextView) view.findViewById(R.id.routeListStation2);
        TextView departureDelay = (TextView) view.findViewById(R.id.routeListDelayDeparture);
        TextView arrivalDelay = (TextView) view.findViewById(R.id.routeListDelayArrival);

        Route selectedRoute = new Route(departureStation.getText().toString(),arrivalStation.getText().toString(),departureTime.getText().toString()
                                        ,arrivalTime.getText().toString(),departureDelay.getText().toString(),arrivalDelay.getText().toString(),
                        0,0,vehicleIdMap.get(departureTime.getText().toString()));
        if(mDate != null && mDate != ""){
            selectedRoute.setDate(mDate);
        }
        return selectedRoute;
    }
}
