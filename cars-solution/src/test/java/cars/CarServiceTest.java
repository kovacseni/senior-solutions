package cars;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CarServiceTest {

    CarService service = new CarService();

    @Test
    void testGetCars() {
        List<Car> cars = service.getCars();

        Assertions.assertEquals("Opel", cars.get(0).getBrand());
        Assertions.assertEquals("S-Max", cars.get(2).getType());
        Assertions.assertEquals(3, cars.size());
    }

    @Test
    void testGetTypes() {
        List<String> carTypes = service.getTypes();

        Assertions.assertEquals("Opel Zafira", carTypes.get(0));
        Assertions.assertEquals("Fiat Scudo", carTypes.get(1));
        Assertions.assertEquals("Ford S-Max", carTypes.get(2));
        Assertions.assertEquals(3, carTypes.size());
    }
}
