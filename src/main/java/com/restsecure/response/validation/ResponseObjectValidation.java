package com.restsecure.response.validation;

import com.restsecure.response.Response;
import lombok.AllArgsConstructor;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Class for mapping a response to a Java class and validating it using predicates
 *
 * @param <T> class to map
 */
public class ResponseObjectValidation<T> extends ExpandableResponseValidation<ResponseObjectValidation<T>> {

    private final List<ObjectValidation<T>> objectValidations;
    private final List<Matcher<T>> matchers;
    private final Class<T> responseClass;

    public ResponseObjectValidation(Class<T> responseClass) {
        this.objectValidations = new ArrayList<>();
        this.matchers = new ArrayList<>();
        this.responseClass = responseClass;
    }

    /**
     * Allows you to verify the equality of the response and the specified object
     * For example:
     * <pre>
     *     User user = new User("name", "pass");
     *     validation().as(User.class).isEquals(user)
     * </pre>
     *
     * @param t Object
     * @return ResponseObjectValidation
     */
    public ResponseObjectValidation<T> isEquals(T t) {
        this.objectValidations.add(
                new ObjectValidation<>(
                        "Validating value is not equal to " + t,
                        obj -> obj.equals(t)
                )
        );
        return this;
    }

    /**
     * Allows you to specify predicates for checking the response<br>
     * For example:
     * <pre>
     *     validation().as(User.class)
     *          .test(user -> user.getName().equal("username"))
     * </pre>
     *
     * @param predicate Predicate
     * @return ResponseObjectValidation
     */
    public ResponseObjectValidation<T> test(Predicate<T> predicate) {
        this.objectValidations.add(new ObjectValidation<>(null, predicate));
        return this;
    }

    /**
     * Allows you to specify predicates for checking the response
     * For example:
     * <pre>
     *     validation().as(User.class)
     *          .test("wrong username", user -> user.getName().equal("username"))
     * </pre>
     *
     * @param predicate Predicate
     * @param reason    The text that will be displayed in exception
     * @return ResponseObjectValidation
     */
    public ResponseObjectValidation<T> test(String reason, Predicate<T> predicate) {
        this.objectValidations.add(new ObjectValidation<>(reason, predicate));
        return this;
    }

    /**
     * Allows you to specify matchers to checking the response
     *
     * @param matcher            matcher
     * @param additionalMatchers additional matchers
     * @return ResponseObjectValidation
     */
    @SafeVarargs
    public final ResponseObjectValidation<T> test(Matcher<T> matcher, Matcher<T>... additionalMatchers) {
        List<Matcher<T>> matchers = new ArrayList<>();
        matchers.add(matcher);
        matchers.addAll(Arrays.asList(additionalMatchers));

        this.matchers.addAll(matchers);
        return this;
    }

    /**
     * Validates response using specified predicates
     *
     * @param response Response
     */
    @Override
    protected void validateResponse(Response response) {
        T responseModel = response.getBody().as(responseClass);

        for (ObjectValidation<T> validation : objectValidations) {
            validation.validate(responseModel);
        }

        for (Matcher<T> matcher : matchers) {
            assertThat(responseModel, matcher);
        }
    }

    @AllArgsConstructor
    public static class ObjectValidation<T> {

        private final String reason;
        private final Predicate<T> predicate;

        public void validate(T data) {
            if (!predicate.test(data)) {
                if (reason == null) {
                    throw new AssertionError("Wrong value " + data);
                } else {
                    throw new AssertionError(reason);
                }
            }
        }
    }
}
