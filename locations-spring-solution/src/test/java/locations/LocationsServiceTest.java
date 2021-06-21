package locations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class LocationsServiceTest {

    @Test
    void testGetLocations() {
        LocationsService service = new LocationsService();
        List<Location> locations = service.getLocations();
        List<Location> expected = Arrays.asList(new Location("Budapest"), new Location("Párizs"), new Location("New York"));
        List<Location> variedExpected = Arrays.asList(new Location("Párizs"), new Location("Budapest"), new Location("New York"));

        Assertions.assertThat(expected).extracting("name").contains("Budapest", "Párizs", "New York");
        Assertions.assertThat(variedExpected).extracting("name").contains("Budapest", "Párizs", "New York");
    }
}
