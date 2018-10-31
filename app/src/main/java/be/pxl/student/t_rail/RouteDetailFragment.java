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

import be.pxl.student.t_rail.adapters.RouteDetailAdapter;
import be.pxl.student.t_rail.domainClasses.Route;
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
        Bundle dataBundle = getArguments();

        if(dataBundle != null){
            String routeDetails = dataBundle.getString("routeDetails");
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
}
