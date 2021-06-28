package covid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateReservation {

    private String taj;
    private VaccineType type;
    private String placeOfVaccination;
    private LocalDateTime dateToReserve;
}
