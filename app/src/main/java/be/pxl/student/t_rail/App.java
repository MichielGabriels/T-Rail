package be.pxl.student.t_rail;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

public class App extends Application implements Application.ActivityLifecycleCallbacks {

    private int activitiesActive = 0;
    private boolean isActivityChangingConfigurations = false;
    private Intent intent;


    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (++activitiesActive == 1 && !isActivityChangingConfigurations) {
            // App enters foreground
            if(intent != null){

            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations();
        if (--activitiesActive == 0 && !isActivityChangingConfigurations) {
            //App enters background
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
