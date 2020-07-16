package com.restsecure.core.request.specification;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.http.param.Parameter;
import com.restsecure.core.mapping.deserialize.DeserializeConfig;
import com.restsecure.core.processor.PostResponseProcessor;
import com.restsecure.core.processor.PreSendProcessor;
import com.restsecure.data.RequestParam;
import com.restsecure.session.SessionConfig;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

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
        assertThat(spec.getHeaders().getFirst("name"), equalTo(header.getValue()));
    }

    @Test
    public void addHeaderWithNameAndValueTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getHeaders().size(), equalTo(0));

        Header header = new Header("name", "value");
        spec.header(header.getName(), header.getValue());

        assertThat(spec.getHeaders().size(), equalTo(1));
        assertThat(spec.getHeaders().getFirst("name"), equalTo(header.getValue()));
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
        assertThat(spec.getHeaders().getFirst("name"), equalTo(header1.getValue()));
        assertThat(spec.getHeaders().getFirst("name2"), equalTo(header2.getValue()));
        assertThat(spec.getHeaders().getFirst("name3"), equalTo(header3.getValue()));
    }

    @Test
    public void addParamTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getParameters().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.param(param);

        assertThat(spec.getParameters().size(), equalTo(1));
        assertThat(spec.getParameters().getFirst("name"), equalTo("value"));
    }

    @Test
    public void addParamWithNameAndValueTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getParameters().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.param(param.getName(), param.getValue());

        assertThat(spec.getParameters().size(), equalTo(1));
        assertThat(spec.getParameters().getFirst("name"), equalTo("value"));
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
        assertThat(spec.getParameters().getFirst("name"), equalTo("value"));
        assertThat(spec.getParameters().getFirst("name2"), equalTo("value2"));
        assertThat(spec.getParameters().getFirst("name3"), equalTo("value3"));
    }

    @Test
    public void addRequestHandlerTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getPreSendProcessors().size(), equalTo(0));

        PreSendProcessor handler1 = request -> {
        };
        PreSendProcessor handler2 = request -> {
        };

        spec.processRequest(handler1, handler2);

        assertThat(spec.getPreSendProcessors().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec.getPreSendProcessors().contains(handler1));
        assertThat("Spec not contain specify handler", spec.getPreSendProcessors().contains(handler2));
    }

    @Test
    public void addRequestHandlersListTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getPreSendProcessors().size(), equalTo(0));

        PreSendProcessor handler1 = request -> {
        };
        PreSendProcessor handler2 = request -> {
        };

        List<PreSendProcessor> handlers = Arrays.asList(
                handler1,
                handler2
        );

        spec.processRequest(handlers);

        assertThat(spec.getPreSendProcessors().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec.getPreSendProcessors().contains(handler1));
        assertThat("Spec not contain specify handler", spec.getPreSendProcessors().contains(handler2));
    }

    @Test
    public void addResponseHandlerTest() {
        RequestSpecification specification = new RequestSpecificationImpl();
        assertThat(specification.getPostResponseProcessors().size(), equalTo(0));

        PostResponseProcessor handler1 = context -> {
        };
        PostResponseProcessor handler2 = context -> {
        };

        specification.processResponse(handler1, handler2);

        assertThat(specification.getPostResponseProcessors().size(), equalTo(2));
        assertThat("Spec not contain specify handler", specification.getPostResponseProcessors().contains(handler1));
        assertThat("Spec not contain specify handler", specification.getPostResponseProcessors().contains(handler2));
    }

    @Test
    public void addResponseHandlersListTest() {
        RequestSpecification specification = new RequestSpecificationImpl();
        assertThat(specification.getPostResponseProcessors().size(), equalTo(0));

        PostResponseProcessor handler1 = context -> {
        };
        PostResponseProcessor handler2 = context -> {
        };

        List<PostResponseProcessor> handlers = Arrays.asList(
                handler1,
                handler2
        );

        specification.processResponse(handlers);

        assertThat(specification.getPostResponseProcessors().size(), equalTo(2));
        assertThat("Spec not contain specify handler", specification.getPostResponseProcessors().contains(handler1));
        assertThat("Spec not contain specify handler", specification.getPostResponseProcessors().contains(handler2));
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
    public void addConfigsListTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        SessionConfig sessionConfig = new SessionConfig();
        DeserializeConfig deserializeConfig = new DeserializeConfig();

        List<Config> configs = Arrays.asList(sessionConfig, deserializeConfig);
        spec.config(configs);

        assertThat(spec.getConfigs().size(), equalTo(2));
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
        assertThat("Spec not contain specify handler", spec2.getParameters().getFirst("name1").equals("value1"));
        assertThat("Spec not contain specify handler", spec2.getParameters().getFirst("name2").equals("value2"));
    }

    @Test
    public void mergeHeadersTest() {
        Header header1 = new Header("name1", "value1");
        Header header2 = new Header("name2", "value2");
        RequestSpecification spec1 = new RequestSpecificationImpl().header(header1);
        RequestSpecification spec2 = new RequestSpecificationImpl().header(header2);

        spec2.mergeWith(spec1);

        assertThat(spec2.getHeaders().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec2.getHeaders().getFirst("name1").equals("value1"));
        assertThat("Spec not contain specify handler", spec2.getHeaders().getFirst("name2").equals("value2"));
    }

    @Test
    public void mergeRequestHandlersTest() {
        PreSendProcessor handler1 = request -> {
        };
        PreSendProcessor handler2 = request -> {
        };

        RequestSpecification spec1 = new RequestSpecificationImpl().processRequest(handler1).processRequest(handler2);
        RequestSpecification spec2 = new RequestSpecificationImpl().processRequest(handler1);

        spec2.mergeWith(spec1);

        assertThat(spec2.getPreSendProcessors().size(), equalTo(3));
        assertThat("Spec not contain specify handler", spec2.getPreSendProcessors().contains(handler1));
        assertThat("Spec not contain specify handler", spec2.getPreSendProcessors().contains(handler2));
    }

    @Test
    public void mergeResponseHandlersTest() {
        PostResponseProcessor handler1 = context -> {
        };
        PostResponseProcessor handler2 = context -> {
        };

        RequestSpecification spec1 = new RequestSpecificationImpl().processResponse(handler1).processResponse(handler2);
        RequestSpecification spec2 = new RequestSpecificationImpl().processResponse(handler1);

        spec2.mergeWith(spec1);

        assertThat(spec2.getPostResponseProcessors().size(), equalTo(3));
        assertThat("Spec not contain specify handler", spec2.getPostResponseProcessors().contains(handler1));
        assertThat("Spec not contain specify handler", spec2.getPostResponseProcessors().contains(handler2));
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