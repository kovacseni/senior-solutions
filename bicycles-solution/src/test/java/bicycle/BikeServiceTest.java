package bicycle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class BikeServiceTest {

    BikeService service = new BikeService();

    @Test
    void testGetBikesEmptyList() {
        List<Bike> expected = service.getBikes();

        Assertions.assertEquals(5, expected.size());
        Assertions.assertEquals("US3334", expected.get(2).getUserId());
        Assertions.assertEquals(1.9, expected.get(3).getKm());
    }

    @Test
    void testGetBikesNotEmptyList() {
        service.addBike(new Bike("ABC", "DEF", LocalDateTime.of(2021, 6, 25, 13, 42), 2.1));
        service.addBike(new Bike("GHI", "JKL", LocalDateTime.of(2021, 5, 2, 16, 35), 3.2));
        List<Bike> expected = service.getBikes();

        Assertions.assertEquals(2, expected.size());
        Assertions.assertEquals("DEF", expected.get(0).getUserId());
        Assertions.assertEquals(3.2, expected.get(1).getKm());
    }

    @Test
    void testGetUserIds() {
        List<String> expected = service.getUserIds();

        Assertions.assertEquals("US3a34", expected.get(1));
        Assertions.assertEquals("US346", expected.get(4));
    }
}
