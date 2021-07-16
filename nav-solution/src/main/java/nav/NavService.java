package nav;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NavService {

    private List<CaseType> cases = new ArrayList<>(/*Arrays.asList(new CaseType("001", "Adóbevallás"), new CaseType("002", "Befizetés"))*/);
    private ModelMapper modelMapper;
    private AtomicLong idGenerator = new AtomicLong();
    private List<Appointment> reservedAppointments = new ArrayList<>();

    public NavService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void addCaseType(CaseType type) {
        cases.add(type);
    }

    public boolean isValidCaseType(String code) {
        for (CaseType c : cases) {
            if (c.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public List<Appointment> getReservedAppointments() {
        return new ArrayList<>(reservedAppointments);
    }

    public List<CaseTypeDto> getCaseTypes() {
        Type targetListType = new TypeToken<List<CaseTypeDto>>() {
        }.getType();
        return modelMapper.map(cases, targetListType);
    }

    public AppointmentDto reserveNewAppointment(ReserveNewAppointmentCommand command) {
        Appointment appointment = new Appointment(idGenerator.incrementAndGet(), command.getCdv(), command.getInterval(), command.getCode());
        reservedAppointments.add(appointment);
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    public void deleteAll() {
        cases.clear();
        idGenerator = new AtomicLong();
    }
}
