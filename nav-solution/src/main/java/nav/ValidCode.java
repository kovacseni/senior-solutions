package nav;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CodeValidator.class)
public @interface ValidCode {

    String message() default "Invalid case type!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
