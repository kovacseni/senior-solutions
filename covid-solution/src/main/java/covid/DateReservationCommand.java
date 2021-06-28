package covid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateReservationCommand {

    private String taj;
    private String typeOfVaccine;
    private String placeOfVaccination;
    private String dateTime;
}
