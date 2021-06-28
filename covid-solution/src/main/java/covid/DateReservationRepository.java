package covid;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class DateReservationRepository {

    private List<DateReservation> reservedDates = new ArrayList<>();

/*    private List<DateReservation> reservedDates = new ArrayList<>(Arrays.asList(new DateReservation("1234", VaccineType.PFIZER, "Budapest", LocalDateTime.of(2021, 4, 5, 13, 40)),
                                                                                new DateReservation("2345", VaccineType.ASTRA_ZENECA, "Győr", LocalDateTime.of(2021, 4, 6, 12, 13)),
                                                                                new DateReservation("1234", VaccineType.PFIZER, "Budapest", LocalDateTime.of(2021, 4, 25, 9, 32))
    )); */

    public List<DateReservation> getReservedDates() {
        return new ArrayList<>(reservedDates);
    }

    public void reserveDate(DateReservation reservation) {
        reservedDates.add(reservation);
    }
}
