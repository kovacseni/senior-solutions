package locations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationsController {

    private LocationsService service;

    public LocationsController(LocationsService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getLocations() {
        return service.getLocations().toString() + "szia"
                ;
    }
}
