package be.pxl.student.t_rail.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import be.pxl.student.t_rail.R;
import be.pxl.student.t_rail.domainClasses.Route;

public class NotificationService extends IntentService {

    private ArrayList<Route> mRoutes;
    private final List<Long> mNotifyMinutes = Arrays.asList(30L,15L,10L,5L);
    private Timer mTimer;
    private final String CHANNEL_ID = "t-rail notifications";
    private final int NOTIFICATION_ID = 001;
    private NotificationManager mNotificationManager;

    public NotificationService(){
        super("");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRoutes = new ArrayList<>();
        mTimer = new Timer();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
        System.out.println("notificationService started");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        mRoutes.add((Route) intent.getSerializableExtra("route"));
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
       synchronized (this){
           mTimer.schedule(getTask(),0,60000);
       }
    }

    private TimerTask getTask(){
        return new TimerTask() {
            @Override
            public void run() {

                try{
                    cleanUpExpiredRoutes();
                }

                catch (ParseException ex){
                    System.out.println(ex.getMessage());
                }

                //stop service if no routes are watched
                if(mRoutes.size() == 0){
                    mTimer.cancel();
                    stopSelf();
                    System.out.println("notification service stopped");
                }

                else{
                    try{
                        HashMap<Long,Route> routesToNotify = getRoutesToNotify();
                        if(routesToNotify.size() >0){
                            for(Map.Entry<Long,Route> entry : routesToNotify.entrySet()){
                                notifyRoute(entry.getKey(),entry.getValue());
                            }
                        }
                        System.out.println("notification service running..");
                    }

                    catch (ParseException ex){
                        System.out.println(ex.getMessage());
                    }
                }

            }
        };
    }

    //checks all watched routes and returns the routes that need a notification
    private HashMap<Long,Route> getRoutesToNotify() throws ParseException {
        HashMap<Long,Route> routesToNotify = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy-HH:mm");
        Date dateNow = Calendar.getInstance().getTime();

        for(Route route : mRoutes){
            String routeDateTime = String.format("%s-%s",route.getDate(),route.getTimeDeparture());
            Date routeDate = formatter.parse(routeDateTime);
            if(TimeService.isSameDay(dateNow,routeDate)){
                long timeDifferenceMinutes = TimeService.differenceInTimeUnit(dateNow,routeDate,TimeUnit.MINUTES);
                if(mNotifyMinutes.contains(timeDifferenceMinutes)){
                    routesToNotify.put(timeDifferenceMinutes,route);
                }
            }
        }
        return routesToNotify;
    }

    private void cleanUpExpiredRoutes() throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy-HH:mm");
        Date dateNow = Calendar.getInstance().getTime();

        for(Route route : mRoutes){
            String routeDateTime = String.format("%s-%s",route.getDate(),route.getTimeDeparture());
            Date routeDate = formatter.parse(routeDateTime);
            long timeDifferenceMinutes = TimeService.differenceInTimeUnit(dateNow,routeDate,TimeUnit.MINUTES);
            if(timeDifferenceMinutes < 0){
                mRoutes.remove(route);
                System.out.println("route expired -> removed");
            }
        }
    }

    //the wakeLock will wake the device, this way it shows notifications when the device is locked
    private void notifyRoute(long minutes,Route route){
        /*PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"my:tag");
        wakeLock.acquire();*/
        mNotificationManager.notify(NOTIFICATION_ID,buildNotification(minutes,route));
        //wakeLock.release();
    }

    private Notification buildNotification(long minutes,Route route){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.t_rail_logo)
                .setContentTitle(String.format("Trein vertrekt in %d minuten",minutes))
                .setContentText(String.format("%s-%s vertrekt in %d minuten op perron %d",route.getStationDeparture(),route.getStationArrival(),minutes,route.getPlatformDeparture()));
        return builder.build();
    }

    private void createNotificationChannel(){

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
