package be.pxl.student.t_rail.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import be.pxl.student.t_rail.HomeActivity;
import be.pxl.student.t_rail.domainClasses.ApiClient;
import be.pxl.student.t_rail.domainClasses.StationCollection;
import be.pxl.student.t_rail.services.ApiService;

public class StationsHttpTask extends AsyncTask<String,String,String> {

    private ApiService apiService;
    private Context activityContext;

    public StationsHttpTask(Context context){
        activityContext = context;
        apiService = new ApiService();
    }

    @Override
    protected String doInBackground(String... strings) {
        String requestUrl = "stations/?format=json&lang=nl";
        String jsonContent = "";
        synchronized (this){
            try{
              jsonContent = apiService.doGetRequest(requestUrl);
            }

            catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }

        return jsonContent;
    }

    @Override
    protected void onPostExecute(String jsonContent) {
        ArrayList<String> stations = extractStationsFromDataString(jsonContent);
        StationCollection.initStations(stations);
        Intent intent = new Intent(activityContext,HomeActivity.class);
        activityContext.startActivity(intent);
    }

    private ArrayList<String> extractStationsFromDataString(String data){
        ArrayList<String> stationsArray = new ArrayList<>();
        try{
            JSONArray stations = new JSONObject(data).getJSONArray("station");
            for(int index = 0; index< stations.length();index++){
                JSONObject currentStation = stations.getJSONObject(index);
                stationsArray.add(currentStation.getString("name"));
            }
        }

        catch (JSONException ex){
            System.out.println(ex.getMessage());
        }

        return  stationsArray;
    }


}
