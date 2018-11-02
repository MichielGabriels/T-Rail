package be.pxl.student.t_rail.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class TimeService {

    public static String extractTimeFromJsonObject(String unixTime){
        long unixTimeHolder = Long.valueOf(unixTime);
        Date dateTime = new Date(unixTimeHolder * 1000);
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(dateTime);
    }

    public static String convertDelayFromSecondsToMinutes(String seconds){
        return String.valueOf(Integer.valueOf(seconds)/60);
    }

    //the formatter specifies in which pattern u want to return the time
    public static String getCurrentSystemTime(SimpleDateFormat formatter){
        Date currentTime = Calendar.getInstance().getTime();
        return formatter.format(currentTime);
    }
    //the formatter specifies in which pattern u want to return the date
    public static String getCurrentSystemDate(SimpleDateFormat formatter){
        Date currentDate = Calendar.getInstance().getTime();
        return formatter.format(currentDate);
    }
}
