package locations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationRepository {

    private List<Location> locations = new ArrayList<>();

    public List<Location> getLocations() {
        return new ArrayList<>(locations);
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public Optional<Location> findByName(String name) {
        return locations.stream()
                .filter(location -> location.getName().equals(name))
                .findFirst();
    }
}
