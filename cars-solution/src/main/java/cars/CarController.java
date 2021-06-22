package cars;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {

    private CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping("/cars")
    public List<Car> getCars() {
        return service.getCars();
    }

    @GetMapping("/types")
    public List<String> getTypes() {
        return service.getTypes();
    }
}
