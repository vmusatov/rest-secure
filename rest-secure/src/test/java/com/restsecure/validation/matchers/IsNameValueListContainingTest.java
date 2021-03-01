package com.restsecure.validation.matchers;

import com.restsecure.core.http.Cookie;
import com.restsecure.core.util.NameValueList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.restsecure.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class IsNameValueListContainingTest {

    private final NameValueList<Cookie> cookies = new NameValueList<>();

    @BeforeClass
    public void init() {
        cookies.add(new Cookie("", "value1"));
        cookies.add(new Cookie("name2", "200"));
        cookies.add(new Cookie("name3", "value3"));
        cookies.add(new Cookie("name4", "value4"));
        cookies.add(new Cookie("name5", "value5"));
    }

    @Test
    public void containsTest() {
        assertThat(cookies, containsValue("value1"));
        assertThat(cookies, containsName("name2"));

        assertThat(cookies, containsValue("value1"));
        assertThat(cookies, containsName("name2"));

        assertThat(cookies, containsName("name3"));
        assertThat(cookies, containsValue("value3"));

        assertThat(cookies, not(containsName("value")));
        assertThat(cookies, not(containsValue("name")));

        assertThat(cookies, containsPair("name3", "value3"));
        assertThat(cookies, not(containsPair("value", "value3")));
    }

    @Test
    public void containsWithMatcherTest() {
        assertThat(cookies, containsValue(equalTo("value1")));
        assertThat(cookies, containsName(equalTo("name2")));

        assertThat(cookies, containsName(containsString("name")));
        assertThat(cookies, containsValue(containsString("value")));

        assertThat(cookies, containsName(not(containsString("value"))));
        assertThat(cookies, containsValue(not(containsString("name"))));

        assertThat(cookies, containsPair("name3", containsString("value")));
        assertThat(cookies, containsPair(containsString("name"), "value3"));
        assertThat(cookies, containsPair(containsString("name"), containsString("value3")));

        assertThat(cookies, containsPair("name2", Integer::parseInt, equalTo(200)));
        assertThat(cookies, containsPair(containsString("name"), Integer::parseInt, equalTo(200)));
    }
}
