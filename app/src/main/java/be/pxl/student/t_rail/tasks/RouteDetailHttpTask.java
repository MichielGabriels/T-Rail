package be.pxl.student.t_rail.tasks;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.io.IOException;

import be.pxl.student.t_rail.R;
import be.pxl.student.t_rail.RouteDetailFragment;
import be.pxl.student.t_rail.domainClasses.Route;
import be.pxl.student.t_rail.domainClasses.RouteDetailCollection;
import be.pxl.student.t_rail.models.RouteDetailResponseModel;
import be.pxl.student.t_rail.dialogs.RouteDialog;
import be.pxl.student.t_rail.services.ApiService;

public class RouteDetailHttpTask extends AsyncTask<Route,String,RouteDetailResponseModel> {

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
    protected RouteDetailResponseModel doInBackground(Route... routes) {
        Route selectedRoute = routes[0];
        RouteDetailResponseModel responseModel = new RouteDetailResponseModel();
        responseModel.setSelectedRoute(selectedRoute);
        String url = String.format("vehicle/?id=%s&date=%s&format=json&lang=nl",selectedRoute.getVehicle().getVehicleId(),selectedRoute.getDate());
        synchronized (this){
            try{
                responseModel.setResponse(service.doGetRequest(url));
            }

            catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }

        return responseModel;
    }

    @Override
    protected void onPostExecute(RouteDetailResponseModel result) {
       dialog.close();
        RouteDetailFragment detailFragment = new RouteDetailFragment();
        RouteDetailCollection routeDetails = new RouteDetailCollection(result.getResponse());
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        Bundle dataBundle = new Bundle();
        dataBundle.putSerializable("routeDetails",routeDetails);
        dataBundle.putSerializable("selectedRoute",result.getSelectedRoute());


        detailFragment.setArguments(dataBundle);
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            transaction.replace(R.id.fragmentDetailHolder,detailFragment);
        }

        //portrait mode
        else{
            //addToBackStack saves previous transaction(masterFragment) so we can navigate back
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.addToBackStack(null);
            transaction.replace(R.id.fragmentMasterHolder,detailFragment);
        }
        transaction.commit();
    }
}
