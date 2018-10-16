package be.pxl.student.t_rail.domainClasses;

public class Route {

    private String stationDeparture;
    private String stationArrival;
    private String timeDeparture;
    private String timeArrival;
    private String delayDeparture;
    private String delayArrival;
    private int platformDeparture;
    private int platformArrival;


    public Route(String stationDeparture,String stationArrival,String timeDeparture,String timeArrival
            ,String delayDeparture,String delayArrival,int platformDeparture,int platformArrival){
        setStationDeparture(stationDeparture);
        setStationArrival(stationArrival);
        setTimeDeparture(timeDeparture);
        setTimeArrival(timeArrival);
        setDelayDeparture(delayDeparture);
        setDelayArrival(delayArrival);
        setPlatformDeparture(platformDeparture);
        setPlatformArrival(platformArrival);
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
        this.delayArrival = delayArrival;
    }

    public void setDelayDeparture(String delayDeparture) {
        this.delayDeparture = delayDeparture;
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
}
