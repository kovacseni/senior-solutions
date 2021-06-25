package bicycle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bike {

    private String bikeId;
    private String userId;
    private LocalDateTime latestDepo;
    private double km;
}
