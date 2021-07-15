package nav;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class NavValidationFailException extends AbstractThrowableProblem {

    public NavValidationFailException() {
        super(URI.create("appointments/not-valid"),
                "Not valid argument",
                Status.BAD_REQUEST,
                "Not valid argument by appointment reserving");
    }
}
