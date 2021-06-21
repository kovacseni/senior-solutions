package locations;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationDto {

    private long id;
    private String name;
    private double lat;
    private double lon;

    public LocationDto(String name) {
        this.name = name;
    }
}
