package com.restsecure;

import com.restsecure.http.Cookie;
import com.restsecure.http.Header;
import com.restsecure.response.validation.*;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.restsecure.Matchers.containsPair;
import static com.restsecure.Matchers.valueByPathIs;
import static org.hamcrest.Matchers.equalTo;

public class Validations {

    /**
     * Allows you to specify the expected response status code<br>
     * For example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     .validate(statusCode(200))
     *     .send();
     * </pre>
     *
     * @param expectedStatusCode expected response status code
     * @return ResponseValidation
     */
    public static ResponseValidation statusCode(int expectedStatusCode) {
        return new StatusCodeValidation(equalTo(expectedStatusCode));
    }

    /**
     * Allows you to specify a matcher to check the expected response status code
     * For example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     .validate(statusCode(lessThan(300)))
     *     .send();
     * </pre>
     *
     * @param statusCodeMatcher status code matcher
     * @return ResponseValidation
     */
    public static ResponseValidation statusCode(Matcher<Integer> statusCodeMatcher) {
        return new StatusCodeValidation(statusCodeMatcher);
    }

    /**
     * Allows you to specify the cookie that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     .validate(cookie("name", "value"))
     *     .send();
     * </pre>
     *
     * @param name  expected cookie name
     * @param value expected cookie value
     * @return ResponseValidation
     */
    public static ResponseValidation cookie(String name, String value) {
        return new CookiesValidation(containsPair(name, value));
    }

    /**
     * Allows you to specify the cookie that should contain the response
     * <pre>
     *     Cookie expectedCookie = new Cookie("name", "value");
     *     get("url")
     *          .param("name", "value")
     *     .validate(cookie(expectedCookie))
     *     .send();
     * </pre>
     *
     * @param cookie expected cookie
     * @return ResponseValidation
     */
    public static ResponseValidation cookie(Cookie cookie) {
        return new CookiesValidation(containsPair(cookie));
    }

    /**
     * Allows you to specify the cookie that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     .validate(cookie("name", containsString("value")))
     *     .send();
     * </pre>
     *
     * @param name         expected cookie name
     * @param valueMatcher cookie value matcher
     * @return ResponseValidation
     */
    public static ResponseValidation cookie(String name, Matcher<String> valueMatcher) {
        return new CookiesValidation(containsPair(name, valueMatcher));
    }

    /**
     * Allows you to specify the cookie that should contain the response<br>
     * The parsing function allows you to convert a string value to another type and specify a matcher of this type
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     .validate(cookie("name", Integer::parseInt, lessThan(1000)))
     *     .send();
     * </pre>
     *
     * @param name            expected cookie name
     * @param parsingFunction cookie value parsing function
     * @param valueMatcher    cookie value matcher
     * @return ResponseValidation
     */
    public static <T> ResponseValidation cookie(String name, Function<String, T> parsingFunction, Matcher<T> valueMatcher) {
        return new CookiesValidation(containsPair(name, parsingFunction, valueMatcher));
    }

    /**
     * Allows you to specify the iterable matcher to apply to the list of response cookies<br>
     * For easier use, you can use {@link com.restsecure.Matchers}, for example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     .validate(
     *          cookies(containsName("name")),
     *          cookies(containsValue("value")),
     *          cookies(containsPair("name2", "value2"))
     *     )
     *     .send();
     * </pre>
     *
     * @param matcher cookies list matcher
     * @return ResponseValidation
     */
    public static <T> ResponseValidation cookies(Matcher<? extends Iterable<? extends T>> matcher) {
        return new CookiesValidation(matcher);
    }

    /**
     * Allows you to specify the header that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     .validate(header("name", "value"))
     *     .send();
     * </pre>
     *
     * @param name          expected header name
     * @param expectedValue expected header value
     * @return ResponseValidation
     */
    public static ResponseValidation header(String name, String expectedValue) {
        return new HeadersValidation(containsPair(name, expectedValue));
    }

    /**
     * Allows you to specify the header that should contain the response
     * <pre>
     *     Header header = new Header("name", "value");
     *     get("url")
     *          .param("name", "value")
     *     .validate(header(header))
     *     .send();
     * </pre>
     *
     * @param header expected header
     * @return ResponseValidation
     */
    public static ResponseValidation header(Header header) {
        return new HeadersValidation(containsPair(header));
    }

    /**
     * Allows you to specify the header that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     .validate(header("name", containsString("value")))
     *     .send();
     * </pre>
     *
     * @param name         expected header name
     * @param valueMatcher header value matcher
     * @return ResponseValidation
     */
    public static ResponseValidation header(String name, Matcher<String> valueMatcher) {
        return new HeadersValidation(containsPair(name, valueMatcher));
    }

    /**
     * Allows you to specify the header that should contain the response<br>
     * The parsing function allows you to convert a string value to another type and specify a matcher of this type
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     .validate(header("Content-Length", Integer::parseInt, lessThan(1000)))
     *     .send();
     * </pre>
     *
     * @param name            expected header name
     * @param parsingFunction header value parsing function
     * @param valueMatcher    header value matcher
     * @return ResponseValidation
     */
    public static <T> ResponseValidation header(String name, Function<String, T> parsingFunction, Matcher<T> valueMatcher) {
        return new HeadersValidation(containsPair(name, parsingFunction, valueMatcher));
    }

    /**
     * Allows you to specify the iterable matcher to apply to the list of response headers<br>
     * For easier use, you can use {@link com.restsecure.Matchers}, for example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     .validate(
     *          headers(containsName("name"))
     *          headers(containsValue("value"))
     *          headers(containsPair("name2", "value2"))
     *     )
     *     .send();
     * </pre>
     *
     * @param matcher headers list matcher
     * @return ResponseValidation
     */
    public static <T> ResponseValidation headers(Matcher<? extends Iterable<? extends T>> matcher) {
        return new HeadersValidation(matcher);
    }

    /**
     * Allows you to specify matchers to checking the response as json<br>
     * For example:
     * <pre>
     *     get("/getUser")
     *          .param("id", "1")
     *     .validate(body("user.login", equalTo("username")))
     *     .send();
     * </pre>
     *
     * @param path    json path
     * @param matcher value matcher
     * @return ResponseValidation
     */
    public static ResponseValidation body(String path, Matcher<?> matcher) {
        return new BodyValidation(valueByPathIs(path, matcher));
    }

    /**
     * Allows you to specify predicates for checking the response<br>
     * For example:
     * <pre>
     *     get("/getUser")
     *          .param("id", "1")
     *     .validate(
     *          as(User.class, user -> user.getName().equal("username"), "username validation")
     *     )
     *     .send();
     * </pre>
     *
     * @param responseClass response class
     * @param predicate     Predicate
     * @param reason        validation reason
     * @return ResponseValidation
     */
    public static <E> ResponseValidation as(Class<E> responseClass, Predicate<E> predicate, String reason) {
        return new ObjectValidation<>(responseClass, predicate, reason);
    }

    /**
     * Allows you to specify predicates for checking the response<br>
     * For example:
     * <pre>
     *     get("/getUser")
     *          .param("id", "1")
     *     .validate(
     *          as(User.class, user -> user.getName().equal("username"), "username validation")
     *     )
     *     .send();
     * </pre>
     *
     * @param responseClass response calss
     * @param predicate     predicate
     * @return ResponseValidation
     */
    public static <E> ResponseValidation as(Class<E> responseClass, Predicate<E> predicate) {
        return new ObjectValidation<>(responseClass, predicate, null);
    }

    /**
     * Allows you to specify predicates for checking the response<br>
     * For example:
     * <pre>
     *     get("/getUser")
     *          .param("id", "1")
     *     .validate(
     *          as(User.class,
     *              idIs(1),
     *              loginIs("login")
     *          )
     *     )
     *     .send();
     * </pre>
     *
     * @param responseClass      response class
     * @param matcher            response class matcher
     * @param additionalMatchers additional response class matchers
     * @return ResponseValidation
     */
    @SafeVarargs
    public static <E> ResponseValidation as(Class<E> responseClass, Matcher<E> matcher, Matcher<E>... additionalMatchers) {
        List<Matcher<E>> matchers = new ArrayList<>();
        matchers.add(matcher);
        matchers.addAll(Arrays.asList(additionalMatchers));

        return new ObjectMatcherValidation<>(responseClass, matchers);
    }
}
