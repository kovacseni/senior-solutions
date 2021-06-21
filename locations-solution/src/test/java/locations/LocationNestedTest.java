package locations;

import org.junit.jupiter.api.*;

public class LocationNestedTest {

    LocationParser parser;

    @BeforeEach
    void setUp() {
        parser = new LocationParser();
    }

    @Nested
    class FirstFavouriteLocation {

        Location favouriteLocation;

        @BeforeEach
        void setUp() {
            favouriteLocation = new Location("In The Middle Of Sea Near Africa", 0.0, 0.0);
        }

        @Test
        @DisplayName("Test location is on the Equator.")
        void testIsOnEquator() {
            Assertions.assertTrue(parser.isOnEquator(favouriteLocation));
        }

        @Test
        @DisplayName("Test location is on the Prime Meridian.")
        void testIsOnPrimeMeridian() {
            Assertions.assertTrue(parser.isOnPrimeMeridian(favouriteLocation));
        }
    }

    @Nested
    class SecondFavouriteLocation {

        Location favouriteLocation;

        @BeforeEach
        void setUp() {
            favouriteLocation = new Location("Budapest", 47.497912,19.040235);
        }

        @Test
        @DisplayName("Test location is not on the Equator.")
        void testIsOnEquatorNotOnEquator() {
            Assertions.assertFalse(parser.isOnEquator(favouriteLocation));
        }

        @Test
        @DisplayName("Test location is not on the Prime Meridian.")
        void testIsOnPrimeMeridianNotOnPrimeMeridian() {
            Assertions.assertFalse(parser.isOnPrimeMeridian(favouriteLocation));
        }
    }
}
