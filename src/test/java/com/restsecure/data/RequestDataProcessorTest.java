package com.restsecure.data;

import com.restsecure.core.http.Header;
import com.restsecure.core.util.NameValueList;
import com.restsecure.core.http.Parameter;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecification;
import org.testng.annotations.Test;

import static com.restsecure.Matchers.containsName;
import static com.restsecure.Matchers.containsPair;
import static com.restsecure.RestSecure.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class RequestDataProcessorTest {

    @Test
    public void addParamsFromDataClassTest() {
        RequestSpecification spec = get("url").data(new RegisterRequestData());

        RequestDataProcessor processor = new RequestDataProcessor();
        processor.preSendProcess(new RequestContext(spec));

        NameValueList<Parameter> parameters = new NameValueList<>(spec.getParameters());

        assertThat(parameters, containsPair("user_login", "userlogin"));
        assertThat(parameters, not(containsName("login")));

        assertThat(parameters, containsPair("user_pass", "userpass"));
        assertThat(parameters, not(containsPair("password", "userpass")));

        assertThat(parameters, containsPair("notify", "false"));
        assertThat(parameters, not(containsName("disableParam")));
    }

    @Test
    public void addHeadersFromDataClassTest() {
        RequestSpecification spec = get("url").data(new RegisterRequestData());

        RequestDataProcessor processor = new RequestDataProcessor();
        processor.preSendProcess(new RequestContext(spec));

        NameValueList<Header> parameters = new NameValueList<>(spec.getHeaders());

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
