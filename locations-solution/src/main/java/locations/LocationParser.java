package locations;

public class LocationParser {

    public Location parse(String text) {
        try {
            String[] parts = text.split(",");
            double lat = Double.parseDouble(parts[1]);
            double lon = Double.parseDouble(parts[2]);

            return new Location(parts[0], lat, lon);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Something is wrong with parameter text.", ex);
        }
    }

    public boolean isOnEquator(Location location) {
        return (location.getLat() == 0);
    }

    public boolean isOnPrimeMeridian(Location location) {
        return (location.getLon() == 0);
    }
}
