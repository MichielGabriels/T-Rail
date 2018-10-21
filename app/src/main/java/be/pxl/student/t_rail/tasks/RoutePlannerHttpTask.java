package be.pxl.student.t_rail.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;

import be.pxl.student.t_rail.services.ApiService;

public class RoutePlannerHttpTask extends AsyncTask<String,String,String> {

    private Context _context;
    private ProgressDialog dialog;
    private ApiService _service;
    private boolean _navigateAfter;
    private Class _nextActivity;

    public RoutePlannerHttpTask(Context context, String dialogTitle){
        _context = context;
        dialog = new ProgressDialog(_context);
        dialog.setTitle(dialogTitle);
        _service = new ApiService();
    }

    public RoutePlannerHttpTask(Context context, String dialogTitle, boolean navigateAfter, Class nextActivity){
        this(context,dialogTitle);
        _navigateAfter = navigateAfter;
        _nextActivity = nextActivity;
    }

    @Override
    protected void onPreExecute() {
        dialog.setCancelable(false);
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
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
        dialog.hide();
    }
}
