package com.restsecure.request.specification;

import com.restsecure.authentication.RequestAuthHandler;
import com.restsecure.configuration.Config;
import com.restsecure.data.RequestParam;
import com.restsecure.deserialize.DeserializeConfig;
import com.restsecure.http.Header;
import com.restsecure.http.Parameter;
import com.restsecure.http.RequestMethod;
import com.restsecure.request.RequestHandler;
import com.restsecure.response.ResponseHandler;
import com.restsecure.session.Session;
import com.restsecure.session.SessionConfig;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.restsecure.RestSecure.basicAuth;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RequestSpecificationTest {

    @Test
    public void setUrlTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getUrl(), emptyString());

        spec.url("/path");
        assertThat(spec.getUrl(), equalTo("/path"));
    }

    @Test
    public void setPortTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getPort(), equalTo(0));

        spec.port(8080);
        assertThat(spec.getPort(), equalTo(8080));
    }

    @Test
    public void setMethodTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getMethod(), equalTo(null));

        spec.method(RequestMethod.GET);
        assertThat(spec.getMethod(), equalTo(RequestMethod.GET));
    }

    @Test
    public void addHeaderTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getHeaders().size(), equalTo(0));

        Header header = new Header("name", "value");
        spec.header(header);

        assertThat(spec.getHeaders().size(), equalTo(1));
        assertThat(spec.getHeaders().get(0), equalTo(header));
    }

    @Test
    public void addHeaderWithNameAndValueTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getHeaders().size(), equalTo(0));

        Header header = new Header("name", "value");
        spec.header(header.getName(), header.getValue());

        assertThat(spec.getHeaders().size(), equalTo(1));
        assertThat(spec.getHeaders().get(0), equalTo(header));
    }

    @Test
    public void addHeadersTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getHeaders().size(), equalTo(0));

        Header header1 = new Header("name", "value");
        Header header2 = new Header("name2", "value2");
        Header header3 = new Header("name3", "value3");
        List<Header> headers = Arrays.asList(header1, header2, header3);
        spec.headers(headers);

        assertThat(spec.getHeaders().size(), equalTo(3));
        assertThat(spec.getHeaders().get(0), equalTo(header1));
        assertThat(spec.getHeaders().get(1), equalTo(header2));
        assertThat(spec.getHeaders().get(2), equalTo(header3));
    }

    @Test
    public void addParamTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getParameters().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.param(param);

        assertThat(spec.getParameters().size(), equalTo(1));
        assertThat(spec.getParameters().get(0), equalTo(param));
    }

    @Test
    public void addParamWithNameAndValueTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getParameters().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.param(param.getName(), param.getValue());

        assertThat(spec.getParameters().size(), equalTo(1));
        assertThat(spec.getParameters().get(0), equalTo(param));
    }

    @Test
    public void addParamsTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getParameters().size(), equalTo(0));

        Parameter param1 = new Parameter("name", "value");
        Parameter param2 = new Parameter("name2", "value2");
        Parameter param3 = new Parameter("name3", "value3");
        List<Parameter> params = Arrays.asList(param1, param2, param3);
        spec.params(params);

        assertThat(spec.getParameters().size(), equalTo(3));
        assertThat(spec.getParameters().get(0), equalTo(param1));
        assertThat(spec.getParameters().get(1), equalTo(param2));
        assertThat(spec.getParameters().get(2), equalTo(param3));
    }

    @Test
    public void addRequestHandlerTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getRequestHandlers().size(), equalTo(0));

        RequestHandler handler1 = request -> {
        };
        RequestHandler handler2 = request -> {
        };

        spec.handleRequest(handler1, handler2);

        assertThat(spec.getRequestHandlers().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec.getRequestHandlers().contains(handler1));
        assertThat("Spec not contain specify handler", spec.getRequestHandlers().contains(handler2));
    }

    @Test
    public void addRequestHandlersListTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getRequestHandlers().size(), equalTo(0));

        RequestHandler handler1 = request -> {
        };
        RequestHandler handler2 = request -> {
        };

        List<RequestHandler> handlers = Arrays.asList(
                handler1,
                handler2
        );

        spec.handleRequest(handlers);

        assertThat(spec.getRequestHandlers().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec.getRequestHandlers().contains(handler1));
        assertThat("Spec not contain specify handler", spec.getRequestHandlers().contains(handler2));
    }

    @Test
    public void addResponseHandlerTest() {
        RequestSpecification specification = new RequestSpecificationImpl();
        assertThat(specification.getResponseHandlers().size(), equalTo(0));

        ResponseHandler handler1 = (request, spec) -> {
        };
        ResponseHandler handler2 = (request, spec) -> {
        };

        specification.handleResponse(handler1, handler2);

        assertThat(specification.getResponseHandlers().size(), equalTo(2));
        assertThat("Spec not contain specify handler", specification.getResponseHandlers().contains(handler1));
        assertThat("Spec not contain specify handler", specification.getResponseHandlers().contains(handler2));
    }

    @Test
    public void addResponseHandlersListTest() {
        RequestSpecification specification = new RequestSpecificationImpl();
        assertThat(specification.getResponseHandlers().size(), equalTo(0));

        ResponseHandler handler1 = (request, spec) -> {
        };
        ResponseHandler handler2 = (request, spec) -> {
        };

        List<ResponseHandler> handlers = Arrays.asList(
                handler1,
                handler2
        );

        specification.handleResponse(handlers);

        assertThat(specification.getResponseHandlers().size(), equalTo(2));
        assertThat("Spec not contain specify handler", specification.getResponseHandlers().contains(handler1));
        assertThat("Spec not contain specify handler", specification.getResponseHandlers().contains(handler2));
    }

    @Test
    public void addSessionTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getRequestHandlers().size(), equalTo(0));
        assertThat(spec.getResponseHandlers().size(), equalTo(0));

        Session session = new Session();
        spec.session(session);

        assertThat(spec.getRequestHandlers().size(), equalTo(1));
        assertThat(spec.getResponseHandlers().size(), equalTo(1));
        assertThat("Spec not contain specify handler", spec.getRequestHandlers().contains(session));
        assertThat("Spec not contain specify handler", spec.getResponseHandlers().contains(session));
    }

    @Test
    public void addAuthTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getRequestHandlers().size(), equalTo(0));

        RequestAuthHandler authentication = basicAuth("name", "pass");

        spec.auth(authentication);

        assertThat(spec.getRequestHandlers().size(), equalTo(1));
        assertThat("Spec not contain specify handler", spec.getRequestHandlers().contains(authentication));

        spec.auth(authentication);
        assertThat(spec.getRequestHandlers().size(), equalTo(1));
    }

    @Test
    public void addDataTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getData(), equalTo(null));
        RegisterRequestData registerData = new RegisterRequestData();
        LoginRequestData loginData = new LoginRequestData();

        spec.data(registerData);
        assertThat(spec.getData(), equalTo(registerData));

        spec.data(loginData);
        assertThat(spec.getData(), equalTo(loginData));
    }

    @Test
    public void addConfigTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        SessionConfig sessionConfig = new SessionConfig();
        spec.config(sessionConfig);

        assertThat(spec.getConfigs(), contains(sessionConfig));
    }

    @Test
    public void addDuplicateConfigsTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        SessionConfig sessionConfig1 = new SessionConfig().setSessionIdName("name1");
        SessionConfig sessionConfig2 = new SessionConfig().setSessionIdName("name2");

        spec.config(sessionConfig1).config(sessionConfig2);

        assertThat(spec.getConfigs(), contains(sessionConfig2));
        assertThat(spec.getConfigs(), not(contains(sessionConfig1)));
    }

    @Test
    public void addConfigsListTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        SessionConfig sessionConfig = new SessionConfig();
        DeserializeConfig deserializeConfig = new DeserializeConfig();

        List<Config> configs = Arrays.asList(sessionConfig, deserializeConfig);
        spec.config(configs);

        assertThat(spec.getConfigs().size(), equalTo(2));
    }

    @Test
    public void getConfigTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        SessionConfig sessionConfig = new SessionConfig().setSessionIdName("PHPSESSID");

        spec.config(sessionConfig);

        assertThat(spec.getConfig(SessionConfig.class).getSessionIdName(), equalTo("PHPSESSID"));
    }

    @Test
    public void mergeWithNotEmptyUrlTest() {
        RequestSpecification spec1 = new RequestSpecificationImpl().url("example1.com");
        RequestSpecification spec2 = new RequestSpecificationImpl().url("example2.com");

        spec2.mergeWith(spec1);
        assertThat(spec2.getUrl(), equalTo("example1.com"));
    }

    @Test
    public void mergeWithEmptyUrlTest() {
        RequestSpecification spec1 = new RequestSpecificationImpl().url("");
        RequestSpecification spec2 = new RequestSpecificationImpl().url("example2.com");

        spec2.mergeWith(spec1);
        assertThat(spec2.getUrl(), equalTo(""));
    }

    @Test
    public void mergePortTest() {
        RequestSpecification spec1 = new RequestSpecificationImpl().port(8080);
        RequestSpecification spec2 = new RequestSpecificationImpl().port(8081);

        spec2.mergeWith(spec1);
        assertThat(spec2.getPort(), equalTo(8080));
    }

    @Test
    public void mergeMethodTest() {
        RequestSpecification spec1 = new RequestSpecificationImpl().method(RequestMethod.GET);
        RequestSpecification spec2 = new RequestSpecificationImpl().method(RequestMethod.POST);

        spec2.mergeWith(spec1);
        assertThat(spec2.getMethod(), equalTo(RequestMethod.GET));
    }

    @Test
    public void mergeParamsTest() {
        Parameter param1 = new Parameter("name1", "value1");
        Parameter param2 = new Parameter("name2", "value2");
        RequestSpecification spec1 = new RequestSpecificationImpl().param(param1);
        RequestSpecification spec2 = new RequestSpecificationImpl().param(param2);

        spec2.mergeWith(spec1);

        assertThat(spec2.getParameters().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec2.getParameters().contains(param1));
        assertThat("Spec not contain specify handler", spec2.getParameters().contains(param2));
    }

    @Test
    public void mergeHeadersTest() {
        Header header1 = new Header("name1", "value1");
        Header header2 = new Header("name2", "value2");
        RequestSpecification spec1 = new RequestSpecificationImpl().header(header1);
        RequestSpecification spec2 = new RequestSpecificationImpl().header(header2);

        spec2.mergeWith(spec1);

        assertThat(spec2.getHeaders().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec2.getHeaders().contains(header1));
        assertThat("Spec not contain specify handler", spec2.getHeaders().contains(header2));
    }

    @Test
    public void mergeRequestHandlersTest() {
        RequestHandler handler1 = request -> {
        };
        RequestHandler handler2 = request -> {
        };

        RequestSpecification spec1 = new RequestSpecificationImpl().handleRequest(handler1).handleRequest(handler2);
        RequestSpecification spec2 = new RequestSpecificationImpl().handleRequest(handler1);

        spec2.mergeWith(spec1);

        assertThat(spec2.getRequestHandlers().size(), equalTo(3));
        assertThat("Spec not contain specify handler", spec2.getRequestHandlers().contains(handler1));
        assertThat("Spec not contain specify handler", spec2.getRequestHandlers().contains(handler2));
    }

    @Test
    public void mergeResponseHandlersTest() {
        ResponseHandler handler1 = (request, spec) -> {
        };
        ResponseHandler handler2 = (request, spec) -> {
        };

        RequestSpecification spec1 = new RequestSpecificationImpl().handleResponse(handler1).handleResponse(handler2);
        RequestSpecification spec2 = new RequestSpecificationImpl().handleResponse(handler1);

        spec2.mergeWith(spec1);

        assertThat(spec2.getResponseHandlers().size(), equalTo(3));
        assertThat("Spec not contain specify handler", spec2.getResponseHandlers().contains(handler1));
        assertThat("Spec not contain specify handler", spec2.getResponseHandlers().contains(handler2));
    }

    @Test
    public void mergeDataTest() {
        RegisterRequestData registerData = new RegisterRequestData();
        LoginRequestData loginData = new LoginRequestData();

        RequestSpecification spec1 = new RequestSpecificationImpl().data(registerData);
        RequestSpecification spec2 = new RequestSpecificationImpl().data(loginData);

        spec2.mergeWith(spec1);

        assertThat(spec2.getData(), equalTo(registerData));
    }

    private static class RegisterRequestData {
        @RequestParam(name = "user_login")
        private String login;

        @RequestParam(name = "user_pass")
        private String password;
    }

    private static class LoginRequestData {
        @RequestParam(name = "user_login")
        private String login;

        @RequestParam(name = "user_pass")
        private String password;
    }
}
