package be.pxl.student.t_rail.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.io.IOException;

import be.pxl.student.t_rail.R;
import be.pxl.student.t_rail.RouteDetailFragment;
import be.pxl.student.t_rail.domainClasses.ApiClient;
import be.pxl.student.t_rail.domainClasses.RouteDialog;
import be.pxl.student.t_rail.services.ApiService;

public class RouteDetailHttpTask extends AsyncTask<String,String,String> {

    private RouteDialog dialog;
    private ApiService service;
    private int orientation;
    private FragmentActivity activity;

    public RouteDetailHttpTask(Context context, FragmentActivity activity, int orientation){
        dialog = new RouteDialog(context);
        service = new ApiService();
        this.orientation = orientation;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
    }

    @Override
    protected String doInBackground(String... content) {
        String vehicleId = content[0];
        String result = "";
        String url = String.format("vehicle/?id=%s&format=json&lang=nl",vehicleId);
        synchronized (this){
            try{
                result = service.doGetRequest(url);
            }

            catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String response) {
       dialog.close();
        RouteDetailFragment detailFragment = new RouteDetailFragment();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        Bundle dataBundle = new Bundle();
        dataBundle.putString("routeDetails",response);
        detailFragment.setArguments(dataBundle);
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            transaction.replace(R.id.fragmentDetailHolder,detailFragment);
        }

        else{
            transaction.replace(R.id.fragmentMasterHolder,detailFragment);
        }
        transaction.commit();
    }
}
