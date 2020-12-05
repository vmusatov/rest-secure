package com.restsecure.core.http.cookie;

import org.apache.http.client.utils.DateUtils;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CookieTest {

    @Test
    public void nameValueConstructorTest() {
        Cookie cookie = new Cookie("name", "value");

        assertThat(cookie.getName(), equalTo("name"));
        assertThat(cookie.getValue(), equalTo("value"));
    }

    @Test
    public void parseFullCookieTest() {
        String expires = "Tue, 01 Dec 2020 20:30:36 GMT";
        Cookie cookie = new Cookie("name=value; Path=/get; Domain=localhost; Expires=" + expires + "; Max-Age=42; Secure; HttpOnly; SameSite=lax");

        assertThat(cookie.getName(), equalTo("name"));
        assertThat(cookie.getValue(), equalTo("value"));
        assertThat(cookie.getPath(), equalTo("/get"));
        assertThat(cookie.getDomain(), equalTo("localhost"));
        assertThat(cookie.getMaxAge(), equalTo(42));
        assertThat(cookie.isSecure(), equalTo(true));
        assertThat(cookie.isHttpOnly(), equalTo(true));
        assertThat(cookie.getSameSite(), equalTo(Cookie.SameSite.Lax));
        assertThat(cookie.getExpires(), equalTo(DateUtils.parseDate(expires)));
    }

    @Test
    public void parseNameValueTest() {
        Cookie cookie = new Cookie("name=value");

        assertThat(cookie.getName(), equalTo("name"));
        assertThat(cookie.getValue(), equalTo("value"));
    }

    @Test
    public void parseNameWithEmptyValueTest() {
        Cookie cookie = new Cookie("name=; Path=/get");

        assertThat(cookie.getName(), equalTo("name"));
        assertThat(cookie.getValue(), equalTo(""));
        assertThat(cookie.getPath(), equalTo("/get"));
    }

    @Test
    public void parseNameWithEqualValueTest() {
        Cookie cookie = new Cookie("name===; Path=/get");

        assertThat(cookie.getName(), equalTo("name"));
        assertThat(cookie.getValue(), equalTo("=="));
        assertThat(cookie.getPath(), equalTo("/get"));
    }

    @Test
    public void parsePathTest() {
        Cookie cookie = new Cookie("name=value; Path=/get");
        assertThat(cookie.getPath(), equalTo("/get"));
    }

    @Test
    public void parseDomainTest() {
        Cookie cookie = new Cookie("name=value; Domain=localhost");
        assertThat(cookie.getDomain(), equalTo("localhost"));
    }

    @Test
    public void parseExpiresTest() {
        String expires = "Tue, 01 Dec 2020 20:30:36 GMT";
        Cookie cookie = new Cookie("name=value; Expires=" + expires);
        assertThat(cookie.getExpires(), equalTo(DateUtils.parseDate(expires)));
    }

    @Test
    public void parseMaxAgeTest() {
        Cookie cookie = new Cookie("name=value; Max-Age=42");
        assertThat(cookie.getMaxAge(), equalTo(42));
    }

    @Test
    public void parseSecureTest() {
        Cookie cookie = new Cookie("name=value; Secure");
        assertThat(cookie.isSecure(), equalTo(true));
    }

    @Test
    public void parseHttpOnlyTest() {
        Cookie cookie = new Cookie("name=value; HttpOnly");
        assertThat(cookie.isHttpOnly(), equalTo(true));
    }

    @Test
    public void parseSameSiteTest() {
        Cookie cookie = new Cookie("name=value; SameSite=Strict");
        assertThat(cookie.getSameSite(), equalTo(Cookie.SameSite.Strict));
    }
}
