package cars;

import lombok.Data;

import java.util.List;

@Data
public class Car {

    private String brand;
    private String type;
    private int age;
    private Condition condition;
    private List<KmState> kms;

    public Car(String brand, String type, int age, Condition condition) {
        this.brand = brand;
        this.type = type;
        this.age = age;
        this.condition = condition;
    }
}
