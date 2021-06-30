package locations;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.with;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationsControllerRestAssuredIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    LocationsService service;

    @BeforeEach
    void init() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.requestSpecification =
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        service.deleteAllLocations();
    }

    @Test
    void testListLocations() {
        RestAssuredMockMvc.with()
                .body(new CreateLocationCommand("Róma", 41.90383, 12.50557))
                .post("/api/locations")
                .then()
                .statusCode(201)
                .body("name", equalTo("Róma"));

        with()
                .get("/api/locations")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("Róma"))
                .body("size()", equalTo(1));
    }

    @Test
    void testFindLocationById() {
        RestAssuredMockMvc.with()
                .body(new CreateLocationCommand("Róma", 41.90383, 12.50557))
                .post("/api/locations")
                .then()
                .statusCode(201);
        with()
                .body(new CreateLocationCommand("Athén", 37.97954, 23.72638))
                .post("/api/locations")
                .then()
                .statusCode(201);

        with()
                .get("/api/locations/2")
                .then()
                .statusCode(200)
                .body("name", equalTo("Athén"));
    }

    @Test
    void testGetLocationsByNameLatLon() {
        RestAssuredMockMvc.with()
                .body(new CreateLocationCommand("Róma", 41.90383, 12.50557))
                .post("/api/locations")
                .then()
                .statusCode(201);
        with()
                .body(new CreateLocationCommand("Athén", 37.97954, 23.72638))
                .post("/api/locations")
                .then()
                .statusCode(201);
        with()
                .body(new CreateLocationCommand("Budapest", 47.497912, 19.040235))
                .post("/api/locations")
                .then()
                .statusCode(201);
        with()
                .body(new CreateLocationCommand("Sydney", -33.88223, 151.33140))
                .post("/api/locations")
                .then()
                .statusCode(201);

        with()
                .get("/api/locations/minmax?minLat=36&maxLon=20")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("Róma"))
                .body("[1].name", equalTo("Budapest"))
                .body("size()", equalTo(2));
    }

    @Test
    void testCreateLocation() {
        RestAssuredMockMvc.with()
                .body(new CreateLocationCommand("Róma", 41.90383, 12.50557))
                .post("/api/locations")
                .then()
                .statusCode(201);

        with()
                .get("/api/locations")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("Róma"))
                .body("size()", equalTo(1));
    }

   /* @Test
    void testUpdateLocation() {
        RestAssuredMockMvc.with()
                .body(new CreateLocationCommand("Róma", 41.90383, 12.50557))
                .post("/api/locations")
                .then()
                .statusCode(201);

        with()
                .put("/api/locations/1", new UpdateLocationCommand("Róma", 2.2, 3.3))
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("Róma"))
                .body("[0].lat", equalTo(2.2))
                .body("[0].lat", equalTo(3.3));
    }*/

    @Test
    void testDeleteLocation() {
        RestAssuredMockMvc.with()
                .body(new CreateLocationCommand("Róma", 41.90383, 12.50557))
                .post("/api/locations")
                .then()
                .statusCode(201);

        with()
                .delete("/api/locations/1")
                .then()
                .statusCode(204);

        with()
                .get("/api/locations")
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    void validate() {
        with()
                .body(new CreateLocationCommand("Róma", 41.90383, 12.50557))
                .post("/api/locations")
                .then()
                .body(matchesJsonSchemaInClasspath("location-dto.json"));
    }
}
