package be.pxl.student.t_rail.domainClasses;

import org.json.JSONException;
import org.json.JSONObject;

import be.pxl.student.t_rail.services.TimeService;

public class RouteDetail {

    private String station1;
    private String station2;
    private String time1;
    private String time2;
    private String platform1;
    private String platform2;
    private String delay1;
    private String delay2;

    public RouteDetail(String station1, String station2, String time1, String time2, String platform1, String platform2,String delay1,String delay2) {
       this.setStation1(station1);
       this.setStation2(station2);
       this.setTime1(time1);
       this.setTime2(time2);
       this.setPlatform1(platform1);
       this.setPlatform2(platform2);
       this.setDelay1(delay1);
       setDelay2(delay2);
    }

    public RouteDetail(String station1,String time1,String platform1,String delay1){
        setStation1(station1);
        setTime1(time1);
        setPlatform1(platform1);
        setDelay1(delay1);
    }

    public RouteDetail(JSONObject jsonObject) throws JSONException {
        this(jsonObject.getString("station"),TimeService.extractTimeFromJsonObject(jsonObject.getString("time")),jsonObject.getString("platform"),TimeService.convertDelayFromSecondsToMinutes(jsonObject.getString("departureDelay")));
    }

    public String getStation1() {
        return station1;
    }

    public void setStation1(String station1) {
        this.station1 = station1;
    }

    public String getStation2() {
        return station2;
    }

    public void setStation2(String station2) {
        this.station2 = station2;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getPlatform1() {
        return platform1;
    }

    public void setPlatform1(String platform1) {
        this.platform1 = "perron " + platform1;
    }

    public String getPlatform2() {
        return platform2;
    }

    public void setPlatform2(String platform2) {
        this.platform2 = platform2;
    }

    public String getDelay1() {
        return delay1;
    }

    public void setDelay1(String delay1) {
        this.delay1 = "+" + delay1;
    }

    public String getDelay2() {
        return delay2;
    }

    public void setDelay2(String delay2) {
        this.delay2 = delay2;
    }
}
