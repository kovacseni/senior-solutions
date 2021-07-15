package nav;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CdvValidator implements ConstraintValidator<ValidCdv, String> {

    @Override
    public boolean isValid(String cdv, ConstraintValidatorContext context) {
        return containsDigits(cdv) && containsTenDigits(cdv) && valid(cdv);
    }

    private boolean containsDigits(String cdv) {
        char[] cdvCharacters = cdv.toCharArray();
        for (char c : cdvCharacters) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean containsTenDigits(String cdv) {
        return cdv.length() == 10;
    }

    private boolean valid(String cdv) {
        int sum = 0;
        for (int i = 1; i <= 9; i++) {
            int x = Integer.parseInt(cdv.substring(i, i + 1));
            x *= i;
            sum += x;
        }
        int last = sum % 11;

        return last == Integer.parseInt(cdv.substring(9));
    }
}
