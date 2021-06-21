package locationsspringlegacy;

public class Location {

    private long id;
    private String name;
    private double lat;
    private double lon;

    public Location() {
    }

    public Location(String name) {
        this.name = name;
    }

    public Location(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public Location(long id, String name, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return name;
    }
}
