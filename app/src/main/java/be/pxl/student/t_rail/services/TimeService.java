package be.pxl.student.t_rail.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
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

    public static boolean isSameDay(Date date1,Date date2){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);

        return calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
    }

    public static long differenceInTimeUnit(Date date1, Date date2, TimeUnit timeUnit){
        long differenceMilliseconds = date2.getTime()-date1.getTime();
        return timeUnit.convert(differenceMilliseconds,TimeUnit.MILLISECONDS);
    }
}
