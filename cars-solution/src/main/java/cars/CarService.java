package cars;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private List<Car> cars = new ArrayList<>(Arrays.asList(new Car("Opel", "Zafira", 18, Condition.NORMAL),
                                                           new Car("Fiat", "Scudo", 13, Condition.NORMAL),
                                                           new Car("Ford", "S-Max", 15, Condition.NORMAL)));

    public List<Car> getCars() {
        return new ArrayList<>(cars);
    }

    public List<String> getTypes() {
        return cars.stream()
                .map(car -> car.getBrand() + " " + car.getType())
                .collect(Collectors.toList());
    }
}
