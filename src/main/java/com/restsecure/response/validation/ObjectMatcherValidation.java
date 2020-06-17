package com.restsecure.response.validation;

import com.restsecure.matchers.MatcherUtils;
import org.hamcrest.Matcher;

import java.util.List;

public class ObjectMatcherValidation<T> extends ResponseObjectValidation<T> {

    private final List<Matcher<T>> matchers;

    public ObjectMatcherValidation(Class<T> responseClass, List<Matcher<T>> matchers) {
        super(responseClass);
        this.matchers = matchers;
    }

    @Override
    public void validate(T responseObject) {
        for (Matcher<T> matcher : matchers) {
            MatcherUtils.match("", responseObject, matcher);
        }
    }
}
