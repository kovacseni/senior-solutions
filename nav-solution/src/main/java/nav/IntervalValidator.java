package nav;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class IntervalValidator implements ConstraintValidator<ValidInterval, Interval> {

    @Override
    public boolean isValid(Interval interval, ConstraintValidatorContext context) {
        return isValidStartTime(interval) && isValidEndTime(interval);
    }

    private boolean isValidStartTime(Interval interval) {
        return interval.getStart().isAfter(LocalDateTime.now());
    }

    private boolean isValidEndTime(Interval interval) {
        return interval.getEnd().isAfter(LocalDateTime.now()) && interval.getEnd().isAfter(interval.getStart());
    }
}
