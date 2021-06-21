package locations;

import static org.hamcrest.CoreMatchers.equalTo;

import org.assertj.core.api.Condition;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;

public class LocationServiceTest {

    LocationService service = new LocationService();

    @TempDir
    Path tempDir;

    @DisplayName("Test of writing file.")
    @Test
    void testWriteLocations() throws IOException {
        Path path = tempDir.resolve("testlocations.csv");

        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Párizs", 48.87376, 2.25120));
        locations.add(new Location("Budapest", 47.497912, 19.040235));
        locations.add(new Location("Sydney", -33.88223, 151.33140));
        locations.add(new Location("New York", 40.76544, -73.93449));
        locations.add(new Location("Fokváros", -33.85148, 18.51842));

        service.writeLocations(path, locations);

        List<String> lines = Files.readAllLines(path);

        Assertions.assertEquals("Budapest,47.497912,19.040235", lines.get(1));
    }

    @DisplayName("Test of reading file.")
    @Test
    void testReadLocations() throws IOException {
        Path path = Path.of("src/main/resources/favouritelocations.csv");

        List<Location> locations = service.readLocations(path);

        Assertions.assertEquals("Budapest", locations.get(1).getName());
        Assertions.assertEquals(-33.88223, locations.get(2).getLat());
        Assertions.assertEquals(-73.93449, locations.get(3).getLon());
    }

    @DisplayName("Test of reading file with Hamcrest matchers.")
    @Test
    void testReadLocationsWithHamcrest() throws IOException {
        Path path = Path.of("src/main/resources/favouritelocations.csv");

        List<Location> locations = service.readLocations(path);

        MatcherAssert.assertThat(locations.get(1).getName(), equalTo("Budapest"));
        MatcherAssert.assertThat(locations.get(2).getLat(), equalTo(-33.88223));
        MatcherAssert.assertThat(locations.get(3).getLon(), equalTo(-73.93449));
        MatcherAssert.assertThat(locations.size(), equalTo(5));
    }

    @DisplayName("Test if one of Location's coordinates is zero with own Hamcrest matcher.")
    @Test
    void testWithOwnHamcrestMatcher() {
        Location location = new Location("Budapest", 47.497912, 19.040235);

        MatcherAssert.assertThat(location, LocationWithZeroCoordinate.zeroCoordinate(equalTo(false)));
    }

    @DisplayName("Test of reading file with AssertJ matchers.")
    @Test
    void testReadLocationsWithAssertJ() throws IOException {
        Path path = Path.of("src/main/resources/favouritelocations.csv");

        List<Location> locations = service.readLocations(path);

        org.assertj.core.api.Assertions.assertThat(locations.get(1).getName().equals("Budapest"));
        org.assertj.core.api.Assertions.assertThat(locations).hasSize(5);
        org.assertj.core.api.Assertions.assertThat(locations.get(1).getLat()).isEqualTo(47.497912);
    }

    @DisplayName("Test if one of Location's coordinates is zero with AssertJ.")
    @Test
    void testWithOwnAssertJassert() {
        Location location = new Location("Budapest", 47.497912, 19.040235);

        Condition<Location> zeroCoordinate = new Condition<>(l -> l.getLat() != 0.0, "coordinate not 0.0");
        org.assertj.core.api.Assertions.assertThat(location).has(zeroCoordinate);
    }

    @DisplayName("Test if Location is in the list.")
    @Test
    void testCalculateDistance() {

        LocationRepository repository = new LocationRepository();

        repository.addLocation(new Location("Párizs", 48.87376, 2.25120));
        repository.addLocation(new Location("Budapest", 47.497912, 19.040235));
        repository.addLocation(new Location("Sydney", -33.88223, 151.33140));
        repository.addLocation(new Location("New York", 40.76544, -73.93449));
        repository.addLocation(new Location("Fokváros", -33.85148, 18.51842));

        DistanceService distanceService = new DistanceService(repository);

        Optional<Double> distance = distanceService.calculateDistance("Budapest", "Párizs");
        Optional<Double> wrongDistance = distanceService.calculateDistance("Budapest", "Szeged");

        Assertions.assertEquals(1248.84, distance.get(), 5.0);
        Assertions.assertTrue(wrongDistance.equals(Optional.empty()));
    }

    @DisplayName("Test with mocking repository.")
    @Test
    void testCalculateDistanceMock() {
        LocationRepository repository = Mockito.mock(LocationRepository.class);
        DistanceService distanceService = new DistanceService(repository);
        Optional<Double> distance1 = distanceService.calculateDistance("Budapest", "Párizs");
        Optional<Double> distance2 = distanceService.calculateDistance("Budapest", "Szeged");

        Assertions.assertTrue(distance1.equals(Optional.empty()));
        Assertions.assertTrue(distance2.equals(Optional.empty()));
    }

    @DisplayName("Test case only for the reason that testing this method is missing.")
    @Test
    void testGetLocations() {
        LocationRepository repository = new LocationRepository();
        List<Location> locations1 = repository.getLocations();

        Assertions.assertEquals(0, locations1.size());

        repository.addLocation(new Location("Budapest", 47.497912, 19.040235));
        List<Location> locations2 = repository.getLocations();

        Assertions.assertEquals(1, locations2.size());
        Assertions.assertEquals("Budapest", locations2.get(0).getName());
    }
}
