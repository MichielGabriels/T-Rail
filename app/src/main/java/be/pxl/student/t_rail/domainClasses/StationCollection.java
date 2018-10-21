package be.pxl.student.t_rail.domainClasses;

import java.util.ArrayList;

public class StationCollection {

    private static ArrayList<String> stations;


    public static ArrayList<String> getStations(){
        return stations;
    }

    public static void initStations(ArrayList<String> _stations){
        stations = _stations;
    }
}
