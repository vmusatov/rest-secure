package com.restsecure.response.validation;

import com.restsecure.http.Cookie;
import com.restsecure.http.Header;
import com.restsecure.http.MultiValueList;
import com.restsecure.matchers.JsonMatcher;
import com.restsecure.response.Response;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.restsecure.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

/**
 * Class for validating response
 */
public class ResponseOptionsValidation extends ExpandableResponseValidation<ResponseOptionsValidation> {

    private Matcher<Integer> statusCodeMatcher;
    private List<JsonMatcher> bodyMatchers;
    private List<Matcher<? extends Iterable<?>>> cookiesMatchers;
    private List<Matcher<? extends Iterable<?>>> headersMatchers;

    public ResponseOptionsValidation() {
        this.statusCodeMatcher = anyValue();
        this.cookiesMatchers = new ArrayList<>();
        this.headersMatchers = new ArrayList<>();
        this.bodyMatchers = new ArrayList<>();
    }

    /**
     * Allows you to specify the expected response status code<br>
     * For example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .statusCode(200)
     *     .send();
     * </pre>
     *
     * @param expectedStatusCode expected response status code
     * @return ResponseValidation
     */
    public ResponseOptionsValidation statusCode(int expectedStatusCode) {
        this.statusCodeMatcher = equalTo(expectedStatusCode);
        return this;
    }

    /**
     * Allows you to specify a matcher to check the expected response status code
     * For example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .statusCode(lessThan(300))
     *     .send();
     * </pre>
     *
     * @param statusCodeMatcher status code matcher
     * @return ResponseValidation
     */
    public ResponseOptionsValidation statusCode(Matcher<Integer> statusCodeMatcher) {
        this.statusCodeMatcher = statusCodeMatcher;
        return this;
    }

    /**
     * Allows you to specify the header that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .header("name", "value")
     *     .send();
     * </pre>
     *
     * @param name  expected header name
     * @param value expected header value
     * @return ResponseValidation
     */
    public ResponseOptionsValidation header(String name, String value) {
        headersMatchers.add(containsPair(name, value));
        return this;
    }

    /**
     * Allows you to specify the header that should contain the response
     * <pre>
     *     Header header = new Header("name", "value");
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .header(header)
     *     .send();
     * </pre>
     *
     * @param header expected header
     * @return ResponseValidation
     */
    public ResponseOptionsValidation header(Header header) {
        headersMatchers.add(containsPair(header));
        return this;
    }

    /**
     * Allows you to specify the header that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .header("name", containsString("value"))
     *     .send();
     * </pre>
     *
     * @param name         expected header name
     * @param valueMatcher header value matcher
     * @return ResponseValidation
     */
    public ResponseOptionsValidation header(String name, Matcher<String> valueMatcher) {
        headersMatchers.add(containsPair(name, valueMatcher));
        return this;
    }

    /**
     * Allows you to specify the header that should contain the response<br>
     * The parsing function allows you to convert a string value to another type and specify a matcher of this type
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .header("Content-Length", Integer::parseInt, lessThan(1000))
     *     .send();
     * </pre>
     *
     * @param name            expected header name
     * @param parsingFunction header value parsing function
     * @param valueMatcher    header value matcher
     * @return ResponseValidation
     */
    public <T> ResponseOptionsValidation header(String name, Function<String, T> parsingFunction, Matcher<T> valueMatcher) {
        headersMatchers.add(containsPair(name, parsingFunction, valueMatcher));
        return this;
    }

    /**
     * Allows you to specify the iterable matcher to apply to the list of response headers<br>
     * For easier use, you can use {@link com.restsecure.Matchers}, for example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .headers(containsName("name"))
     *          .headers(containsValue("value"))
     *          .headers(containsPair("name2", "value2"))
     *     .send();
     * </pre>
     *
     * @param matcher headers list matcher
     * @return ResponseValidation
     */
    public <T> ResponseOptionsValidation headers(Matcher<? extends Iterable<? extends T>> matcher) {
        headersMatchers.add(matcher);
        return this;
    }

    /**
     * Allows you to specify the cookie that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .cookie("name", "value")
     *     .send();
     * </pre>
     *
     * @param name  expected cookie name
     * @param value expected cookie value
     * @return ResponseValidation
     */
    public ResponseOptionsValidation cookie(String name, String value) {
        cookiesMatchers.add(containsPair(name, value));
        return this;
    }

    /**
     * Allows you to specify the cookie that should contain the response
     * <pre>
     *     Cookie cookie = new Cookie("name", "value");
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .cookie(cookie)
     *     .send();
     * </pre>
     *
     * @param cookie expected cookie
     * @return ResponseValidation
     */
    public ResponseOptionsValidation cookie(Cookie cookie) {
        cookiesMatchers.add(containsPair(cookie));
        return this;
    }

    /**
     * Allows you to specify the cookie that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .cookie("name", containsString("value"))
     *     .send();
     * </pre>
     *
     * @param name         expected cookie name
     * @param valueMatcher cookie value matcher
     * @return ResponseValidation
     */
    public ResponseOptionsValidation cookie(String name, Matcher<String> valueMatcher) {
        cookiesMatchers.add(containsPair(name, valueMatcher));
        return this;
    }

    /**
     * Allows you to specify the cookie that should contain the response<br>
     * The parsing function allows you to convert a string value to another type and specify a matcher of this type
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .cookie("name", Integer::parseInt, lessThan(1000))
     *     .send();
     * </pre>
     *
     * @param name            expected cookie name
     * @param parsingFunction cookie value parsing function
     * @param valueMatcher    cookie value matcher
     * @return ResponseValidation
     */
    public <T> ResponseOptionsValidation cookie(String name, Function<String, T> parsingFunction, Matcher<T> valueMatcher) {
        cookiesMatchers.add(containsPair(name, parsingFunction, valueMatcher));
        return this;
    }

    /**
     * Allows you to specify the iterable matcher to apply to the list of response cookies<br>
     * For easier use, you can use {@link com.restsecure.Matchers}, for example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *     validate()
     *          .cookies(containsName("name"))
     *          .cookies(containsValue("value"))
     *          .cookies(containsPair("name2", "value2"))
     *     .send();
     * </pre>
     *
     * @param matcher cookies list matcher
     * @return ResponseValidation
     */
    public final <T> ResponseOptionsValidation cookies(Matcher<? extends Iterable<? extends T>> matcher) {
        cookiesMatchers.add(matcher);
        return this;
    }

    /**
     * Allows you to specify matchers to checking the response as json<br>
     * For example:
     * <pre>
     *     get("/getUser")
     *          .param("id", "1")
     *     validate()
     *          .body("user.login", equalTo("username"))
     *     .send();
     * </pre>
     *
     * @param path    json path
     * @param matcher value matcher
     * @return ResponseValidation
     */
    public ResponseOptionsValidation body(String path, Matcher<?> matcher) {
        bodyMatchers.add(valueByPathIs(path, matcher));
        return this;
    }

    /**
     * Creates an instance of ResponseObjectValidation and attaches all current validations to it.<br>
     * ResponseObjectValidation allows you to validate an response as a Java class, for example:
     * <pre>
     *     get("/getUser")
     *          .param("id", "1")
     *     validate().as(User.class)
     *          .test(user -> user.getLogin().equal("username"))
     *     .send();
     * </pre>
     *
     * @param responseClass class to json map
     * @return ResponseObjectValidation
     */
    public <T> ResponseObjectValidation<T> as(Class<T> responseClass) {
        return new ResponseObjectValidation<>(responseClass).join(this);
    }

    /**
     * Validates response using specified matchers
     *
     * @param response Response
     */
    @Override
    protected void validateResponse(Response response) {
        new Validator().validate(response);
    }

    private class Validator implements ResponseValidation {

        private final String HEADER_VALIDATION_REASON = "Headers validation";
        private final String COOKIE_VALIDATION_REASON = "Cookies validation";
        private final String BODY_VALIDATION_REASON = "Body validation";

        @Override
        public void validate(Response response) {
            if (response == null) {
                throw new AssertionError("Response is null");
            }

            validateStatusCode(response.getStatusCode());
            validateHeaders(response.getHeaders());
            validateCookies(response.getCookies());

            if (response.getBody() == null) {
                validateBody("");
            } else {
                validateBody(response.getBody().getContent());
            }
        }

        private void validateStatusCode(int factStatusCode) {
            if (!statusCodeMatcher.matches(factStatusCode)) {
                throw new AssertionError("Expected status code is " + statusCodeMatcher + ", but found " + factStatusCode);
            }
        }

        private void validateHeaders(MultiValueList<Header> responseHeaders) {
            for (Matcher<?> matcher : headersMatchers) {
                match(HEADER_VALIDATION_REASON, responseHeaders, matcher);
            }
        }

        private void validateCookies(MultiValueList<Cookie> responseCookies) {
            for (Matcher<?> matcher : cookiesMatchers) {
                match(COOKIE_VALIDATION_REASON, responseCookies, matcher);
            }
        }

        private void validateBody(String json) {
            for (JsonMatcher matcher : bodyMatchers) {
                match(BODY_VALIDATION_REASON, json, matcher);
            }
        }

        private void match(String reason, Object actual, Matcher<?> matcher) {
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
}
