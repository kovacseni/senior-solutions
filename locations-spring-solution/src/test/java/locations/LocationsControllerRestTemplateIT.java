package locations;

import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        service.deleteAllLocations();
    }

    @Test
    void testGetLocations() {
        template.postForObject("/api/locations", new CreateLocationCommand("Róma", 41.90383, 12.50557), LocationDto.class);
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

    @Test
    void testFindLocationById() {
        LocationDto locationDto =
                template.postForObject("/api/locations", new CreateLocationCommand("Róma", 41.90383, 12.50557), LocationDto.class);

        LocationDto expected = template.exchange("/api/locations/1",
                HttpMethod.GET,
                null,
                LocationDto.class)
                .getBody();

        assertThat(expected.getName()).isEqualTo("Róma");
    }

    @Test
    void testGetLocationsByNameLatLon() {
        template.postForObject("/api/locations", new CreateLocationCommand("Róma", 41.90383, 12.50557), LocationDto.class);
        template.postForObject("/api/locations", new CreateLocationCommand("Athén", 37.97954, 23.72638), LocationDto.class);
        template.postForObject("/api/locations", new CreateLocationCommand("Budapest", 47.497912, 19.040235), LocationDto.class);
        template.postForObject("/api/locations", new CreateLocationCommand("Sydney", -33.88223, 151.33140), LocationDto.class);

        List<LocationDto> expected = template.exchange("/api/locations/minmax?minLat=36&maxLon=20",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LocationDto>>() {})
                .getBody();

        assertThat(expected).hasSize(2);
        assertThat(expected).extracting(LocationDto::getName)
                .containsExactly("Róma", "Budapest");
    }

    @Test
    void testCreateLocation() {
        LocationDto locationDto =
                template.postForObject("/api/locations", new CreateLocationCommand("Róma", 41.90383, 12.50557), LocationDto.class);

        Assertions.assertEquals("Róma", locationDto.getName());
        Assertions.assertEquals(41.90383, locationDto.getLat());
        Assertions.assertEquals(12.50557, locationDto.getLon());
    }

    @Test
    void testUpdateLocation() {
        template.postForObject("/api/locations", new CreateLocationCommand("Róma", 41.90383, 12.50557), LocationDto.class);
        template.put("/api/locations/1", new UpdateLocationCommand("Róma", 2.2, 3.3));

        LocationDto expected = template.exchange("/api/locations/1",
                HttpMethod.GET,
                null,
                LocationDto.class)
                .getBody();

        Assertions.assertEquals("Róma", expected.getName());
        Assertions.assertEquals(2.2, expected.getLat());
        Assertions.assertEquals(3.3, expected.getLon());
    }

    @Test
    void testDeleteLocation() {
        template.delete("/api/locations/1");

        List<LocationDto> expected = template.exchange("/api/locations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LocationDto>>() {
                })
                .getBody();

        assertThat(expected).hasSize(0);
    }
}
