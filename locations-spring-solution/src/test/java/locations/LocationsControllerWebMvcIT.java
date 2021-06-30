package locations;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Test
    void testFindLocationById() throws Exception {
        when(service.findLocationById(anyLong())).thenReturn(new LocationDto(2L, "Athén", 37.97954, 23.72638));

        mockMvc.perform(get("/api/locations/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Athén")));
    }

    @Test
    void testGetLocationsByNameLatLon() throws Exception {
        when(service.getLocationsByNameLatLon(any(), any(), any(), any(), any())).thenReturn(List.of(new LocationDto(1L, "Róma", 41.90383, 12.50557),
                new LocationDto(2L, "Athén", 37.97954, 23.72638)));

        mockMvc.perform(get("/api/locations/minmax"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", equalTo("Róma")))
                .andExpect(jsonPath("$[1].name", equalTo("Athén")));
    }

    /*@Test
    void testCreateLocation() throws Exception {
        when(service.createLocation(any())).thenReturn(new LocationDto(2L, "Athén", 37.97954, 23.72638));

        mockMvc.perform(post("/api/locations"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Athén")));
    }*/

   /* @Test
    void testUpdateLocation() throws Exception {
        when(service.updateLocation(anyLong(), any())).thenReturn(new LocationDto(2L, "Athén", 37.97954, 23.72638));

        mockMvc.perform(put("/api/locations/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Athén")));
    }*/
}
