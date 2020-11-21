package com.restsecure.validation.matchers;

import com.jayway.jsonpath.JsonPath;
import lombok.Setter;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class JsonMatcher extends TypeSafeMatcher<String> {
    @Setter
    private String basePath;
    private final String path;
    private final Matcher<?> valueMatcher;

    public JsonMatcher(String path, Matcher<?> valueMatcher) {
        this.basePath = "";
        this.path = path;
        this.valueMatcher = valueMatcher;
    }

    @Override
    protected boolean matchesSafely(String json) {
        String jsonPath = basePath + path;
        Object read = JsonPath.read(json, jsonPath);
        return valueMatcher.matches(read);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("json path [" + this.path + "] ")
                .appendText("should have value [")
                .appendDescriptionOf(valueMatcher)
                .appendText("]");
    }
}
