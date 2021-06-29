package locations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CoordinateValidator implements ConstraintValidator<Coordinate, Double> {

    private Type type;

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

    @Override
    public void initialize(Coordinate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
