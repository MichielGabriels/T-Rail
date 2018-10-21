package be.pxl.student.t_rail.domainClasses;

public class Vehicle {

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
