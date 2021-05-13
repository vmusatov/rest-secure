package com.restsecure.core.request.specification;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.configuration.configs.ObjectMapperConfig;
import com.restsecure.core.http.Header;
import com.restsecure.core.http.Parameter;
import com.restsecure.core.http.RequestMethod;
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

public class RequestSpecTest {

    @Test
    public void setUrlTest() {
        RequestSpec spec = new RequestSpecImpl();
        assertThat(spec.getUrl(), emptyString());

        spec.url("/path");
        assertThat(spec.getUrl(), equalTo("/path"));
    }

    @Test
    public void setPortTest() {
        RequestSpec spec = new RequestSpecImpl();
        assertThat(spec.getPort(), equalTo(0));

        spec.port(8080);
        assertThat(spec.getPort(), equalTo(8080));
    }

    @Test
    public void setMethodTest() {
        RequestSpec spec = new RequestSpecImpl();
        assertThat(spec.getMethod(), equalTo(null));

        spec.method(RequestMethod.GET);
        assertThat(spec.getMethod(), equalTo(RequestMethod.GET));
    }

    @Test
    public void setBodyTest() {
        RequestSpec spec = new RequestSpecImpl();
        assertThat(spec.getBody(), is(nullValue()));

        String body = "Some body";
        spec.body(body);

        assertThat(spec.getBody(), equalTo(body));
    }

    @Test
    public void addHeaderTest() {
        RequestSpec spec = new RequestSpecImpl();
        assertThat(spec.getHeaders().size(), equalTo(0));

        Header header = new Header("name", "value");
        spec.header(header);

        assertThat(spec.getHeaders().size(), equalTo(1));
        assertThat(spec.getHeaders().getFirst("name"), equalTo(header.getValue()));
    }

    @Test
    public void addHeaderWithNameAndValueTest() {
        RequestSpec spec = new RequestSpecImpl();
        assertThat(spec.getHeaders().size(), equalTo(0));

        Header header = new Header("name", "value");
        spec.header(header.getName(), header.getValue());

        assertThat(spec.getHeaders().size(), equalTo(1));
        assertThat(spec.getHeaders().getFirst("name"), equalTo(header.getValue()));
    }

    @Test
    public void addHeadersTest() {
        RequestSpec spec = new RequestSpecImpl();
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
        RequestSpec spec = new RequestSpecImpl();
        assertThat(spec.getParameters().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.param(param);

        assertThat(spec.getParameters().size(), equalTo(1));
        assertThat(spec.getParameters().getFirst("name"), equalTo("value"));
    }

    @Test
    public void addParamWithNameAndValueTest() {
        RequestSpec spec = new RequestSpecImpl();
        assertThat(spec.getParameters().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.param(param.getName(), param.getValue());

        assertThat(spec.getParameters().size(), equalTo(1));
        assertThat(spec.getParameters().getFirst("name"), equalTo("value"));
    }

    @Test
    public void addParamsTest() {
        RequestSpec spec = new RequestSpecImpl();
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
        RequestSpec spec = new RequestSpecImpl();
        assertThat(spec.getQueryParams().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.queryParam(param);

        assertThat(spec.getQueryParams().size(), equalTo(1));
        assertThat(spec.getQueryParams().getFirst("name"), equalTo("value"));
    }

    @Test
    public void addQueryParamAsNameAndValueTest() {
        RequestSpec spec = new RequestSpecImpl();
        assertThat(spec.getQueryParams().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.queryParam(param.getName(), param.getValue());

        assertThat(spec.getQueryParams().size(), equalTo(1));
        assertThat(spec.getQueryParams().getFirst("name"), equalTo("value"));
    }

    @Test
    public void addQueryParamsAsMapTest() {
        RequestSpec spec = new RequestSpecImpl();
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
        RequestSpec spec = new RequestSpecImpl();
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
        RequestSpec spec = new RequestSpecImpl();
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
        RequestSpec specification = new RequestSpecImpl();
        assertThat(specification.getProcessors().size(), equalTo(0));

        Processor processor1 = new TestProcessor();
        Processor processor2 = new TestProcessor();

        specification.process(processor1, processor2);

        assertThat(specification.getProcessors().size(), equalTo(2));
        assertThat("Spec not contain specify handler", specification.getProcessors().contains(processor1));
        assertThat("Spec not contain specify handler", specification.getProcessors().contains(processor2));
    }

    @Test
    public void addProcessorsListTest() {
        RequestSpec specification = new RequestSpecImpl();
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
        RequestSpec spec = new RequestSpecImpl();
        SessionIdNameConfig sessionConfig = new SessionIdNameConfig();
        spec.config(sessionConfig);

        assertThat(spec.getConfigs(), contains(sessionConfig));
    }

    @Test
    public void addConfigsListTest() {
        RequestSpec spec = new RequestSpecImpl();
        SessionIdNameConfig sessionConfig = new SessionIdNameConfig();
        ObjectMapperConfig objectMapperConfig = new ObjectMapperConfig();

        List<Config<?>> configs = Arrays.asList(sessionConfig, objectMapperConfig);
        spec.config(configs);

        assertThat(spec.getConfigs().size(), equalTo(2));
    }

    @Test
    public void mergeWithNotEmptyUrlTest() {
        RequestSpec spec1 = new RequestSpecImpl().url("example1.com");
        RequestSpec spec2 = new RequestSpecImpl().url("example2.com");

        spec2.mergeWith(spec1);
        assertThat(spec2.getUrl(), equalTo("example1.com"));
    }

    @Test
    public void mergeWithEmptyUrlTest() {
        RequestSpec spec1 = new RequestSpecImpl().url("");
        RequestSpec spec2 = new RequestSpecImpl().url("example2.com");

        spec2.mergeWith(spec1);
        assertThat(spec2.getUrl(), equalTo(""));
    }

    @Test
    public void mergePortTest() {
        RequestSpec spec1 = new RequestSpecImpl().port(8080);
        RequestSpec spec2 = new RequestSpecImpl().port(8081);

        spec2.mergeWith(spec1);
        assertThat(spec2.getPort(), equalTo(8080));
    }

    @Test
    public void mergeMethodTest() {
        RequestSpec spec1 = new RequestSpecImpl().method(RequestMethod.GET);
        RequestSpec spec2 = new RequestSpecImpl().method(RequestMethod.POST);

        spec2.mergeWith(spec1);
        assertThat(spec2.getMethod(), equalTo(RequestMethod.GET));
    }

    @Test
    public void mergeBodyTest() {
        String body1 = "Some body 1";
        String body2 = "Some body 2";
        RequestSpec spec1 = new RequestSpecImpl().body(body1);
        RequestSpec spec2 = new RequestSpecImpl().body(body2);

        spec2.mergeWith(spec1);
        assertThat(spec2.getBody(), equalTo(body1));
    }

    @Test
    public void mergeParamsTest() {
        Parameter param1 = new Parameter("name1", "value1");
        Parameter param2 = new Parameter("name2", "value2");
        RequestSpec spec1 = new RequestSpecImpl().param(param1);
        RequestSpec spec2 = new RequestSpecImpl().param(param2);

        spec2.mergeWith(spec1);

        assertThat(spec2.getParameters().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec2.getParameters().getFirst("name1").equals("value1"));
        assertThat("Spec not contain specify handler", spec2.getParameters().getFirst("name2").equals("value2"));
    }

    @Test
    public void mergeRouteParamsTest() {
        RequestSpec spec1 = new RequestSpecImpl()
                .routeParam("name1", "value1");
        RequestSpec spec2 = new RequestSpecImpl()
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
        RequestSpec spec1 = new RequestSpecImpl().header(header1);
        RequestSpec spec2 = new RequestSpecImpl().header(header2);

        spec2.mergeWith(spec1);

        assertThat(spec2.getHeaders().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec2.getHeaders().getFirst("name1").equals("value1"));
        assertThat("Spec not contain specify handler", spec2.getHeaders().getFirst("name2").equals("value2"));
    }

    @Test
    public void mergeProcessorsTest() {
        Processor processor1 = new TestProcessor();
        Processor processor2 = new TestProcessor();

        RequestSpec spec1 = new RequestSpecImpl().process(processor1, processor2);
        RequestSpec spec2 = new RequestSpecImpl().process(processor1);

        spec2.mergeWith(spec1);

        assertThat(spec2.getProcessors().size(), equalTo(3));
        assertThat("Spec not contain specify handler", spec2.getProcessors().contains(processor1));
        assertThat("Spec not contain specify handler", spec2.getProcessors().contains(processor2));
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
        public void processResponse(RequestContext context, Response response) {

        }
    }
}
