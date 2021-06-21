package locations;

public class Location {

    private String name;
    private double lat;
    private double lon;

    public Location() {
    }

    public Location(String name, double lat, double lon) {
        validate(lat, lon);
        this.name = name;
        this.lat = lat;
        this.lon = lon;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double distanceFrom(Location other) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(other.lat - this.lat);
        double lonDistance = Math.toRadians(other.lon - this.lon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.lat)) * Math.cos(Math.toRadians(other.lat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        double height = 0;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    private void validate(double lat, double lon) {
        if (lat < -90.0 || lat > 90.0 || lon < -180.0 || lon > 180.0) {
            throw new IllegalArgumentException("Wrong parameter!");
        }
    }
}
