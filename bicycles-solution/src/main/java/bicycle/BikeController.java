package bicycle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BikeController {

    private BikeService service;

    public BikeController(BikeService service) {
        this.service = service;
    }

    @GetMapping("/history")
    public List<Bike> getBikes() {
        return service.getBikes();
    }

    @GetMapping("/users")
    public List<String> getUserIds() {
        return service.getUserIds();
    }
}
