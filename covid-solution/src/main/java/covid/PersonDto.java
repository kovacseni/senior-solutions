package covid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

    private long id;
    private String name;
    private String taj;
    private String dateOfBirth;
    private String placeOfBirth;
    private String address;

    public PersonDto(String name, String taj, String dateOfBirth, String placeOfBirth, String address) {
        this.name = name;
        this.taj = taj;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.address = address;
    }
}
