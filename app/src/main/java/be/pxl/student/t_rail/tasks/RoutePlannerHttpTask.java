package be.pxl.student.t_rail.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import be.pxl.student.t_rail.dialogs.RouteDialog;
import be.pxl.student.t_rail.domainClasses.Route;
import be.pxl.student.t_rail.domainClasses.RouteCollection;
import be.pxl.student.t_rail.models.RoutePlanResponseModel;
import be.pxl.student.t_rail.services.ApiService;

public class RoutePlannerHttpTask extends AsyncTask<String,String,RoutePlanResponseModel> {

    private Context _context;
    private RouteDialog dialog;
    private ApiService _service;
    private boolean _navigateAfter;
    private Class _nextActivity;

    public RoutePlannerHttpTask(Context context){
        _context = context;
        dialog = new RouteDialog(_context);
        _service = new ApiService();
    }

    public RoutePlannerHttpTask(Context context, boolean navigateAfter, Class nextActivity){
        this(context);
        _navigateAfter = navigateAfter;
        _nextActivity = nextActivity;
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
    }

    @Override
    protected RoutePlanResponseModel doInBackground(String... strings){
        String url = strings[0];
        String date = strings[1];
        RoutePlanResponseModel content = new RoutePlanResponseModel();
        content.setDate(date);
        synchronized (this){
            try{
                content.setResponse(_service.doGetRequest(url));
            }

            catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }

        return content;
    }

    @Override
    protected void onPostExecute(RoutePlanResponseModel content) {
        if(_navigateAfter){
            if(_nextActivity != null){
                Intent intent = new Intent(_context,_nextActivity);
                RouteCollection routes = new RouteCollection(content.getResponse(),content.getDate());
                intent.putExtra("routes",routes);
                _context.startActivity(intent);
            }
        }
        dialog.close();
    }
}
