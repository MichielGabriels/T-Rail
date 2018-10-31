package be.pxl.student.t_rail.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
