package nav;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodeValidator implements ConstraintValidator<ValidCode, String> {

    @Autowired
    private NavService service;

    public CodeValidator(NavService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        return service.isValidCaseType(code);
    }
}
