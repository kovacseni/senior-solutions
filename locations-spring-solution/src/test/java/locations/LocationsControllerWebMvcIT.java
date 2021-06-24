package locations;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LocationsController.class)
public class LocationsControllerWebMvcIT {

    @MockBean
    LocationsService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetLocations() throws Exception {
        when(service.getLocations(any())).thenReturn(List.of(new LocationDto(1L, "Róma", 41.90383, 12.50557),
                                                             new LocationDto(2L, "Athén", 37.97954, 23.72638)));

        mockMvc.perform(get("/api/locations"))
    //            .andDo(print());
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("$[0].name", equalTo("Róma")));
    }
}
