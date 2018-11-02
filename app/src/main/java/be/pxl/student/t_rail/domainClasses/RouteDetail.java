package be.pxl.student.t_rail.domainClasses;

import org.json.JSONException;
import org.json.JSONObject;

import be.pxl.student.t_rail.services.TimeService;

public class RouteDetail {

    private String station;
    private String timeArrival;
    private String timeDeparture;
    private String platform;
    private String delayArrival;
    private String delayDeparture;

    public RouteDetail(String station, String station2, String timeArrival, String timeDeparture, String platform, String platform2, String delayArrival, String delay2) {
       this.setStation(station);
       this.setTimeArrival(timeArrival);
       this.setTimeDeparture(timeDeparture);
       this.setDelayArrival(delayArrival);
       setDelayDeparture(delay2);
    }

    public RouteDetail(String station, String timeArrival, String platform, String delayArrival){
        setStation(station);
        setTimeArrival(timeArrival);
        setPlatform(platform);
        setDelayArrival(delayArrival);
    }

    public RouteDetail(JSONObject jsonObject) {
        try{
            extractDetailsFromJsonObject(jsonObject);
        }

        catch (JSONException ex){
            System.out.println(ex.getMessage());
        }
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(String timeArrival) {
        this.timeArrival = timeArrival;
    }

    public String getTimeDeparture() {
        return timeDeparture;
    }

    public void setTimeDeparture(String timeDeparture) {
        this.timeDeparture = timeDeparture;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = "Perron " + platform;
    }

    public String getDelayArrival() {
        return delayArrival;
    }

    public void setDelayArrival(String delayArrival) {
        this.delayArrival = "+" + TimeService.convertDelayFromSecondsToMinutes(delayArrival);
    }

    public String getDelayDeparture() {
        return delayDeparture;
    }

    public void setDelayDeparture(String delayDeparture) {
        this.delayDeparture = "+" + TimeService.convertDelayFromSecondsToMinutes(delayDeparture);
    }

    private void extractDetailsFromJsonObject(JSONObject jsonObject) throws JSONException{
        setStation(jsonObject.getString("station"));
        setPlatform(jsonObject.getString("platform"));
        setDelayArrival(jsonObject.getString("arrivalDelay"));
        setDelayDeparture(jsonObject.getString("departureDelay"));

        String timeArrival = jsonObject.getString("scheduledArrivalTime");
        String timeDeparture = jsonObject.getString("scheduledDepartureTime");
        timeArrival = TimeService.extractTimeFromJsonObject(timeArrival);
        timeDeparture = TimeService.extractTimeFromJsonObject(timeDeparture);
        setTimeDeparture(timeDeparture);
        setTimeArrival(timeArrival);
    }
}
