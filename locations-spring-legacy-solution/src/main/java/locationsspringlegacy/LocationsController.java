package locationsspringlegacy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class LocationsController {

    private List<Location> locations = Arrays.asList(new Location("Budapest"), new Location("Párizs"), new Location("New York"));

    @GetMapping("/")
    @ResponseBody
    public String getLocations() {
        return locations.toString();
    }
}
