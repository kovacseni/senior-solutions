package locations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/locations")
@Tag(name = "Web operations on locations")
public class LocationsController {

    private LocationsService service;

    public LocationsController(LocationsService service) {
        this.service = service;
    }

    @GetMapping//(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Gets all locations in a list")
    @ApiResponse(responseCode = "200", description = "Query of locations was successful")
    public List<LocationDto> getLocations(@RequestParam Optional<String> namePrefix) {
        return service.getLocations(namePrefix);
    }

    /*
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Gets all locations in a list")
    @ApiResponse(responseCode = "200", description = "Query of locations was successful")
    public LocationsDto getLocations(@RequestParam Optional<String> namePrefix) {
        return new LocationsDto(service.getLocations(namePrefix));
    }
    */

    @GetMapping(value = "/{id}" /*, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}*/ )
    @Operation(summary = "Finds one exact location by its ID")
    @ApiResponse(responseCode = "200", description = "Location has been found")
    @ApiResponse(responseCode = "404", description = "Location has not been found")
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
    @Operation(summary = "Gets all locations in a list, which fulfil the conditions")
    @ApiResponse(responseCode = "200", description = "Query of location(s) was successful")
    public List<LocationDto> getLocationsByNameLatLon(@RequestParam Optional<String> prefix,
                                                      @RequestParam Optional<Double> minLat,
                                                      @RequestParam Optional<Double> maxLat,
                                                      @RequestParam Optional<Double> minLon,
                                                      @RequestParam Optional<Double> maxLon) {
        return service.getLocationsByNameLatLon(prefix, minLat, maxLat, minLon, maxLon);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a location")
    @ApiResponse(responseCode = "201", description = "Location has been created")
    public LocationDto createLocation(@Valid @RequestBody CreateLocationCommand command) {
        return service.createLocation(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Changes the status of an exact location")
    @ApiResponse(responseCode = "200", description = "Changing of status was successful")
    public LocationDto updateLocation(@PathVariable("id") long id, @Valid @RequestBody UpdateLocationCommand command) {
        return service.updateLocation(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes one exact location")
    @ApiResponse(responseCode = "204", description = "Deletion of location was successful")
    public void deleteLocation(@PathVariable("id") long id) {
        service.deleteLocation(id);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Operation(summary = "Handles exception when location is not found")
    @ApiResponse(responseCode = "404", description = "Query of location(s) was unsuccessful")
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Problem> handleValidException(MethodArgumentNotValidException exception) {
        List<Violation> violations = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        Problem problem =
                Problem.builder().withType(URI.create("locations/not-valid"))
                .withTitle("Validation error")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(exception.getMessage())
                .with("violations", violations)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
