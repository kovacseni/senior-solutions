package locations;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class LocationsService {

    private List<Location> locations = Arrays.asList(new Location("Budapest"), new Location("Párizs"), new Location("New York"));

    public List<Location> getLocations() {
        return new ArrayList<>(locations);
    }
}
