package com.restsecure.matchers;

import com.restsecure.http.MultiValueList;
import com.restsecure.http.NameAndValue;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.function.Function;

public class IsMultiValueListContaining<T extends NameAndValue> extends TypeSafeMatcher<MultiValueList<T>> {

    private final Matcher<String> nameMatcher;
    private final Matcher<?> valueMatcher;
    private final Function<String, ?> parsingFunction;

    public <E> IsMultiValueListContaining(Matcher<String> nameMatchers, Function<String, E> parsingFunction, Matcher<? super E> valueMatchers) {
        this.nameMatcher = nameMatchers;
        this.valueMatcher = valueMatchers;
        this.parsingFunction = parsingFunction;
    }

    @Override
    protected boolean matchesSafely(MultiValueList<T> list) {
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
