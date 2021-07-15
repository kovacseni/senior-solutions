package nav;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveNewAppointmentCommand {

    @ValidCdv
    private String cdv;

    @ValidInterval
    private Interval interval;

    @ValidCode
    private String code;
}
