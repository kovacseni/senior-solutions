package bicycle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BikeControllerIT {

    @Autowired
    BikeService service;

    @Test
    void testGetBikes() {

        List<Bike> expected = service.getBikes();

        Assertions.assertEquals(5, expected.size());
        Assertions.assertEquals("US3334", expected.get(2).getUserId());
        Assertions.assertEquals(1.9, expected.get(3).getKm());
    }

    @Test
    void testGetUserIds() {

        List<String> expected = service.getUserIds();

        Assertions.assertEquals("US3a34", expected.get(1));
        Assertions.assertEquals("US346", expected.get(4));
    }
}
