package locations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationsControllerIT {

    @Autowired
    LocationsController controller;

    @Test
    void testGetLocations() {
        String locationsString = controller.getLocations();

        Assertions.assertThat(locationsString).contains("Párizs");
        Assertions.assertThat(locationsString).endsWith("rk]");
    }
}
