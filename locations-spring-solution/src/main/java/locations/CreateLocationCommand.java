package locations;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLocationCommand {

    @NotBlank(message = "Name can not be blank")
    @Schema(description = "Name of location: ", example = "Kukutyin")
    private String name;

    @Min(value = -90)
    @Max(value = 90)
    @Schema(description = "Latitude: ")
    private double lat;

  //  @Min(value = -180)
  //  @Max(value = 180)
    @Coordinate(type = Type.LON)
    @Schema(description = "Longitude: ")
    private double lon;
}
