package com.restsecure.validation.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class MatcherUtils {

    public static void match(String reason, Object actual, Matcher<?> matcher) {
        if (!matcher.matches(actual)) {
            Description description = getDescription(reason, actual, matcher);
            throw new AssertionError(description.toString());
        }
    }

    public static Description getDescription(String reason, Object actual, Matcher<?> matcher) {
        Description description = new StringDescription()
                .appendText(reason)
                .appendText(System.lineSeparator())
                .appendText("Expected: ")
                .appendDescriptionOf(matcher)
                .appendText(System.lineSeparator())
                .appendText("     but: ");

        matcher.describeMismatch(actual, description);

        return description;
    }

    public static Description getDescription(Object actual, Matcher<?> matcher) {
        return getDescription("", actual, matcher);
    }
}
