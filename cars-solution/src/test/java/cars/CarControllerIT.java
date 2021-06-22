package cars;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CarControllerIT {

    @Autowired
    CarController controller;

    @Test
    void testGetCars() {

        String carsString = controller.getCars().toString();

        Assertions.assertThat(carsString).contains("Opel");
        Assertions.assertThat(carsString).contains("13");
    }

    @Test
    void testGetTypes() {

        String typesString = controller.getTypes().toString();

        Assertions.assertThat(typesString).contains("Zafira");
        Assertions.assertThat(typesString).contains("Ford");
    }
}
