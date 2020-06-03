package com.restsecure.request.handler;

import com.restsecure.RestSecureConfiguration;
import com.restsecure.request.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.restsecure.RestSecure.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RequestConfigurationHandlerTest {

    private final String url = "http://example.com";
    private final RequestConfigurationHandler handler = new RequestConfigurationHandler();

    @AfterMethod
    public void resetConfiguration() {
        RestSecureConfiguration.reset();
    }

    @Test
    public void urlWithoutConfigurationTest() {
        RequestSpecification spec = get(url);

        handler.handleRequest(spec);

        assertThat(spec.getUrl(), equalTo(url));
    }

    @Test
    public void notEmptyUrlWithBaseUrlTest() {

        String pathUrl = "/path";

        RestSecureConfiguration.setBaseUrl(url);
        RequestSpecification spec = get(pathUrl);

        handler.handleRequest(spec);

        assertThat(spec.getUrl(), equalTo(url + pathUrl));
    }

    @Test
    public void emptyUrlWithBaseUrlTest() {
        String pathUrl = "";

        RestSecureConfiguration.setBaseUrl(url);
        RequestSpecification spec = get(pathUrl);

        handler.handleRequest(spec);

        assertThat(spec.getUrl(), equalTo(url));
    }

    @Test
    public void emptyUrlWithEmptyBaseUrlTest() {
        String pathUrl = "";

        RestSecureConfiguration.setBaseUrl("");
        RequestSpecification spec = get(pathUrl);

        handler.handleRequest(spec);

        assertThat(spec.getUrl(), equalTo(RestSecureConfiguration.DEFAULT_URL));
    }
}
