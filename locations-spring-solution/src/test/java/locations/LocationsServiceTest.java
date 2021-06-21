package locations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class LocationsServiceTest {

    @Test
    void testGetLocations() {
        LocationsService service = new LocationsService(new ModelMapper());
        List<LocationDto> locations = service.getLocations();

        Assertions.assertThat(locations).extracting("name").contains("Budapest", "Párizs", "New York");
    }
}
