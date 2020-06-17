package com.restsecure.matchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

public class MatcherUtils {

    public static void match(String reason, Object actual, Matcher<?> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription()
                    .appendText(reason)
                    .appendText(System.lineSeparator())
                    .appendText("Expected: ")
                    .appendDescriptionOf(matcher)
                    .appendText(System.lineSeparator())
                    .appendText("     but: ");

            matcher.describeMismatch(actual, description);

            throw new AssertionError(description.toString());
        }
    }
}
