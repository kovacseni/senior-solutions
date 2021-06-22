package cars;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @Mock
    CarService service;

    @InjectMocks
    CarController controller;

    @Test
    void testGetCars() {
        when(service.getCars()).thenReturn(new ArrayList<>(Arrays.asList(new Car("Opel", "Zafira", 18, Condition.NORMAL),
                                                                         new Car("Fiat", "Scudo", 13, Condition.NORMAL),
                                                                         new Car("Ford", "S-Max", 15, Condition.NORMAL))));

        String carsString = service.getCars().toString();

        Assertions.assertThat(carsString).contains("Opel");
        Assertions.assertThat(carsString).contains("13");

        verify(service).getCars();
    }

    @Test
    void testGetTypes() {
        when(service.getTypes()).thenReturn(new ArrayList<>(Arrays.asList("Opel Zafira", "Fiat Scudo", "Ford S-Max")));

        String typesString = service.getTypes().toString();

        Assertions.assertThat(typesString).contains("Zafira");
        Assertions.assertThat(typesString).contains("Ford");

        verify(service).getTypes();
    }
}
