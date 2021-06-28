package covid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonCommand {

    private String name;
    private String taj;
    private String dateOfBirth;
    private String placeOfBirth;
    private String address;
}
