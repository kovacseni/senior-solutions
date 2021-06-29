package locations;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateLocationCommand {

    @NotBlank(message = "Name can not be blank")
    private String name;

    @Min(value = -90)
    @Max(value = 90)
    private double lat;

    @Min(value = -180)
    @Max(value = 180)
    private double lon;
}
