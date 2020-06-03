package com.restsecure;

import com.restsecure.http.MultiValueList;
import com.restsecure.http.NameAndValue;
import com.restsecure.matchers.IsAnyValue;
import com.restsecure.matchers.IsMultiValueListContaining;
import com.restsecure.matchers.JsonMatcher;
import org.hamcrest.Matcher;

import java.util.function.Function;

import static org.hamcrest.Matchers.equalTo;

public class Matchers {

    /**
     * Creates a matcher who always returns the true
     *
     * @return IsAnyValue
     */
    public static <T> Matcher<T> anyValue() {
        return new IsAnyValue<>();
    }

    /**
     * Creates a matcher for {@link MultiValueList} matching when the examined {@link MultiValueList} contains
     * at least one name that satisfies the specified matcher.<br>
     * For example:
     * <pre>assertThat(multiValueList, containsName(containsString("myName")))</pre>
     *
     * @param nameMatcher the matcher that must be satisfied by at least one name
     * @return IsMultiValueListContaining
     */
    public static <T extends NameAndValue> Matcher<MultiValueList<T>> containsName(Matcher<String> nameMatcher) {
        return new IsMultiValueListContaining<>(nameMatcher, Function.identity(), anyValue());
    }

    /**
     * Creates a matcher for {@link MultiValueList} matching when the examined {@link MultiValueList} contains
     * at least one name that is equal to the specified name.<br>
     * For example:
     * <pre>assertThat(multiValueList, containsName("myName"))</pre>
     *
     * @param name the name that at least one element must have
     * @return IsMultiValueListContaining
     */
    public static <T extends NameAndValue> Matcher<MultiValueList<T>> containsName(String name) {
        return new IsMultiValueListContaining<>(equalTo(name), Function.identity(), anyValue());
    }

    /**
     * Creates a matcher for {@link MultiValueList} matching when the examined {@link MultiValueList} contains
     * at least one value that satisfies the specified matcher.<br>
     * For example:
     * <pre>assertThat(multiValueList, containsValue(equalTo("myValue")))</pre>
     *
     * @param valueMatcher the matcher that must be satisfied by at least one value
     * @return IsMultiValueListContaining
     */
    public static <T extends NameAndValue> Matcher<MultiValueList<T>> containsValue(Matcher<String> valueMatcher) {
        return new IsMultiValueListContaining<>(anyValue(), Function.identity(), valueMatcher);
    }

    /**
     * Creates a matcher for {@link MultiValueList} matching when the examined {@link MultiValueList} contains
     * at least one value that is equal to the specified value.<br>
     * For example:
     * <pre>assertThat(multiValueList, containsValue("myValue"))</pre>
     *
     * @param value the value that at least one element must have
     * @return IsMultiValueListContaining
     */
    public static <T extends NameAndValue> Matcher<MultiValueList<T>> containsValue(String value) {
        return new IsMultiValueListContaining<>(anyValue(), Function.identity(), equalTo(value));
    }

    /**
     * Creates a matcher for {@link MultiValueList} matching when the examined {@link MultiValueList} contains
     * at least one name and value pair that is equal to the specified pair.<br>
     * For example:
     * <pre>assertThat(multiValueList, containsPair(nameAndValue))</pre>
     *
     * @param pair the name and value pair that at least one element must have
     * @return IsMultiValueListContaining
     */
    public static <T extends NameAndValue> Matcher<MultiValueList<T>> containsPair(NameAndValue pair) {
        return new IsMultiValueListContaining<>(equalTo(pair.getName()), Function.identity(), equalTo(pair.getValue()));
    }

    /**
     * Creates a matcher for {@link MultiValueList} matching when the examined {@link MultiValueList} contains
     * at least one name and value pair that is equal to the specified pair.<br>
     * For example:
     * <pre>assertThat(multiValueList, containsPair("myName", "myValue"))</pre>
     *
     * @param name  the name that at least one element must have
     * @param value the value that at least one element must have
     * @return IsMultiValueListContaining
     */
    public static <T extends NameAndValue> Matcher<MultiValueList<T>> containsPair(String name, String value) {
        return new IsMultiValueListContaining<>(equalTo(name), Function.identity(), equalTo(value));
    }

    /**
     * Creates a matcher for {@link MultiValueList} matching when the examined {@link MultiValueList} contains
     * at least one name and value pair that is equal to the specified pair.<br>
     * For example:
     * <pre>assertThat(multiValueList, containsPair("myName", Integer::parseInt, lessThan(200)))</pre>
     *
     * @param name            the name that at least one element must have
     * @param parsingFunction the function that converts the value to the needed type
     * @param valueMatcher    the matcher that must be satisfied by at least one value
     * @return IsMultiValueListContaining
     */
    public static <T extends NameAndValue, E> Matcher<MultiValueList<T>> containsPair(String name,
                                                                                      Function<String, E> parsingFunction,
                                                                                      Matcher<? super E> valueMatcher) {
        return new IsMultiValueListContaining<>(equalTo(name), parsingFunction, valueMatcher);
    }

    /**
     * Creates a matcher for {@link MultiValueList} matching when the examined {@link MultiValueList} contains
     * at least one name and value pair that is equal to the specified pair.<br>
     * For example:
     * <pre>assertThat(multiValueList, containsPair("myName", containsString("myValue")))</pre>
     *
     * @param name         the name that at least one element must have
     * @param valueMatcher the matcher that must be satisfied by at least one value
     * @return IsMultiValueListContaining
     */
    public static <T extends NameAndValue> Matcher<MultiValueList<T>> containsPair(String name, Matcher<String> valueMatcher) {
        return new IsMultiValueListContaining<>(equalTo(name), Function.identity(), valueMatcher);
    }

    /**
     * Creates a matcher for {@link MultiValueList} matching when the examined {@link MultiValueList} contains
     * at least one name and value pair that is equal to the specified pair.<br>
     * For example:
     * <pre>assertThat(multiValueList, containsPair(containsString("myName"), "myValue"))</pre>
     *
     * @param nameMatcher the matcher that must be satisfied by at least one name
     * @param value       the value that at least one element must have
     * @return IsMultiValueListContaining
     */
    public static <T extends NameAndValue> Matcher<MultiValueList<T>> containsPair(Matcher<String> nameMatcher, String value) {
        return new IsMultiValueListContaining<>(nameMatcher, Function.identity(), equalTo(value));
    }

    /**
     * Creates a matcher for {@link MultiValueList} matching when the examined {@link MultiValueList} contains
     * at least one name and value pair that is equal to the specified pair.<br>
     * For example:
     * <pre>assertThat(multiValueList, containsPair("myName", Integer::parseInt, lessThan(200)))</pre>
     *
     * @param nameMatcher     the matcher that must be satisfied by at least one name
     * @param parsingFunction the function that converts the value to the needed type
     * @param valueMatcher    the matcher that must be satisfied by at least one value
     * @return IsMultiValueListContaining
     */
    public static <T extends NameAndValue, E> Matcher<MultiValueList<T>> containsPair(Matcher<String> nameMatcher,
                                                                                      Function<String, E> parsingFunction,
                                                                                      Matcher<? super E> valueMatcher) {
        return new IsMultiValueListContaining<>(nameMatcher, parsingFunction, valueMatcher);
    }

    /**
     * Creates a matcher for {@link MultiValueList} matching when the examined {@link MultiValueList} contains
     * at least one name and value pair that is equal to the specified pair.<br>
     * For example:
     * <pre>assertThat(multiValueList, containsPair("myName", Integer::parseInt, lessThan(200)))</pre>
     *
     * @param nameMatcher  the matcher that must be satisfied by at least one name
     * @param valueMatcher the matcher that must be satisfied by at least one value
     * @return IsMultiValueListContaining
     */
    public static <T extends NameAndValue> Matcher<MultiValueList<T>> containsPair(Matcher<String> nameMatcher, Matcher<String> valueMatcher) {
        return new IsMultiValueListContaining<>(nameMatcher, Function.identity(), valueMatcher);
    }

    /**
     * Creates a matcher for json path value matching when the examined value satisfied at specified matcher<br>
     * For example:
     * <pre>assertThat(jsonString, valueByPathIs("user.age", lessThan(20)))</pre>
     *
     * @param path         json path
     * @param valueMatcher the matcher that must be satisfied by value
     * @return IsMultiValueListContaining
     */
    public static JsonMatcher valueByPathIs(String path, Matcher<?> valueMatcher) {
        return new JsonMatcher(path, valueMatcher);
    }
}
