package com.restsecure.components.data;

import com.restsecure.http.Header;
import com.restsecure.http.MultiValueList;
import com.restsecure.http.Parameter;
import com.restsecure.request.specification.RequestSpecification;
import org.testng.annotations.Test;

import static com.restsecure.Matchers.containsName;
import static com.restsecure.Matchers.containsPair;
import static com.restsecure.RestSecure.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class RequestDataHandlerTest {

    @Test
    public void addParamsFromDataClassTest() {
        RequestSpecification request = get("url").data(new RegisterRequestData());

        RequestDataHandler handler = new RequestDataHandler();
        handler.handleRequest(request);

        MultiValueList<Parameter> parameters = new MultiValueList<>(request.getParameters());

        assertThat(parameters, containsPair("user_login", "userlogin"));
        assertThat(parameters, not(containsName("login")));

        assertThat(parameters, containsPair("user_pass", "userpass"));
        assertThat(parameters, not(containsPair("password", "userpass")));

        assertThat(parameters, containsPair("notify", "false"));
        assertThat(parameters, not(containsName("disableParam")));
    }

    @Test
    public void addHeadersFromDataClassTest() {
        RequestSpecification request = get("url").data(new RegisterRequestData());

        RequestDataHandler handler = new RequestDataHandler();
        handler.handleRequest(request);

        MultiValueList<Header> parameters = new MultiValueList<>(request.getHeaders());

        assertThat(parameters, containsPair("accept", "text/plain"));

        assertThat(parameters, containsPair("Cache-Control", "no-cache"));
        assertThat(parameters, not(containsName("cacheControl")));

        assertThat(parameters, not(containsName("Content-Length")));
        assertThat(parameters, not(containsName("contentLength")));
    }

    private static class RegisterRequestData {

        @RequestParam(name = "user_login")
        private String login;

        @RequestParam(name = "user_pass")
        private String password;

        @RequestParam
        private boolean notify;

        @RequestParam(enable = false)
        private String disableParam;

        @RequestHeader
        private String accept = "text/plain";

        @RequestHeader(name = "Cache-Control")
        private String cacheControl = "no-cache";

        @RequestHeader(name = "Content-Length", enable = false)
        private String contentLength = "1000";

        public RegisterRequestData() {
            this.login = "userlogin";
            this.password = "userpass";
            this.notify = false;
            this.disableParam = "disableparam";
        }
    }
}
