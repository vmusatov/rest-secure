package com.restsecure.validation.base;

import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;
import org.hamcrest.Matcher;

import java.util.concurrent.TimeUnit;

import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class ResponseTimeValidation implements Validation {

    private final Matcher<Long> timeMatcher;
    private final TimeUnit timeUnit;

    public ResponseTimeValidation(Matcher<Long> timeMatcher, TimeUnit timeUnit) {
        this.timeMatcher = timeMatcher;
        this.timeUnit = timeUnit;
    }

    @Override
    public ValidationResult validate(Response response) {
        long time = response.getTimeIn(timeUnit);

        if (!timeMatcher.matches(time)) {
            return new ValidationResult(ValidationStatus.FAIL, "Expected response time is " + timeMatcher + ", but found " + time);
        }

        return new ValidationResult(SUCCESS);
    }
}
