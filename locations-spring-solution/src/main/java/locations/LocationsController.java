package locations;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
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
    public LocationDto findLocationById(@PathVariable("id") long id) {
            return service.findLocationById(id);
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity findLocationById(@PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(service.findLocationById(id));
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.notFound().build();
        }
    }
    */

    @GetMapping("/minmax")
    public List<LocationDto> getLocationsByNameLatLon(@RequestParam Optional<String> prefix,
                                                      @RequestParam Optional<Double> minLat,
                                                      @RequestParam Optional<Double> maxLat,
                                                      @RequestParam Optional<Double> minLon,
                                                      @RequestParam Optional<Double> maxLon) {
        return service.getLocationsByNameLatLon(prefix, minLat, maxLat, minLon, maxLon);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto createLocation(@RequestBody CreateLocationCommand command) {
        return service.createLocation(command);
    }

    @PutMapping("/{id}")
    public LocationDto updateLocation(@PathVariable("id") long id, @RequestBody UpdateLocationCommand command) {
        return service.updateLocation(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable("id") long id) {
        service.deleteLocation(id);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(LocationNotFoundException lnfe) {
        Problem problem =
                Problem.builder()
                .withType(URI.create("locations/not-found"))
                .withTitle("Not found")
                .withStatus(Status.NOT_FOUND)
                .withDetail(lnfe.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
