package be.pxl.student.t_rail.domainClasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class RouteDetailCollection implements Serializable {

    private ArrayList<RouteDetail> routeDetails;

    public RouteDetailCollection(){
        routeDetails = new ArrayList<>();
    }

    public RouteDetailCollection(String jsonString){
        this();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray dataArray = jsonObject.getJSONObject("stops").getJSONArray("stop");
            extractRouteDetailsFromJsonArray(dataArray);
        }

        catch (JSONException ex){
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<RouteDetail> getRouteDetails() {
        return routeDetails;
    }

    private void extractRouteDetailsFromJsonArray(JSONArray dataArray) throws JSONException {
        for(int index = 0 ;index<dataArray.length();index++){
            JSONObject currentObject = dataArray.getJSONObject(index);
            routeDetails.add(new RouteDetail(currentObject));
        }
    }
}
