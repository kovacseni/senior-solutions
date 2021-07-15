package nav;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NavControllerIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    NavService service;

    @BeforeEach
    void init() {
        service.deleteAll();

        service.addCaseType(new CaseType("001", "Adóbevallás"));
        service.addCaseType(new CaseType("002", "Befizetés"));
    }

    @Test
    void testGetCaseTypes() {
        List<CaseTypeDto> cases = template.exchange("/api/types",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CaseTypeDto>>() {
                }).getBody();

        assertThat(cases)
                .hasSize(2)
                .extracting(CaseTypeDto::getName)
                .containsExactly("Adóbevallás", "Befizetés");
    }

    @Test
    void testReserveNewAppointment() {
        template.postForObject("/api/appointments", new ReserveNewAppointmentCommand("1111111111", new Interval(
                LocalDateTime.now().plusMinutes(1), LocalDateTime.now().plusMinutes(2)), "001"), AppointmentDto.class);

        List<Appointment> expected = service.getReservedAppointments();

        assertThat(expected)
                .hasSize(1)
                .extracting(Appointment::getCdv, Appointment::getCode)
                .containsExactly(tuple("1111111111", "001"));
    }

    @Test
    void testReserveNewAppointmentWithInvalidCdv1() {
        Problem expected = template.postForObject("/api/appointments", new ReserveNewAppointmentCommand("1234", new Interval(
                LocalDateTime.now().plusMinutes(1), LocalDateTime.now().plusMinutes(2)), "001"), Problem.class);

        assertEquals(Status.BAD_REQUEST, expected.getStatus());
    }

    @Test
    void testReserveNewAppointmentWithInvalidCdv2() {
        Problem expected = template.postForObject("/api/appointments", new ReserveNewAppointmentCommand("1111111112", new Interval(
                LocalDateTime.now().plusMinutes(1), LocalDateTime.now().plusMinutes(2)), "001"), Problem.class);

        assertEquals(Status.BAD_REQUEST, expected.getStatus());
    }

    @Test
    void testReserveNewAppointmentWithInvalidCdv3() {
        Problem expected = template.postForObject("/api/appointments", new ReserveNewAppointmentCommand("111111ó111", new Interval(
                LocalDateTime.now().plusMinutes(1), LocalDateTime.now().plusMinutes(2)), "001"), Problem.class);

        assertEquals(Status.BAD_REQUEST, expected.getStatus());
    }

    @Test
    void testReserveNewAppointmentWithInvalidInterval1() {
        Problem expected = template.postForObject("/api/appointments", new ReserveNewAppointmentCommand("1111111111", new Interval(
                LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(2)), "001"), Problem.class);

        assertEquals(Status.BAD_REQUEST, expected.getStatus());
    }

    @Test
    void testReserveNewAppointmentWithInvalidInterval2() {
        Problem expected = template.postForObject("/api/appointments", new ReserveNewAppointmentCommand("1111111111", new Interval(
                LocalDateTime.now().plusMinutes(1), LocalDateTime.now().minusMinutes(2)), "001"), Problem.class);

        assertEquals(Status.BAD_REQUEST, expected.getStatus());
    }

    @Test
    void testReserveNewAppointmentWithInvalidInterval3() {
        Problem expected = template.postForObject("/api/appointments", new ReserveNewAppointmentCommand("1111111111", new Interval(
                LocalDateTime.now().plusMinutes(2), LocalDateTime.now().plusMinutes(1)), "001"), Problem.class);

        assertEquals(Status.BAD_REQUEST, expected.getStatus());
    }

    @Test
    void testReserveNewAppointmentWithInvalidCode() {
        Problem expected = template.postForObject("/api/appointments", new ReserveNewAppointmentCommand("1111111111", new Interval(
                LocalDateTime.now().plusMinutes(1), LocalDateTime.now().plusMinutes(2)), "003"), Problem.class);

        assertEquals(Status.BAD_REQUEST, expected.getStatus());
    }
}
