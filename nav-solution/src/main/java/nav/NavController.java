package nav;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class NavController {

    private NavService service;

    public NavController(NavService service) {
        this.service = service;
    }

    @GetMapping("/api/types")
    public List<CaseTypeDto> getCaseTypes() {
        return service.getCaseTypes();
    }

    @PostMapping("/api/appointments")
    public AppointmentDto reserveNewAppointment(@Valid @RequestBody ReserveNewAppointmentCommand command) {
        return service.reserveNewAppointment(command);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleValidationException() {
        throw new NavValidationFailException();
    }
}
