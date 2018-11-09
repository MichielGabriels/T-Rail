package be.pxl.student.t_rail.models;

public class RoutePlanResponseModel {

    private String response;
    private String date;

    public RoutePlanResponseModel(String response,String date){
        setDate(date);
        setResponse(response);
    }

    public RoutePlanResponseModel(){}

    public String getDate() {
        return date;
    }

    public String getResponse() {
        return response;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
