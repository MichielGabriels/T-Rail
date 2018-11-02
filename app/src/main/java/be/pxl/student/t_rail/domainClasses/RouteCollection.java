package be.pxl.student.t_rail.domainClasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RouteCollection implements Serializable {

    private ArrayList<Route> routes;
    private String date;

    public RouteCollection(){
        routes = new ArrayList<>();
    }

    public RouteCollection(String jsonString,String date){
        this();
        this.date = date;
        try{
            JSONArray connections = new JSONObject(jsonString).getJSONArray("connection");
            extractRoutesFromJsonArray(connections);
        }

        catch (JSONException ex){
            System.out.println(ex.getMessage());
        }

    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    private void extractRoutesFromJsonArray(JSONArray jsonArray) throws JSONException{
        for(int index = 0;index < jsonArray.length();index++){
                JSONObject currentObject = jsonArray.getJSONObject(index);
                Route currentRoute = new Route(currentObject);
                currentRoute.setDate(this.date);
                routes.add(currentRoute);
        }
    }

    public Route getRouteByDepartureTime(String departureTime) throws RouteNotFoundException{
        for(Route currentRoute : routes){
            if(departureTime.equals(currentRoute.getTimeDeparture())){
                return currentRoute;
            }
        }

        throw new RouteNotFoundException("Route not found in routeCollection");
    }

}
