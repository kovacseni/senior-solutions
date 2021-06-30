package locations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@XmlRootElement
public class LocationDto {

    private long id;
    private String name;
    private double lat;
    private double lon;

    public LocationDto(String name) {
        this.name = name;
    }
}
