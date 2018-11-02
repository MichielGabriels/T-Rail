package be.pxl.student.t_rail.domainClasses;

import java.io.Serializable;

public class Vehicle implements Serializable {

    private String vehicleId;

    public Vehicle(String id){
        setVehicleId(id);
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleId() {
        return vehicleId;
    }
}
