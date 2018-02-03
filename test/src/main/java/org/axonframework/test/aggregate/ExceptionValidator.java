package org.axonframework.test.aggregate;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * Exception Validator with the operations available on the "validate exception" (a.k.a. "expect exception") stage of
 * the test execution.
 * The underlying fixture expects a test to have been executed succesfully using a {@link
 * TestExecutor}.
 * <p>
 * There are several things to validate:<ul>
 *     <li>message</li>
 *     <li>cause</li></ul>
 *
 * @author Krzysztof Chrusciel
 * @since 3.2
 */
class ExceptionValidator {

    private final Throwable actualException;
    private final Reporter reporter = new Reporter();

    /**
     * Initialize the ExceptionValidator with the given {@code actualException}.
     *
     * @param actualException The actual exception that was thrown during command execution
     */
    ExceptionValidator(Throwable actualException) {
        this.actualException = actualException;
    }

    /**
     * Expect an exception message to occur during command handler execution that matches with the given {@code
     * matcher}.
     *
     * @param matcher The matcher to validate the actual exception message
     */
    public void withMessage(Matcher<?> matcher) {
        StringDescription description = new StringDescription(
                new StringBuilder("Given exception message matcher is null!"));
        if (matcher == null) {
            reporter.reportWrongExceptionMessage(actualException, description);
            return;
        }
        matcher.describeTo(description);
        if (actualException != null && !matcher.matches(actualException.getMessage())) {
            reporter.reportWrongExceptionMessage(actualException, description);
        }
    }

    /**
     * Expect the given {@code exceptionMessage} to occur during command handler execution. The actual exception
     * message should be exactly the same as {@code exceptionMessage}.
     *
     * @param exceptionMessage The type of exception expected from the command handler execution
     */
    public void withMessage(String exceptionMessage) {
        StringDescription description = new StringDescription(
                new StringBuilder("Given exception message is null!"));
        if (exceptionMessage == null) {
            reporter.reportWrongExceptionMessage(actualException, description);
            return;
        }
        description = new StringDescription(new StringBuilder(exceptionMessage));
        if (actualException != null && !exceptionMessage.equals(actualException.getMessage())){
            reporter.reportWrongExceptionMessage(actualException, description);
        }
    }

}
