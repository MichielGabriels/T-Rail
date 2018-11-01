package be.pxl.student.t_rail.domainClasses;

public class RouteDetailResponseModel {

    private String response;
    private Route selectedRoute;

    public RouteDetailResponseModel(String response,Route selectedRoute){
        setSelectedRoute(selectedRoute);
        setResponse(response);
    }

    public RouteDetailResponseModel(){}

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public Route getSelectedRoute() {
        return selectedRoute;
    }

    public void setSelectedRoute(Route selectedRoute) {
        this.selectedRoute = selectedRoute;
    }
}
