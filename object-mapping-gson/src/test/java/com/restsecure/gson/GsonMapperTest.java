package com.restsecure.gson;

import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class GsonMapperTest {
    private final String json = "{" +
            "\"name\":\"testname\"," +
            "\"surname\":\"testsurname\"," +
            "\"contacts\":" +
            "[" +
            "{" +
            "\"contact\":\"9999999999\"," +
            "\"type\":\"phone\"" +
            "}," +
            "{" +
            "\"contact\":\"test@example.com\"," +
            "\"type\":\"email\"" +
            "}" +
            "]" +
            "}";

    private final User user = new User(
            "testname",
            "testsurname",
            Arrays.asList(
                    new Contact("9999999999", "phone"),
                    new Contact("test@example.com", "email")
            )
    );

    private GsonMapper mapper = new GsonMapper();

    @Test
    public void deserializeTest() {
        User user = mapper.deserialize(json, User.class);

        assertThat(user, notNullValue());
        assertThat(user.getName(), equalTo("testname"));
        assertThat(user.getSurname(), equalTo("testsurname"));
        assertThat(user.getContacts(), notNullValue());
        assertThat(user.getContacts().size(), is(2));
        assertThat(user.getContacts(), hasItem(new Contact("9999999999", "phone")));
        assertThat(user.getContacts(), hasItem(new Contact("test@example.com", "email")));
    }

    @Test
    public void deserializeTypeTest() {
        Type type = new TypeToken<User>() {
        }.getType();
        User user = mapper.deserialize(json, type);

        assertThat(user, notNullValue());
        assertThat(user.getName(), equalTo("testname"));
        assertThat(user.getSurname(), equalTo("testsurname"));
        assertThat(user.getContacts(), notNullValue());
        assertThat(user.getContacts().size(), is(2));
        assertThat(user.getContacts(), hasItem(new Contact("9999999999", "phone")));
        assertThat(user.getContacts(), hasItem(new Contact("test@example.com", "email")));
    }

    @Test
    public void serializeTest() {
        String json = mapper.serialize(user);
        assertThat(json, equalTo(this.json));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String name;
        private String surname;
        private List<Contact> contacts;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact {
        private String contact;
        private String type;
    }
}
