package be.pxl.student.t_rail;

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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.pxl.student.t_rail.adapters.RouteMasterAdapter;
import be.pxl.student.t_rail.domainClasses.ClickEvent;
import be.pxl.student.t_rail.domainClasses.Route;

public class RouteMasterFragment extends Fragment {

    private String mConnectionJsonString;
    private HashMap<String,String> vehicleIdMap;

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
        vehicleIdMap = new HashMap<>();

        //orientation changed
        if(savedInstanceState != null){
            mConnectionJsonString = savedInstanceState.getString("connections");
            convertStringToVehicleIdMap(savedInstanceState.getString("vehicleIdMap"));
        }

        //first time fragment created
        else if(dataBundle != null){
            mConnectionJsonString = dataBundle.getString("connections");
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
            TextView departTime  = (TextView) v.findViewById(R.id.routeListTimeStation1);
            String time = departTime.getText().toString();
            String vehicleId = vehicleIdMap.get(time);
            RouteMasterDetailActivity activity = (RouteMasterDetailActivity) getActivity();
            activity.initializeDetailFragment(vehicleId,getResources().getConfiguration().orientation);
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
}
