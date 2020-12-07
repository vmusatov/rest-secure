package com.restsecure.validation.object;

import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.validation.matchers.MatcherUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.List;

import static com.restsecure.core.response.validation.ValidationStatus.FAIL;
import static com.restsecure.core.response.validation.ValidationStatus.SUCCESS;

public class ObjectMatcherValidation<T> extends ResponseObjectValidation<T> {

    private final List<Matcher<T>> matchers;

    public ObjectMatcherValidation(String path, Class<T> responseClass, List<Matcher<T>> matchers) {
        super(path, responseClass);
        this.matchers = matchers;
    }

    @Override
    public ValidationResult softValidate(T responseObject) {

        for (Matcher<T> matcher : this.matchers) {
            if (!matcher.matches(responseObject)) {
                Description description = MatcherUtils.getDescription(responseObject, matcher);
                return new ValidationResult(FAIL, description.toString());
            }
        }

        return new ValidationResult(SUCCESS);
    }
}
