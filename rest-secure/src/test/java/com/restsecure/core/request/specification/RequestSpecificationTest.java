package com.restsecure.core.request.specification;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.configuration.configs.ObjectMapperConfig;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.http.param.Parameter;
import com.restsecure.core.http.proxy.Proxy;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.session.SessionIdNameConfig;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void setBodyTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getBody(), is(nullValue()));

        String body = "Some body";
        spec.body(body);

        assertThat(spec.getBody(), equalTo(body));
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
    public void addQueryParamTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getQueryParams().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.queryParam(param);

        assertThat(spec.getQueryParams().size(), equalTo(1));
        assertThat(spec.getQueryParams().getFirst("name"), equalTo("value"));
    }

    @Test
    public void addQueryParamAsNameAndValueTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getQueryParams().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.queryParam(param.getName(), param.getValue());

        assertThat(spec.getQueryParams().size(), equalTo(1));
        assertThat(spec.getQueryParams().getFirst("name"), equalTo("value"));
    }

    @Test
    public void addQueryParamsAsMapTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getQueryParams().size(), equalTo(0));

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "value");
        queryParams.put("name2", "value2");

        spec.queryParams(queryParams);

        assertThat(spec.getQueryParams().size(), equalTo(2));
        assertThat(spec.getQueryParams().getFirst("name"), equalTo("value"));
        assertThat(spec.getQueryParams().getFirst("name2"), equalTo("value2"));
    }

    @Test
    public void addQueryParamsTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getQueryParams().size(), equalTo(0));

        Parameter param1 = new Parameter("name", "value");
        Parameter param2 = new Parameter("name2", "value2");
        Parameter param3 = new Parameter("name3", "value3");
        List<Parameter> params = Arrays.asList(param1, param2, param3);
        spec.queryParams(params);

        assertThat(spec.getQueryParams().size(), equalTo(3));
        assertThat(spec.getQueryParams().getFirst("name"), equalTo("value"));
        assertThat(spec.getQueryParams().getFirst("name2"), equalTo("value2"));
        assertThat(spec.getQueryParams().getFirst("name3"), equalTo("value3"));
    }

    @Test
    public void addRouteParamTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getRouteParams().size(), equalTo(0));

        spec.routeParam("name1", "value1");
        assertThat(spec.getRouteParams().size(), equalTo(1));
        assertThat(spec.getRouteParams().getFirst("name1"), equalTo("value1"));

        spec.routeParam("name2", "value2");
        assertThat(spec.getRouteParams().size(), equalTo(2));
        assertThat(spec.getRouteParams().getFirst("name1"), equalTo("value1"));
        assertThat(spec.getRouteParams().getFirst("name2"), equalTo("value2"));
    }

    @Test
    public void addProcessorTest() {
        RequestSpecification specification = new RequestSpecificationImpl();
        assertThat(specification.getProcessors().size(), equalTo(0));

        Processor processor1 = new TestProcessor();
        Processor processor2 = new TestProcessor();

        specification.process(processor1, processor2);

        assertThat(specification.getProcessors().size(), equalTo(2));
        assertThat("Spec not contain specify handler", specification.getProcessors().contains(processor1));
        assertThat("Spec not contain specify handler", specification.getProcessors().contains(processor2));
    }

    @Test
    public void addProxyTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getProxy(), is(nullValue()));

        spec.proxy("host", 8090);

        assertThat(spec.getProxy().getHost(), equalTo("host"));
        assertThat(spec.getProxy().getPort(), equalTo(8090));
        assertThat(spec.getProxy().needAuth(), equalTo(false));
        assertThat(spec.getProxy().getUsername(), is(nullValue()));
        assertThat(spec.getProxy().getPassword(), is(nullValue()));
    }

    @Test
    public void addProxyWithAuthTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getProxy(), is(nullValue()));

        spec.proxy("host", 8090, "username", "pass");

        assertThat(spec.getProxy().getHost(), equalTo("host"));
        assertThat(spec.getProxy().getPort(), equalTo(8090));
        assertThat(spec.getProxy().needAuth(), equalTo(true));
        assertThat(spec.getProxy().getUsername(), equalTo("username"));
        assertThat(spec.getProxy().getPassword(), equalTo("pass"));
    }

    @Test
    public void addProxyObjectTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        assertThat(spec.getProxy(), is(nullValue()));

        Proxy proxy = new Proxy("host", 8080);
        spec.proxy(proxy);

        assertThat(spec.getProxy(), equalTo(proxy));
    }

    @Test
    public void addProcessorsListTest() {
        RequestSpecification specification = new RequestSpecificationImpl();
        assertThat(specification.getProcessors().size(), equalTo(0));

        Processor processor1 = new TestProcessor();
        Processor processor2 = new TestProcessor();

        List<Processor> processors = Arrays.asList(
                processor1,
                processor2
        );

        specification.process(processors);

        assertThat(specification.getProcessors().size(), equalTo(2));
        assertThat("Spec not contain specify handler", specification.getProcessors().contains(processor1));
        assertThat("Spec not contain specify handler", specification.getProcessors().contains(processor2));
    }

    @Test
    public void addConfigTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        SessionIdNameConfig sessionConfig = new SessionIdNameConfig();
        spec.config(sessionConfig);

        assertThat(spec.getConfigs(), contains(sessionConfig));
    }

    @Test
    public void addConfigsListTest() {
        RequestSpecification spec = new RequestSpecificationImpl();
        SessionIdNameConfig sessionConfig = new SessionIdNameConfig();
        ObjectMapperConfig objectMapperConfig = new ObjectMapperConfig();

        List<Config<?>> configs = Arrays.asList(sessionConfig, objectMapperConfig);
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
    public void mergeBodyTest() {
        String body1 = "Some body 1";
        String body2 = "Some body 2";
        RequestSpecification spec1 = new RequestSpecificationImpl().body(body1);
        RequestSpecification spec2 = new RequestSpecificationImpl().body(body2);

        spec2.mergeWith(spec1);
        assertThat(spec2.getBody(), equalTo(body1));
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
    public void mergeRouteParamsTest() {
        RequestSpecification spec1 = new RequestSpecificationImpl()
                .routeParam("name1", "value1");
        RequestSpecification spec2 = new RequestSpecificationImpl()
                .routeParam("name2", "value2");

        spec2.mergeWith(spec1);

        assertThat(spec2.getRouteParams().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec2.getRouteParams().getFirst("name1").equals("value1"));
        assertThat("Spec not contain specify handler", spec2.getRouteParams().getFirst("name2").equals("value2"));
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
    public void mergeProcessorsTest() {
        Processor processor1 = new TestProcessor();
        Processor processor2 = new TestProcessor();

        RequestSpecification spec1 = new RequestSpecificationImpl().process(processor1, processor2);
        RequestSpecification spec2 = new RequestSpecificationImpl().process(processor1);

        spec2.mergeWith(spec1);

        assertThat(spec2.getProcessors().size(), equalTo(3));
        assertThat("Spec not contain specify handler", spec2.getProcessors().contains(processor1));
        assertThat("Spec not contain specify handler", spec2.getProcessors().contains(processor2));
    }

    @Test
    public void mergeProxyTest() {
        RequestSpecification spec1 = new RequestSpecificationImpl();
        spec1.proxy("host1", 8090);

        RequestSpecification spec2 = new RequestSpecificationImpl();
        spec2.proxy("host2", 8092);

        spec2.mergeWith(spec1);

        assertThat(spec2.getProxy().getHost(), equalTo("host1"));
        assertThat(spec2.getProxy().getPort(), equalTo(8090));
    }

    private static class RegisterRequestData {
        private String login;

        private String password;
    }

    private static class LoginRequestData {
        private String login;

        private String password;
    }

    private static class TestProcessor implements Processor {
        @Override
        public void processRequest(RequestContext context) {

        }

        @Override
        public void processResponse(Response response) {

        }
    }
}
