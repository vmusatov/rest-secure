package com.restsecure.validation.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * A matcher who always returns the true
 */
public class IsAnyValue<T> extends BaseMatcher<T> {

    private final String message;

    public IsAnyValue() {
        this("anything");
    }

    public IsAnyValue(String message) {
        this.message = message;
    }

    @Override
    public boolean matches(Object o) {
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(message);
    }
}
