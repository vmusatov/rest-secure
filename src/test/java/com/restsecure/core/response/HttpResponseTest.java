package com.restsecure.core.response;

import com.restsecure.BaseTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HttpResponseTest extends BaseTest {

    @Test
    public void setBodyTest() {
        ResponseBody body = new ResponseBody("{ }");

        HttpResponse response = new HttpResponse();
        response.setBody(body);

        assertThat(response.getBody(), equalTo(body));
    }

    @Test
    public void setStatusLineTest() {
        HttpResponse response = new HttpResponse();
        response.setStatusLine("status line");

        assertThat(response.getStatusLine(), equalTo("status line"));
    }

    @Test
    public void setStatusCodeTest() {
        HttpResponse response = new HttpResponse();
        response.setStatusCode(500);

        assertThat(response.getStatusCode(), equalTo(500));
    }

    @Test
    public void setHeadersTest() {
        HttpResponse response = new HttpResponse();
        response.setHeaders(headersWithStringValues);

        assertThat(response.getHeaders().size(), equalTo(5));
        assertThat("wrong response headers", response.getHeaders().containsAll(headersWithStringValues));
    }

    @Test
    public void setCookiesTest() {
        HttpResponse response = new HttpResponse();
        response.setCookies(cookiesWithStringValues);

        assertThat(response.getCookies().size(), equalTo(5));
        assertThat("wrong response headers", response.getCookies().containsAll(cookiesWithStringValues));
    }

    @Test
    public void setTimeTest() {
        HttpResponse response = new HttpResponse();
        response.setTime(1000);

        assertThat(response.getTime(), equalTo(1000L));
    }

    @Test
    public void getTimeInTest() {
        HttpResponse response = new HttpResponse();
        response.setTime(1200);

        assertThat(response.getTimeIn(TimeUnit.SECONDS), equalTo(1L));
    }
}
