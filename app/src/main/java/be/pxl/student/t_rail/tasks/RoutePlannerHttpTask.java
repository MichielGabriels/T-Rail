package be.pxl.student.t_rail.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;

import be.pxl.student.t_rail.domainClasses.RouteDialog;
import be.pxl.student.t_rail.services.ApiService;

public class RoutePlannerHttpTask extends AsyncTask<String,String,String> {

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
    protected String doInBackground(String... strings){
        String url = strings[0];
        String content = "";
        synchronized (this){
            try{
                content = _service.doGetRequest(url);
            }

            catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }

        return content;
    }

    @Override
    protected void onPostExecute(String content) {
        if(_navigateAfter){
            if(_nextActivity != null){
                Intent intent = new Intent(_context,_nextActivity);
                intent.putExtra("connections",content);
                _context.startActivity(intent);
            }
        }
        dialog.close();
    }
}
