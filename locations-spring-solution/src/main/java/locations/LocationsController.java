package locations;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationsController {

    private LocationsService service;

    public LocationsController(LocationsService service) {
        this.service = service;
    }

    @GetMapping
    public List<LocationDto> getLocations(@RequestParam Optional<String> namePrefix) {
        return service.getLocations(namePrefix);
    }

    @GetMapping("/{id}")
    public LocationDto getLocationById(@PathVariable("id") long id) {
        return service.getLocationById(id);
    }

    @GetMapping
    public List<LocationDto> getLocationsByNameLatLon(@RequestParam Optional<String> prefix,
                                                      @RequestParam Optional<Double> minLat,
                                                      @RequestParam Optional<Double> maxLat,
                                                      @RequestParam Optional<Double> minLon,
                                                      @RequestParam Optional<Double> maxLon) {
        return service.getLocationsByNameLatLon(prefix, minLat, maxLat, minLon, maxLon);
    }
}
