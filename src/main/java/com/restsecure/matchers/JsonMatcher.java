package com.restsecure.matchers;

import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class JsonMatcher extends TypeSafeMatcher<String> {
    private final String path;
    private final Matcher<?> valueMatcher;

    public JsonMatcher(String path, Matcher<?> valueMatcher) {
        this.path = path;
        this.valueMatcher = valueMatcher;
    }

    @Override
    protected boolean matchesSafely(String json) {
        return valueMatcher.matches(JsonPath.read(json, path));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("json path [" + this.path + "] ")
                .appendText("should have value [")
                .appendDescriptionOf(valueMatcher)
                .appendText("]");
    }
}
