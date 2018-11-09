package be.pxl.student.t_rail.domainClasses;

public class RouteNotFoundException extends Exception {

    public RouteNotFoundException(String message){
        super(message);
    }
}
