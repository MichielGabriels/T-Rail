package be.pxl.student.t_rail.domainClasses;

public class RouteDetail {

    private String station1;
    private String station2;
    private String time1;
    private String time2;
    private String platform1;
    private String platform2;

    public RouteDetail(String station1, String station2, String time1, String time2, String platform1, String platform2) {
       this.setStation1(station1);
       this.setStation2(station2);
       this.setTime1(time1);
       this.setTime2(time2);
       this.setPlatform1(platform1);
       this.setPlatform2(platform2);
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
        this.platform1 = platform1;
    }

    public String getPlatform2() {
        return platform2;
    }

    public void setPlatform2(String platform2) {
        this.platform2 = platform2;
    }
}
