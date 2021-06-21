package locations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LocationTest {

    LocationParser parser;

    @BeforeEach
    void setUp() {
        parser = new LocationParser();
    }

    @Test
    @DisplayName("Test if Location is correctly created.")
    void testCreate() {
        Location location = new Location("Budapest", 47.497912, 19.040235);

        Assertions.assertEquals("Budapest", location.getName());
        Assertions.assertEquals(47.497912, location.getLat());
        Assertions.assertEquals(19.040235, location.getLon());
    }

    @Test
    @DisplayName("Test if Exception is thrown when latitude is too low.")
    void testCreateWithTooLowLatitude() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Location("FakeTown", -91.0, 19.040235));
        Assertions.assertEquals("Wrong parameter!", ex.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown when latitude is too high.")
    void testCreateWithTooHighLatitude() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Location("FakeTown", +91.0, 19.040235));
        Assertions.assertEquals("Wrong parameter!", ex.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown when longitude is too low.")
    void testCreateWithTooLowLongitude() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Location("FakeTown", 19.040235, -181.0));
        Assertions.assertEquals("Wrong parameter!", ex.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown when longitude is too high.")
    void testCreateWithTooHighLongitude() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> new Location("FakeTown", 19.040235, +181.0));
        Assertions.assertEquals("Wrong parameter!", ex.getMessage());
    }

    @Test
    @DisplayName("Test if Location is correctly parsed from text.")
    void testParse() {
        Location location = parser.parse("Budapest,47.497912,19.040235");

        Assertions.assertEquals("Budapest", location.getName());
        Assertions.assertEquals(47.497912, location.getLat());
        Assertions.assertEquals(19.040235, location.getLon());
    }

    @Test
    @DisplayName("Test if Exception is thrown, when parameter text has two parts.")
    void testParseWithWrongParameter1() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parse("Budapest47.497912,19.040235"));
        Assertions.assertEquals("Something is wrong with parameter text.", ex.getMessage());
    }

    @Test
    @DisplayName("Test if Exception is thrown, when double numbers in parameter are wrong.")
    void testParseWithWrongParameter2() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> parser.parse("Budapest,latitude=47.497912,longitude=19.040235"));
        Assertions.assertEquals("Something is wrong with parameter text.", ex.getMessage());
    }

    @Test
    @DisplayName("Test if method returns true when town is on the Equator.")
    void testIsOnEquator() {
        Location location = new Location("Macapá", 0.0, -51.205999);

        Assertions.assertTrue(parser.isOnEquator(location));
    }

    @Test
    @DisplayName("Test if method returns false when town is not on the Equator.")
    void testIsOnEquatorNotOnEquator() {
        Location location = new Location("Budapest", 47.497912, 19.040235);

        Assertions.assertFalse(parser.isOnEquator(location));
    }

    @Test
    @DisplayName("Test if method returns true when town is on the Prime Meridian.")
    void testIsOnPrimeMeridian() {
        Location location = new Location("Greenwich", 51.2840, 0.0);

        Assertions.assertTrue(parser.isOnPrimeMeridian(location));
    }

    @Test
    @DisplayName("Test if method returns false when town is not on the Prime Meridian.")
    void testIsOnPrimeMeridianNotOnPrimeMeridian() {
        Location location = new Location("Budapest", 47.497912, 19.040235);

        Assertions.assertFalse(parser.isOnPrimeMeridian(location));
    }

    @Test
    @DisplayName("Test if method returns two different objects when invocated twice.")
    void testTwoDifferentParsedObjects() {
        Location firstLocation = parser.parse("Budapest,47.497912,19.040235");
        Location secondLocation = parser.parse("Budapest,47.497912,19.040235");

        Assertions.assertFalse(firstLocation.equals(secondLocation));
    }

    @Test
    @DisplayName("Test if distance is correctly calculated.")
    void testDistanceFrom() {
        Location location = new Location("Budapest",47.497912,19.040235);
        Location other = new Location("Szeged", 46.27015, 20.12793);

        Assertions.assertEquals(160.08, location.distanceFrom(other), 5.0);
    }
}
