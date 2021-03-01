package com.restsecure;

import com.restsecure.core.http.Cookie;
import com.restsecure.core.http.Header;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.response.validation.ValidationResult;
import com.restsecure.core.response.validation.ValidationStatus;
import lombok.Data;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.List;

import static com.restsecure.Matchers.anyValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class BaseTest {

    protected final String userJson = "{\n" +
            "  \"id\": 1,\n" +
            "  \"login\": \"UserLogin\",\n" +
            "  \"is_subscribed\": true,\n" +
            "  \"phone\": {\n" +
            "    \"code\": \"+7\",\n" +
            "    \"number\": \"9999999999\"\n" +
            "  }\n" +
            "}";

    protected final List<Header> headersWithStringValues = Arrays.asList(
            new Header("name1", "value1"),
            new Header("name2", "value2"),
            new Header("name3", "value3"),
            new Header("name4", "value4"),
            new Header("name5", "value5")
    );

    protected final List<Cookie> cookiesWithStringValues = Arrays.asList(
            new Cookie("name1", "value1"),
            new Cookie("name2", "value2"),
            new Cookie("name3", "value3"),
            new Cookie("name2", "value2"),
            new Cookie("name3", "value3")
    );

    protected final List<Header> headersWithNumberValues = Arrays.asList(
            new Header("name1", "1"),
            new Header("name2", "2"),
            new Header("name3", "3"),
            new Header("name4", "4"),
            new Header("name5", "5")
    );

    protected final List<Cookie> cookiesWithNumberValues = Arrays.asList(
            new Cookie("name1", "1"),
            new Cookie("name2", "2"),
            new Cookie("name3", "3"),
            new Cookie("name4", "4"),
            new Cookie("name5", "5")
    );

    protected void expectValidationSuccess(Validation validation, Response response) {
        ValidationResult result = validation.softValidate(response);
        assertThat(result.getStatus(), equalTo(ValidationStatus.SUCCESS));
    }

    protected void expectValidationFailWithErrorText(Validation validation, Response response, String expectedText) {
        ValidationResult result = validation.softValidate(response);

        assertThat(result.getStatus(), equalTo(ValidationStatus.FAIL));
        assertThat(result.getErrorText(), containsString(expectedText));
    }

    @Data
    public static class User {
        private Integer id;
        private String login;
        private Boolean is_subscribed;
        private Phone phone;
    }

    @Data
    public static class Phone {
        private String code;
        private String number;
    }

    public static class UserMatcher extends TypeSafeMatcher<User> {
        private final Matcher<Integer> idMatcher;
        private final Matcher<String> loginMatcher;
        private final Matcher<Boolean> subscribedMatcher;

        public UserMatcher(Matcher<Integer> idMatcher, Matcher<String> loginMatcher, Matcher<Boolean> subscribedMatcher) {
            this.idMatcher = idMatcher;
            this.loginMatcher = loginMatcher;
            this.subscribedMatcher = subscribedMatcher;
        }

        @Override
        protected boolean matchesSafely(User user) {
            return idMatcher.matches(user.getId())
                    && loginMatcher.matches(user.getLogin())
                    && subscribedMatcher.matches(user.getIs_subscribed());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("User should be <")
                    .appendText("id: ")
                    .appendDescriptionOf(idMatcher)
                    .appendText(", ")
                    .appendText("login: ")
                    .appendDescriptionOf(loginMatcher)
                    .appendText(", ")
                    .appendText("is_subscribe: ")
                    .appendDescriptionOf(subscribedMatcher)
                    .appendText(">");
        }

        public static Matcher<User> loginIs(String login) {
            return new UserMatcher(anyValue(), equalTo(login), anyValue());
        }

        public static Matcher<User> loginIs(Matcher<String> loginMatcher) {
            return new UserMatcher(anyValue(), loginMatcher, anyValue());
        }

        public static Matcher<User> idIs(int id) {
            return new UserMatcher(equalTo(id), anyValue(), anyValue());
        }

        public static Matcher<User> idIs(Matcher<Integer> idMatcher) {
            return new UserMatcher(idMatcher, anyValue(), anyValue());
        }

        public static Matcher<User> isSubscribe(boolean isSubscribe) {
            return new UserMatcher(anyValue(), anyValue(), equalTo(isSubscribe));
        }
    }


}
