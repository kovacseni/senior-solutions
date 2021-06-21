package locations;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LocationOperatorsTest {

    List<Location> locations;
    LocationOperators operator;
    LocationParser parser;

    @BeforeEach
    void setUp() {
        operator = new LocationOperators();

        parser = new LocationParser();

        locations = new ArrayList<>();
        locations.add(new Location("Budapest", 47.497912, 19.040235));
        locations.add(new Location("Párizs", 48.87376, 2.25120));
        locations.add(new Location("Sydney", -33.88223, 151.33140));
        locations.add(new Location("New York", 40.76544, -73.93449));
        locations.add(new Location("Fokváros", -33.85148, 18.51842));
    }

    @Test
    @DisplayName("Test if only such cities in the list which are on northern hemisphere.")
    void testFilterOnNorth() {
        List<Location> northernLocations = operator.filterOnNorth(locations);

        Assertions.assertEquals(3, northernLocations.size());
        Assertions.assertEquals("Budapest", northernLocations.get(0).getName());
        Assertions.assertEquals("Párizs", northernLocations.get(1).getName());
        Assertions.assertEquals("New York", northernLocations.get(2).getName());
    }

    @DisplayName("Repeated test if Locations are on Equator.")
    @RepeatedTest(value = 5, name = "Is on Equator? {currentRepetition}/{totalRepetitions}")
    void testIsOnEquatorRepeated(RepetitionInfo info) {
        boolean[] values = new boolean[]{false, false, true, false, true};

        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Budapest", 47.497912, 19.040235));
        locations.add(new Location("Greenwich", 51.2840, 0.0));
        locations.add(new Location("Macapá", 0.0, -51.205999));
        locations.add(new Location("Sydney", -33.88223, 151.33140));
        locations.add(new Location("In The Middle Of Sea Near Africa", 0.0, 0.0));

        Assertions.assertEquals(values[info.getCurrentRepetition() - 1], parser.isOnEquator(locations.get(info.getCurrentRepetition() - 1)));
    }

    @DisplayName("Repeated test if Locations are on Prime Meridian.")
    @RepeatedTest(value = 5, name = "Is on Prime Meridian? {currentRepetition}/{totalRepetitions}")
    void testIsOnPrimeMeridianRepeated(RepetitionInfo info) {
        boolean[] values = new boolean[]{false, false, true, false, true};

        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Budapest", 47.497912, 19.040235));
        locations.add(new Location("Macapá", 0.0, -51.205999));
        locations.add(new Location("Greenwich", 51.2840, 0.0));
        locations.add(new Location("Sydney", -33.88223, 151.33140));
        locations.add(new Location("In The Middle Of Sea Near Africa", 0.0, 0.0));

        Assertions.assertEquals(values[info.getCurrentRepetition() - 1], parser.isOnPrimeMeridian(locations.get(info.getCurrentRepetition() - 1)));
    }

    @DisplayName("Parameterized test if Locations are on Prime Meridian.")
    @ParameterizedTest
    @MethodSource("createArguments")
    void testIsOnPrimeMeridianParameterized(Location location, boolean isThere) {
        Assertions.assertEquals(isThere, parser.isOnPrimeMeridian(location));
    }

    static Stream<Arguments> createArguments() {
        return Stream.of(
                arguments(new Location("Budapest", 47.497912, 19.040235), false),
                arguments(new Location("Macapá", 0.0, -51.205999), false),
                arguments(new Location("Greenwich", 51.2840, 0.0), true),
                arguments(new Location("Sydney", -33.88223, 151.33140), false),
                arguments(new Location("In The Middle Of Sea Near Africa", 0.0, 0.0), true)

        );
    }

    @DisplayName("Parameterized test if distance of Locations is correct is CSV file.")
    @ParameterizedTest
    @CsvFileSource(resources = "/locations.csv")
    void testDistanceFrom(String coordinates) {
        String[] temp = coordinates.split(";");
        Location firstLocation = new Location("Budapest", Double.parseDouble(temp[0]), Double.parseDouble(temp[1]));
        Location secondLocation = new Location("Szeged", Double.parseDouble(temp[2]), Double.parseDouble(temp[3]));
        double distance = Double.parseDouble(temp[4]);

        Assertions.assertEquals(distance, firstLocation.distanceFrom(secondLocation), 5.0);
    }

    @DisplayName("Dynamic test cases if Locations are on Equator.")
    @TestFactory
    Stream<DynamicTest> testIsOnEquatorDynamic() {
        return locations.stream()
               .map(location -> DynamicTest.dynamicTest(location.getName() + " nincs rajta az Egyenlítőn.", () -> Assertions.assertFalse(parser.isOnEquator(location))));
    }
}
