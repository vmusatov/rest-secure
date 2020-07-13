package com.restsecure.validation.matchers;

import com.restsecure.core.util.NameValueList;
import com.restsecure.core.http.NameAndValue;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.function.Function;

public class IsNameValueListContaining<T extends NameAndValue> extends TypeSafeMatcher<NameValueList<T>> {

    private final Matcher<String> nameMatcher;
    private final Matcher<?> valueMatcher;
    private final Function<String, ?> parsingFunction;

    public <E> IsNameValueListContaining(Matcher<String> nameMatchers, Function<String, E> parsingFunction, Matcher<? super E> valueMatchers) {
        this.nameMatcher = nameMatchers;
        this.valueMatcher = valueMatchers;
        this.parsingFunction = parsingFunction;
    }

    @Override
    protected boolean matchesSafely(NameValueList<T> list) {
        for (T item : list) {
            if (nameMatcher.matches(item.getName()) && valueMatcher.matches(parsingFunction.apply(item.getValue()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("list containing item [")
                .appendDescriptionOf(nameMatcher)
                .appendText(", ")
                .appendDescriptionOf(valueMatcher)
                .appendText("]");
    }
}
