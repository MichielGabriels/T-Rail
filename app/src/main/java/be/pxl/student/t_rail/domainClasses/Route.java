package be.pxl.student.t_rail.domainClasses;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import be.pxl.student.t_rail.services.TimeService;

public class Route implements Serializable {

    private String stationDeparture;
    private String stationArrival;
    private String timeDeparture;
    private String timeArrival;
    private String delayDeparture;
    private String delayArrival;
    private int platformDeparture;
    private int platformArrival;
    private Vehicle vehicle;
    private String date;


    public Route(String stationDeparture,String stationArrival,String timeDeparture,String timeArrival
            ,String delayDeparture,String delayArrival,int platformDeparture,int platformArrival,String trainId){
        setStationDeparture(stationDeparture);
        setStationArrival(stationArrival);
        setTimeDeparture(timeDeparture);
        setTimeArrival(timeArrival);
        setDelayDeparture(delayDeparture);
        setDelayArrival(delayArrival);
        setPlatformDeparture(platformDeparture);
        setPlatformArrival(platformArrival);
        setVehicle(new Vehicle(trainId));
    }


    public Route(JSONObject jsonObject){
        try{
            JSONObject departureObject = jsonObject.getJSONObject("departure");
            JSONObject arrivalObject = jsonObject.getJSONObject("arrival");

            setStationDeparture(departureObject.getString("station"));
            setStationArrival(arrivalObject.getString("station"));
            setTimeDeparture(TimeService.extractTimeFromJsonObject(departureObject.getString("time")));
            setTimeArrival(TimeService.extractTimeFromJsonObject(arrivalObject.getString("time")));
            setDelayDeparture(departureObject.getString("delay"));
            setDelayArrival(arrivalObject.getString("delay"));
            setPlatformDeparture(departureObject.getInt("platform"));
            setPlatformArrival(arrivalObject.getInt("platform"));
            vehicle = new Vehicle(departureObject.getString("vehicle"));
        }

        catch (JSONException ex){
            System.out.println(ex.getMessage());
        }
    }

    public String getDelayDeparture() {
        return delayDeparture;
    }

    public String getDelayArrival() {
        return delayArrival;
    }

    public String getStationArrival() {
        return stationArrival;
    }

    public String getStationDeparture() {
        return stationDeparture;
    }

    public String getTimeArrival() {
        return timeArrival;
    }

    public String getTimeDeparture() {
        return timeDeparture;
    }

    public int getPlatformArrival() {
        return platformArrival;
    }

    public int getPlatformDeparture() {
        return platformDeparture;
    }

    public void setStationArrival(String stationArrival) {
        this.stationArrival = stationArrival;
    }

    public void setDelayArrival(String delayArrival) {
        this.delayArrival = "+" + TimeService.convertDelayFromSecondsToMinutes(delayArrival);
    }

    public void setDelayDeparture(String delayDeparture) {
        this.delayDeparture = "+" + TimeService.convertDelayFromSecondsToMinutes(delayDeparture);
    }

    public void setStationDeparture(String stationDeparture) {
        this.stationDeparture = stationDeparture;
    }

    public void setTimeArrival(String timeArrival) {
        this.timeArrival = timeArrival;
    }

    public void setTimeDeparture(String timeDeparture) {
        this.timeDeparture = timeDeparture;
    }

    public void setPlatformArrival(int platformArrival) {
        this.platformArrival = platformArrival;
    }

    public void setPlatformDeparture(int platformDeparture) {
        this.platformDeparture = platformDeparture;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
