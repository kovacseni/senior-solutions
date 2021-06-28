package covid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CovidController {

    private CovidService service;

    public CovidController(CovidService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String sayHello() {
        return service.sayHello();
    }

    @GetMapping("/registrations")
    public List<PersonDto> getRegistrated() {
        return service.getRegistrated();
    }

    @PostMapping("/registrate")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto createPerson(@RequestBody CreatePersonCommand command) {
        return service.createPerson(command);
    }

    @GetMapping("/registrations/{taj}")
    public PersonDto findExactPerson(@PathVariable("taj") String taj) {
        return service.findExactPerson(taj);
    }

    @PostMapping("/datereservation")
    @ResponseStatus(HttpStatus.CREATED)
    public DateReservationDto reserveDate(@RequestBody DateReservationCommand command) {
        return service.reserveDate(command);
    }

    @GetMapping("/reservations")
    public List<DateReservationDto> getReservedDates() {
        return service.getReservedDates();
    }

    @GetMapping("/reservations/{taj}")
    public List<DateReservationDto> getReservedDatesByExactPerson(@PathVariable("taj") String taj) {
        return service.getReservedDatesByExactPerson(taj);
    }
}
