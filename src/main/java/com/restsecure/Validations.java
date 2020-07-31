package com.restsecure;

import com.restsecure.core.http.cookie.Cookie;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.http.header.HeaderNames;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;
import com.restsecure.validation.DefaultValidation;
import com.restsecure.validation.base.*;
import com.restsecure.validation.composite.BaseCompositeValidation;
import com.restsecure.validation.composite.CompositeValidation;
import com.restsecure.validation.composite.LogicalOperators;
import com.restsecure.validation.conditional.BaseConditionalValidation;
import com.restsecure.validation.conditional.Condition;
import com.restsecure.validation.conditional.ResponseConditionalValidation;
import com.restsecure.validation.object.ObjectMatcherValidation;
import com.restsecure.validation.object.ObjectValidation;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.restsecure.Matchers.containsPair;
import static com.restsecure.Matchers.valueByPathIs;
import static org.hamcrest.Matchers.equalTo;

public class Validations {

    public static final Validation AND = LogicalOperators.AND;
    public static final Validation OR = LogicalOperators.OR;

    /**
     * The default validation.
     * The RequestSpecification can contain only one default validation, if more than one is specified, only the last one is executed.
     * If non-default validation is added, default validations will not be executed
     *
     * <pre>
     *     RequestSpecification getUser = get("/users")
     *          .param("id", id)
     *          .validate(
     *              byDefault(
     *                  body("user.id", equalTo(id));
     *              )
     *          );
     * </pre>
     * A default validation will be added here, which checks that the server returned the user with the ID that we specified<br><br>
     * <p>
     * Suppose that we added an invalid parameter to the request and now we want to check that the server returns an error.
     * To do this, we can add new validations and in this case default validation will not be executed
     *
     * <pre>
     *     getUser.validate(
     *          statusCode(400),
     *          body("error.message", containString("One of the specified parameters is invalid."))
     *     )
     *     .send();
     * </pre>
     *
     * @param validations response validations list
     * @return DefaultValidation
     */
    public static DefaultValidation byDefault(List<Validation> validations) {
        return new DefaultValidation(validations);
    }

    /**
     * The default validation.
     * The RequestSpecification can contain only one default validation, if more than one is specified, only the last one is executed.
     * If non-default validation is added, default validations will not be executed
     *
     * <pre>
     *     RequestSpecification getUser = get("/users")
     *          .param("id", id)
     *          .validate(
     *              byDefault(
     *                  body("user.id", equalTo(id));
     *              )
     *          );
     * </pre>
     * A default validation will be added here, which checks that the server returned the user with the ID that we specified<br><br>
     * <p>
     * Suppose that we added an invalid parameter to the request and now we want to check that the server returns an error.
     * To do this, we can add new validations and in this case default validation will not be executed
     *
     * <pre>
     *     getUser.validate(
     *          statusCode(400),
     *          body("error.message", containString("One of the specified parameters is invalid."))
     *     )
     *     .send();
     * </pre>
     *
     * @param validation            response validation
     * @param additionalValidations additional response validation
     * @return DefaultValidation
     */
    public static Validation byDefault(Validation validation, Validation... additionalValidations) {
        List<Validation> validations = new ArrayList<>();
        validations.add(validation);
        validations.addAll(Arrays.asList(additionalValidations));

        return new DefaultValidation(validations);
    }

    /**
     * Combines several validations into one. Supports logical operators AND, OR
     * Convenient if you need to make a difficult condition for conditional validation.
     * <pre>
     *     RequestSpecification getUser = get("/users")
     *          .param("id", id)
     *          .validate(
     *              when(
     *                  combine(
     *                      statusCode(greaterThan(400)), statusCode(lessThan(500)),
     *                  ),
     *                  combine(
     *                      body("error.message", containString("One of the specified parameters is invalid.")),
     *                      cookies(containsName("MyCookie"))
     *                  )
     *              )
     *          );
     * </pre>
     * Here we check that the server returned an error and response contain cookie "MyCookie" only if the code is between 400 and 500
     *
     * @param validations response validations list
     * @return CompositeValidation
     */
    public static CompositeValidation combine(List<Validation> validations) {
        return new BaseCompositeValidation(validations);
    }

    /**
     * Combines several validations into one. Supports logical operators AND, OR
     * Convenient if you need to make a difficult condition for conditional validation.
     * <pre>
     *     RequestSpecification getUser = get("/users")
     *          .param("id", id)
     *          .validate(
     *              when(
     *                  combine(
     *                      statusCode(greaterThan(400)), statusCode(lessThan(500)),
     *                  ),
     *                  combine(
     *                      body("error.message", containString("One of the specified parameters is invalid.")),
     *                      cookies(containsName("MyCookie"))
     *                  )
     *              )
     *          );
     * </pre>
     * Here we check that the server returned an error and response contain cookie "MyCookie" only if the code is between 400 and 500
     *
     * @param validation            response validation
     * @param additionalValidations additional response validations
     * @return CompositeValidation
     */
    public static CompositeValidation combine(Validation validation, Validation... additionalValidations) {
        List<Validation> validations = new ArrayList<>();
        validations.add(validation);
        validations.addAll(Arrays.asList(additionalValidations));

        return new BaseCompositeValidation(validations);
    }

    /**
     * Conditional validation will be performed only if the condition is met.
     * In this case, the condition is the validation of the answer - if the validation in the condition was successful,
     * then the specified validation will be called, otherwise the validation will be considered successful.
     * <pre>
     *     RequestSpecification getUser = get("/users")
     *          .param("id", id)
     *          .validate(
     *              when(statusCode(400), then(
     *                  body("error.message", containString("One of the specified parameters is invalid."))
     *              ))
     *          );
     * </pre>
     * Here we check that the server returned an error only if the status code is 400
     *
     * @param condition  validation condition
     * @param validation response validation
     * @return ResponseConditionalValidation
     */
    public static ResponseConditionalValidation when(Validation condition, Validation validation) {
        return new ResponseConditionalValidation(condition, validation, (ctx, resp) -> new ValidationResult(ValidationStatus.SUCCESS));
    }

    /**
     * Conditional validation will be performed only if the condition is met.
     * In this case, the condition is the validation of the answer - if the validation in the condition was successful,
     * then the first specified validation will be called, otherwise the second specified validation will be called.
     * <pre>
     *     RequestSpecification getUser = get("/users")
     *          .param("id", id)
     *          .validate(
     *              when(statusCode(400), then(
     *                  body("error.message", containString("One of the specified parameters is invalid."))
     *              ), orElse(
     *                  body("result.id", equalTo(id))
     *              ))
     *          );
     * </pre>
     * Here we check that the server returned an error only if the status code is 400
     *
     * @param condition      validation condition
     * @param validation     response validation
     * @param elseValidation else response validation
     * @return ResponseConditionalValidation
     */
    public static ResponseConditionalValidation when(Validation condition, Validation validation, Validation elseValidation) {
        return new ResponseConditionalValidation(condition, validation, elseValidation);
    }

    /**
     * Conditional validation will be performed only if the condition is met.
     * In this case, the condition is the functional interface Condition with the method <pre>boolean isTrue();</pre>
     * if the method returns true, the specified validation will be called, otherwise the validation will be considered successful
     * <pre>
     *     RequestSpecification getUser = get("/users")
     *          .param("id", id)
     *          .validate(
     *              when(() -> id < 0, then(
     *                  body("error.message", containString("One of the specified parameters is invalid."))
     *              ))
     *          );
     * </pre>
     * Here we check that the server returned an error only if the id less then 0
     *
     * @param condition  validation condition
     * @param validation response validation
     * @return ConditionalValidation
     */
    public static BaseConditionalValidation when(Condition condition, Validation validation) {
        return new BaseConditionalValidation(condition, validation, (ctx, resp) -> new ValidationResult(ValidationStatus.SUCCESS));
    }

    /**
     * Conditional validation will be performed only if the condition is met.
     * In this case, the condition is the functional interface Condition with the method <pre>boolean isTrue();</pre>
     * if the method returns true, the specified validation will be called, otherwise the second specified validation will be called.
     * <pre>
     *     RequestSpecification getUser = get("/users")
     *          .param("id", id)
     *          .validate(
     *              when(() -> id < 0, then(
     *                  body("error.message", containString("One of the specified parameters is invalid."))
     *              ), orElse(
     *                  body("result.id", equalTo(id))
     *              ))
     *          );
     * </pre>
     * Here we check that the server returned an error only if the id less then 0
     *
     * @param condition      validation condition
     * @param validation     response validation
     * @param elseValidation else response validation
     * @return ConditionalValidation
     */
    public static BaseConditionalValidation when(Condition condition, Validation validation, Validation elseValidation) {
        return new BaseConditionalValidation(condition, validation, elseValidation);
    }

    /**
     * Conditional validation will be performed only if the condition is met.
     * In this case, the condition is boolean value
     * if condition is true, the specified validation will be called, otherwise the validation will be considered successful
     * <pre>
     *     RequestSpecification getUser = get("/users")
     *          .param("id", id)
     *          .validate(
     *              when(id < 0, then(
     *                  body("error.message", containString("One of the specified parameters is invalid."))
     *              ))
     *          );
     * </pre>
     * Here we check that the server returned an error only if the id less then 0
     *
     * @param condition  validation condition
     * @param validation response validation
     * @return ConditionalValidation
     */
    public static BaseConditionalValidation when(boolean condition, Validation validation) {
        return new BaseConditionalValidation(() -> condition, validation, (ctx, resp) -> new ValidationResult(ValidationStatus.SUCCESS));
    }

    /**
     * Conditional validation will be performed only if the condition is met.
     * In this case, the condition is boolean value
     * if condition is true, the specified validation will be called, otherwise the second specified validation will be called.
     * <pre>
     *     RequestSpecification getUser = get("/users")
     *          .param("id", id)
     *          .validate(
     *              when(id < 0, then(
     *                  body("error.message", containString("One of the specified parameters is invalid."))
     *              ), orElse(
     *                  body("result.id", equalTo(id))
     *              ))
     *          );
     * </pre>
     * Here we check that the server returned an error only if the id less then 0
     *
     * @param condition      validation condition
     * @param validation     response validation
     * @param elseValidation else response validation
     * @return ConditionalValidation
     */
    public static BaseConditionalValidation when(boolean condition, Validation validation, Validation elseValidation) {
        return new BaseConditionalValidation(() -> condition, validation, elseValidation);
    }

    /**
     * It is a composite validation. Syntactic sugar for conditional validation
     * Allow you to create construction when-then
     *
     * @param validations response validations list
     * @return CompositeValidation
     */
    public static CompositeValidation then(List<Validation> validations) {
        return combine(validations);
    }

    /**
     * It is a composite validation. Syntactic sugar for conditional validation
     * Allow you to create construction when-then
     *
     * @param validation            response validation
     * @param additionalValidations additional response validations
     * @return CompositeValidation
     */
    public static CompositeValidation then(Validation validation, Validation... additionalValidations) {
        return combine(validation, additionalValidations);
    }

    /**
     * It is a composite validation. Syntactic sugar for conditional validation
     * Allow you to create construction when-then-orElse
     *
     * @param validations response validations list
     * @return CompositeValidation
     */
    public static CompositeValidation orElse(List<Validation> validations) {
        return combine(validations);
    }

    /**
     * It is a composite validation. Syntactic sugar for conditional validation
     * Allow you to create construction when-then-orElse
     *
     * @param validation            response validation
     * @param additionalValidations additional response validations
     * @return CompositeValidation
     */
    public static CompositeValidation orElse(Validation validation, Validation... additionalValidations) {
        return combine(validation, additionalValidations);
    }

    /**
     * Allows you to specify the expected response status line<br>
     * For example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(
     *              statusLine("HTTP/1.1 302 Moved Temporarily")
     *          )
     *          .send();
     * </pre>
     *
     * @param expectedStatusLine expectedStatusLine
     * @return StatusLineValidation
     */
    public static StatusLineValidation statusLine(String expectedStatusLine) {
        return new StatusLineValidation(equalTo(expectedStatusLine));
    }

    /**
     * Allows you to specify the expected response status line<br>
     * For example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(
     *              statusLine(containsString("HTTP/1.1 302"))
     *          )
     *          .send();
     * </pre>
     *
     * @param statusLineMatcher statusLineMatcher
     * @return StatusLineValidation
     */
    public static StatusLineValidation statusLine(Matcher<String> statusLineMatcher) {
        return new StatusLineValidation(statusLineMatcher);
    }

    /**
     * Allows you to specify the expected response status code<br>
     * For example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(statusCode(200))
     *          .send();
     * </pre>
     *
     * @param expectedStatusCode expected response status code
     * @return ResponseValidation
     */
    public static StatusCodeValidation statusCode(int expectedStatusCode) {
        return new StatusCodeValidation(equalTo(expectedStatusCode));
    }

    /**
     * Allows you to specify a matcher to check the expected response status code
     * For example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(statusCode(lessThan(300)))
     *          .send();
     * </pre>
     *
     * @param statusCodeMatcher status code matcher
     * @return ResponseValidation
     */
    public static StatusCodeValidation statusCode(Matcher<Integer> statusCodeMatcher) {
        return new StatusCodeValidation(statusCodeMatcher);
    }

    /**
     * Allows you to specify a matcher to check the expected response time in milliseconds
     * For example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(
     *              time(lessThan(2000L))
     *          )
     *          .send();
     * </pre>
     *
     * @param timeMatcher response time matcher
     * @return ResponseTimeValidation
     */
    public static ResponseTimeValidation time(Matcher<Long> timeMatcher) {
        return new ResponseTimeValidation(timeMatcher, TimeUnit.MILLISECONDS);
    }

    /**
     * Allows you to specify a matcher to check the expected response time in specify time unit
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(
     *              time(lessThan(2L), TimeUnit.SECONDS)
     *          )
     *          .send();
     * </pre>
     *
     * @param timeMatcher response time matcher
     * @param timeUnit    time unit
     * @return ResponseTimeValidation
     */
    public static ResponseTimeValidation time(Matcher<Long> timeMatcher, TimeUnit timeUnit) {
        return new ResponseTimeValidation(timeMatcher, timeUnit);
    }

    /**
     * Allows you to specify the cookie that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(cookie("name", "value"))
     *          .send();
     * </pre>
     *
     * @param name  expected cookie name
     * @param value expected cookie value
     * @return ResponseValidation
     */
    public static CookiesValidation cookie(String name, String value) {
        return new CookiesValidation(containsPair(name, value));
    }

    /**
     * Allows you to specify the cookie that should contain the response
     * <pre>
     *     Cookie expectedCookie = new Cookie("name", "value");
     *     get("url")
     *          .param("name", "value")
     *          .validate(cookie(expectedCookie))
     *          .send();
     * </pre>
     *
     * @param cookie expected cookie
     * @return ResponseValidation
     */
    public static CookiesValidation cookie(Cookie cookie) {
        return new CookiesValidation(containsPair(cookie));
    }

    /**
     * Allows you to specify the cookie that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(cookie("name", containsString("value")))
     *          .send();
     * </pre>
     *
     * @param name         expected cookie name
     * @param valueMatcher cookie value matcher
     * @return ResponseValidation
     */
    public static CookiesValidation cookie(String name, Matcher<String> valueMatcher) {
        return new CookiesValidation(containsPair(name, valueMatcher));
    }

    /**
     * Allows you to specify the cookie that should contain the response<br>
     * The parsing function allows you to convert a string value to another type and specify a matcher of this type
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(cookie("name", Integer::parseInt, lessThan(1000)))
     *          .send();
     * </pre>
     *
     * @param name            expected cookie name
     * @param parsingFunction cookie value parsing function
     * @param valueMatcher    cookie value matcher
     * @return ResponseValidation
     */
    public static <T> CookiesValidation cookie(String name, Function<String, T> parsingFunction, Matcher<T> valueMatcher) {
        return new CookiesValidation(containsPair(name, parsingFunction, valueMatcher));
    }

    /**
     * Allows you to specify the iterable matcher to apply to the list of response cookies<br>
     * For easier use, you can use {@link com.restsecure.Matchers}, for example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(
     *              cookies(containsName("name")),
     *              cookies(containsValue("value")),
     *              cookies(containsPair("name2", "value2"))
     *          )
     *          .send();
     * </pre>
     *
     * @param matcher cookies list matcher
     * @return ResponseValidation
     */
    public static <T> CookiesValidation cookies(Matcher<? extends Iterable<? extends T>> matcher) {
        return new CookiesValidation(matcher);
    }

    /**
     * Allows you to specify the header that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(header("name", "value"))
     *          .send();
     * </pre>
     *
     * @param name          expected header name
     * @param expectedValue expected header value
     * @return ResponseValidation
     */
    public static HeadersValidation header(String name, String expectedValue) {
        return new HeadersValidation(containsPair(name, expectedValue));
    }

    /**
     * Allows you to specify the header that should contain the response
     * <pre>
     *     Header header = new Header("name", "value");
     *     get("url")
     *          .param("name", "value")
     *          .validate(header(header))
     *          .send();
     * </pre>
     *
     * @param header expected header
     * @return ResponseValidation
     */
    public static HeadersValidation header(Header header) {
        return new HeadersValidation(containsPair(header));
    }

    /**
     * Allows you to specify the header that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(header("name", containsString("value")))
     *          .send();
     * </pre>
     *
     * @param name         expected header name
     * @param valueMatcher header value matcher
     * @return ResponseValidation
     */
    public static HeadersValidation header(String name, Matcher<String> valueMatcher) {
        return new HeadersValidation(containsPair(name, valueMatcher));
    }

    /**
     * Allows you to specify the header that should contain the response<br>
     * The parsing function allows you to convert a string value to another type and specify a matcher of this type
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(header("Content-Length", Integer::parseInt, lessThan(1000)))
     *          .send();
     * </pre>
     *
     * @param name            expected header name
     * @param parsingFunction header value parsing function
     * @param valueMatcher    header value matcher
     * @return ResponseValidation
     */
    public static <T> HeadersValidation header(String name, Function<String, T> parsingFunction, Matcher<T> valueMatcher) {
        return new HeadersValidation(containsPair(name, parsingFunction, valueMatcher));
    }

    /**
     * Allows you to specify the iterable matcher to apply to the list of response headers<br>
     * For easier use, you can use {@link com.restsecure.Matchers}, for example:
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(
     *              headers(containsName("name"))
     *              headers(containsValue("value"))
     *              headers(containsPair("name2", "value2"))
     *          )
     *          .send();
     * </pre>
     *
     * @param matcher headers list matcher
     * @return ResponseValidation
     */
    public static <T> HeadersValidation headers(Matcher<? extends Iterable<? extends T>> matcher) {
        return new HeadersValidation(matcher);
    }

    /**
     * Allows you to specify the Content-Type header that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(
     *              contentType("application/json; charset=UTF-8")
     *          )
     *          .send();
     * </pre>
     *
     * @param value Content-type header value
     * @return HeadersValidation
     */
    public static HeadersValidation contentType(String value) {
        return new HeadersValidation(containsPair(HeaderNames.CONTENT_TYPE, value));
    }

    /**
     * Allows you to specify the Content-Type header that should contain the response
     * <pre>
     *     get("url")
     *          .param("name", "value")
     *          .validate(
     *              contentType(containsString("application/json"))
     *          )
     *          .send();
     * </pre>
     *
     * @param valueMatcher Content-type header value matcher
     * @return HeadersValidation
     */
    public static HeadersValidation contentType(Matcher<String> valueMatcher) {
        return new HeadersValidation(containsPair(HeaderNames.CONTENT_TYPE, valueMatcher));
    }

    /**
     * Allows you to specify matchers to checking the response as json<br>
     * For example:
     * <pre>
     *     get("/users")
     *          .param("id", "1")
     *          .validate(body("user.login", equalTo("username")))
     *          .send();
     * </pre>
     *
     * @param path    json path
     * @param matcher value matcher
     * @return ResponseValidation
     */
    public static BodyValidation body(String path, Matcher<?> matcher) {
        return new BodyValidation(valueByPathIs(path, matcher));
    }

    /**
     * Allows you to specify predicates for checking the response<br>
     * For example:
     * <pre>
     *     get("/users")
     *          .param("id", "1")
     *          .validate(
     *              as(User.class, user -> user.getName().equal("username"), "username validation")
     *          )
     *          .send();
     * </pre>
     *
     * @param responseClass response class
     * @param predicate     Predicate
     * @param reason        validation reason
     * @return ResponseValidation
     */
    public static <E> ObjectValidation<E> as(Class<E> responseClass, Predicate<E> predicate, String reason) {
        return new ObjectValidation<>(responseClass, predicate, reason);
    }

    /**
     * Allows you to specify predicates for checking the response<br>
     * For example:
     * <pre>
     *     get("/users")
     *          .param("id", "1")
     *          .validate(
     *              as(User.class, user -> user.getName().equal("username"), "username validation")
     *          )
     *          .send();
     * </pre>
     *
     * @param responseClass response calss
     * @param predicate     predicate
     * @return ResponseValidation
     */
    public static <E> ObjectValidation<E> as(Class<E> responseClass, Predicate<E> predicate) {
        return new ObjectValidation<>(responseClass, predicate, null);
    }

    /**
     * Allows you to specify predicates for checking the response<br>
     * For example:
     * <pre>
     *     get("/users")
     *          .param("id", "1")
     *          .validate(
     *              as(User.class,
     *                  idIs(1),
     *                  loginIs("login")
     *              )
     *          )
     *          .send();
     * </pre>
     *
     * @param responseClass      response class
     * @param matcher            response class matcher
     * @param additionalMatchers additional response class matchers
     * @return ResponseValidation
     */
    @SafeVarargs
    public static <E> ObjectMatcherValidation<E> as(Class<E> responseClass, Matcher<E> matcher, Matcher<E>... additionalMatchers) {
        List<Matcher<E>> matchers = new ArrayList<>();
        matchers.add(matcher);
        matchers.addAll(Arrays.asList(additionalMatchers));

        return new ObjectMatcherValidation<>(responseClass, matchers);
    }
}
