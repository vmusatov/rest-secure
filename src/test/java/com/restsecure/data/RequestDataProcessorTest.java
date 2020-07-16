package com.restsecure.data;

import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.util.MultiKeyMap;
import org.testng.annotations.Test;

import static com.restsecure.RestSecure.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RequestDataProcessorTest {

    @Test
    public void addParamsFromDataClassTest() {
        RequestSpecification spec = get("url").data(new RegisterRequestData());

        RequestDataProcessor processor = new RequestDataProcessor();
        processor.preSendProcess(new RequestContext(spec));

        MultiKeyMap<String, Object> parameters = spec.getParameters();

        assertThat(parameters.getFirst("user_login"), equalTo("userlogin"));
        assertThat(parameters.getFirst("login"), is(nullValue()));

        assertThat(parameters.getFirst("user_pass"), equalTo("userpass"));
        assertThat(parameters.getFirst("password"), is(nullValue()));

        assertThat(parameters.getFirst("notify"), equalTo("false"));
        assertThat(parameters.getFirst("disableParam"), is(nullValue()));
    }

    @Test
    public void addHeadersFromDataClassTest() {
        RequestSpecification spec = get("url").data(new RegisterRequestData());

        RequestDataProcessor processor = new RequestDataProcessor();
        processor.preSendProcess(new RequestContext(spec));

        MultiKeyMap<String, Object> headers = spec.getHeaders();

        assertThat(headers.getFirst("accept"), equalTo("text/plain"));

        assertThat(headers.getFirst("Cache-Control"), equalTo("no-cache"));
        assertThat(headers.getFirst("cacheControl"), is(nullValue()));

        assertThat(headers.getFirst("Content-Length"), is(nullValue()));
        assertThat(headers.getFirst("contentLength"), is(nullValue()));
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
