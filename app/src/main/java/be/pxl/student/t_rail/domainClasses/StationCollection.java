package be.pxl.student.t_rail.domainClasses;

import java.util.ArrayList;

public class StationCollection {

    private static ArrayList<String> stations;
    private static ArrayList<String> stationsLowerCase;

    public static ArrayList<String> getStations(boolean lowerCase){
        if(lowerCase){
            return stationsLowerCase;
        }
        return stations;
    }

    public static void initStations(ArrayList<String> _stations){
        stations = _stations;
        stationsLowerCase = new ArrayList<>();
        for(int index = 0;index<stations.size();index++){
            stationsLowerCase.add(stations.get(index).toLowerCase());
        }
    }
}
