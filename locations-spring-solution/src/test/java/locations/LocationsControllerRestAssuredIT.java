package locations;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.RestAssured.with;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
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

   /* @Test
    void testListLocations() {
        RestAssuredMockMvc.with()
                .body(new CreateLocationCommand("Róma", 41.90383, 12.50557))
                .post("/api/locations")
                .then()
                .statusCode(201)
                .body("name", equalTo("Róma"));
        service.deleteAllLocations();
        with()
                .get("/api/locations")
                .then()
                .statusCode(200)
                .body("[0].name", equalTo("Róma"))
                .body("size()", equalTo(1));
    }*/
}
