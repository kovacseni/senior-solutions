package bicycle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BikeControllerTest {

    @Mock
    BikeService service;

    @InjectMocks
    BikeController controller;

    @Test
    void testGetBikes() {
        when(service.getBikes()).thenReturn(new ArrayList<>(Arrays.asList(new Bike("ABC", "DEF", LocalDateTime.of(2021, 6, 25, 13, 42), 2.1),
                new Bike("GHI", "JKL", LocalDateTime.of(2021, 5, 2, 16, 35), 3.2))));

        List<Bike> expected = service.getBikes();

        Assertions.assertThat(expected).extracting(Bike::getBikeId, Bike::getUserId)
                .contains(tuple("ABC", "DEF"), tuple("GHI", "JKL"));

        verify(service).getBikes();
    }

    @Test
    void testGetUserIds() {
        when(service.getUserIds()).thenReturn(Arrays.asList("ABC", "DEF", "GHI"));

        String expected = service.getUserIds().toString();

        Assertions.assertThat(expected).startsWith("[ABC");
        Assertions.assertThat(expected).endsWith("GHI]");

        verify(service).getUserIds();
    }
}

