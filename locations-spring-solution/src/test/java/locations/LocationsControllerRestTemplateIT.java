package locations;

import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationsControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    LocationsService service;

    @Test
    void testGetLocations() {
        service.deleteAllLocations();

        LocationDto locationDto =
                template.postForObject("/api/locations", new CreateLocationCommand("Róma", 41.90383, 12.50557), LocationDto.class);

        Assertions.assertEquals("Róma", locationDto.getName());
        Assertions.assertEquals(41.90383, locationDto.getLat());
        Assertions.assertEquals(12.50557, locationDto.getLon());

        template.postForObject("/api/locations", new CreateLocationCommand("Athén", 37.97954, 23.72638), LocationDto.class);

        List<LocationDto> locations = template.exchange("/api/locations",
                                                        HttpMethod.GET,
                                                        null,
                                                        new ParameterizedTypeReference<List<LocationDto>>() {})
                                                        .getBody();

        assertThat(locations)
                .extracting(LocationDto::getName)
                .containsExactly("Róma", "Athén");
    }
}
