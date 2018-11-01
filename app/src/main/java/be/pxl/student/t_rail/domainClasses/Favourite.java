package be.pxl.student.t_rail.domainClasses;

public class Favourite {

    String favouriteId;
    String fromStation;
    String toStation;

    public Favourite() {

    }

    public Favourite(String favouriteId, String fromStation, String toStation) {
        this.favouriteId = favouriteId;
        this.fromStation = fromStation;
        this.toStation = toStation;
    }

    public String getFavouriteId() {
        return favouriteId;
    }

    public String getFromStation() {
        return fromStation;
    }

    public String getToStation() {
        return toStation;
    }
}
