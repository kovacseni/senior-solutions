package locations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationsControllerTest {

    @Mock
    LocationsService service;

    @InjectMocks
    LocationsController controller;

    @Test
    void testGetLocations() {
        when(service.getLocations()).thenReturn(Arrays.asList(new LocationDto("Bagdad"), new LocationDto("Peking")));

        String locationsString = controller.getLocations().toString();

        Assertions.assertThat(locationsString).contains("Peking");
    }
}
