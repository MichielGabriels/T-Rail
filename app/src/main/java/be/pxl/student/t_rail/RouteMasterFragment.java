package be.pxl.student.t_rail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.pxl.student.t_rail.adapters.RouteMasterAdapter;
import be.pxl.student.t_rail.domainClasses.Route;

public class RouteMasterFragment extends Fragment {

    //private JSONArray connections;

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
        if(dataBundle != null){
            String jsonString = dataBundle.getString("connections");
            try{
                JSONArray connections = new JSONObject(jsonString).getJSONArray("connection");
                ArrayList<Route> routes = extractRoutesFromJsonArray(connections);


                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewRouteMaster);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                RouteMasterAdapter adapter = new RouteMasterAdapter(routes);
                recyclerView.setAdapter(adapter);
            }

            catch (JSONException ex){
                System.out.println(ex.getMessage());
            }
        }


        //Route route = new Route("Aarschot","Hasselt","06:57","07:22","+2","+5",3,2,"bla");
        //Route secondRoute = new Route("Aarschot","Hasselt","12:27","13:00","+15","+13",3,9);
        //List<Route> routes = new ArrayList<>();
       // routes.add(route);
        //routes.add(secondRoute);



        return view;
    }

    private ArrayList<Route> extractRoutesFromJsonArray(JSONArray jsonData){
            ArrayList<Route> routes = new ArrayList<>();
        try{
            for(int index = 0;index < jsonData.length();index++){
                JSONObject currentObject = jsonData.getJSONObject(index);
                routes.add(new Route(currentObject));
            }
        }

        catch (JSONException ex){
            System.out.println(ex.getMessage());
        }

        return routes;
    }


}
