package covid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CovidService {

    private RegistrationRepository registrationRepository;
    private DateReservationRepository reservationRepository;
    private ModelMapper modelMapper;
    private AtomicLong idGenerator = new AtomicLong();

    public CovidService(RegistrationRepository registrationRepository, DateReservationRepository reservationRepository, ModelMapper modelMapper) {
        this.registrationRepository = registrationRepository;
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
    }

    public String sayHello() {
        return "Üdvözöljük a koronavírus elleni oltás regisztrációs oldalán!";
    }

    public PersonDto createPerson(CreatePersonCommand command) {
        Person person = new Person(idGenerator.incrementAndGet(),
                command.getName(), command.getTaj(), command.getDateOfBirth(), command.getPlaceOfBirth(), command.getAddress());
        registrationRepository.registrate(person);
        return modelMapper.map(person, PersonDto.class);
    }

    public List<PersonDto> getRegistrated() {
        Type targetListType = new TypeToken<List<PersonDto>>() {}.getType();
        List<Person> registrated = registrationRepository.getRegistrated();
        return modelMapper.map(registrated, targetListType);
    }

    public PersonDto findExactPerson(String taj) {
        Person exactPerson = registrationRepository.getRegistrated().stream()
                .filter(person -> person.getTaj().equals(taj))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no registration with this TAJ-number!"));
        return modelMapper.map(exactPerson, PersonDto.class);
    }

    public DateReservationDto reserveDate(DateReservationCommand command) {
        String taj = command.getTaj();
        VaccineType type = VaccineType.valueOf(command.getTypeOfVaccine());
        String placeOfVaccination = command.getPlaceOfVaccination();
        String[] temp = command.getDateTime().split(" ");
        LocalDateTime dateToReserve = LocalDateTime.of(LocalDate.parse(temp[0]), LocalTime.parse(temp[1]));


        DateReservation reservation = new DateReservation(taj, type, placeOfVaccination, dateToReserve);
        reservationRepository.reserveDate(reservation);
        return modelMapper.map(reservation, DateReservationDto.class);
    }

    public List<DateReservationDto> getReservedDates() {
        Type targetListType = new TypeToken<List<DateReservationDto>>() {}.getType();
        List<DateReservation> reservedDates = reservationRepository.getReservedDates();
        return modelMapper.map(reservedDates, targetListType);
    }

    public List<DateReservationDto> getReservedDatesByExactPerson(String taj) {
        Type targetListType = new TypeToken<List<DateReservationDto>>() {}.getType();
        List<DateReservation> reservedDatesOfExactPerson = reservationRepository.getReservedDates().stream()
                .filter(reservation -> reservation.getTaj().equals(taj))
                .collect(Collectors.toList());
        return modelMapper.map(reservedDatesOfExactPerson, targetListType);
    }
}
