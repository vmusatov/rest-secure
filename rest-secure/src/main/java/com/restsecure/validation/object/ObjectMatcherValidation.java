package com.restsecure.validation.object;

import com.restsecure.core.request.RequestContext;
import com.restsecure.validation.matchers.MatcherUtils;
import com.restsecure.core.response.validation.ValidationResult;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.List;

import static com.restsecure.core.response.validation.ValidationStatus.FAIL;
import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class ObjectMatcherValidation<T> extends ResponseObjectValidation<T> {

    private final List<Matcher<T>> matchers;

    public ObjectMatcherValidation(Class<T> responseClass, List<Matcher<T>> matchers) {
        super(responseClass);
        this.matchers = matchers;
    }

    @Override
    public ValidationResult validate(RequestContext context, T responseObject) {

        for (Matcher<T> matcher : this.matchers) {
            if (!matcher.matches(responseObject)) {
                Description description = MatcherUtils.getDescription(responseObject, matcher);
                return new ValidationResult(FAIL, description.toString());
            }
        }

        return new ValidationResult(SUCCESS);
    }
}
