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
        RequestSpecImpl spec = new RequestSpecImpl();
        assertThat(spec.getUrl(), emptyString());

        spec.url("/path");
        assertThat(spec.getUrl(), equalTo("/path"));
    }

    @Test
    public void setPortTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
        assertThat(spec.getPort(), equalTo(0));

        spec.port(8080);
        assertThat(spec.getPort(), equalTo(8080));
    }

    @Test
    public void setMethodTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
        assertThat(spec.getMethod(), equalTo(null));

        spec.method(RequestMethod.GET);
        assertThat(spec.getMethod(), equalTo(RequestMethod.GET));
    }

    @Test
    public void setBodyTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
        assertThat(spec.getBody(), is(nullValue()));

        String body = "Some body";
        spec.body(body);

        assertThat(spec.getBody(), equalTo(body));
    }

    @Test
    public void addHeaderTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
        assertThat(spec.getHeaders().size(), equalTo(0));

        Header header = new Header("name", "value");
        spec.header(header);

        assertThat(spec.getHeaders().size(), equalTo(1));
        assertThat(spec.getHeaders().getFirst("name"), equalTo(header.getValue()));
    }

    @Test
    public void addHeaderWithNameAndValueTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
        assertThat(spec.getHeaders().size(), equalTo(0));

        Header header = new Header("name", "value");
        spec.header(header.getName(), header.getValue());

        assertThat(spec.getHeaders().size(), equalTo(1));
        assertThat(spec.getHeaders().getFirst("name"), equalTo(header.getValue()));
    }

    @Test
    public void addHeadersTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
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
        RequestSpecImpl spec = new RequestSpecImpl();
        assertThat(spec.getParams().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.param(param);

        assertThat(spec.getParams().size(), equalTo(1));
        assertThat(spec.getParams().getFirst("name"), equalTo("value"));
    }

    @Test
    public void addParamWithNameAndValueTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
        assertThat(spec.getParams().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.param(param.getName(), param.getValue());

        assertThat(spec.getParams().size(), equalTo(1));
        assertThat(spec.getParams().getFirst("name"), equalTo("value"));
    }

    @Test
    public void addParamsTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
        assertThat(spec.getParams().size(), equalTo(0));

        Parameter param1 = new Parameter("name", "value");
        Parameter param2 = new Parameter("name2", "value2");
        Parameter param3 = new Parameter("name3", "value3");
        List<Parameter> params = Arrays.asList(param1, param2, param3);
        spec.params(params);

        assertThat(spec.getParams().size(), equalTo(3));
        assertThat(spec.getParams().getFirst("name"), equalTo("value"));
        assertThat(spec.getParams().getFirst("name2"), equalTo("value2"));
        assertThat(spec.getParams().getFirst("name3"), equalTo("value3"));
    }

    @Test
    public void addQueryParamTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
        assertThat(spec.getQueryParams().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.queryParam(param);

        assertThat(spec.getQueryParams().size(), equalTo(1));
        assertThat(spec.getQueryParams().getFirst("name"), equalTo("value"));
    }

    @Test
    public void addQueryParamAsNameAndValueTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
        assertThat(spec.getQueryParams().size(), equalTo(0));

        Parameter param = new Parameter("name", "value");
        spec.queryParam(param.getName(), param.getValue());

        assertThat(spec.getQueryParams().size(), equalTo(1));
        assertThat(spec.getQueryParams().getFirst("name"), equalTo("value"));
    }

    @Test
    public void addQueryParamsAsMapTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
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
        RequestSpecImpl spec = new RequestSpecImpl();
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
        RequestSpecImpl spec = new RequestSpecImpl();
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
        RequestSpecImpl specification = new RequestSpecImpl();
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
        RequestSpecImpl specification = new RequestSpecImpl();
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
        RequestSpecImpl spec = new RequestSpecImpl();
        SessionIdNameConfig sessionConfig = new SessionIdNameConfig();
        spec.config(sessionConfig);

        assertThat(spec.getConfigs(), contains(sessionConfig));
    }

    @Test
    public void addConfigsListTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
        SessionIdNameConfig sessionConfig = new SessionIdNameConfig();
        ObjectMapperConfig objectMapperConfig = new ObjectMapperConfig();

        List<Config<?>> configs = Arrays.asList(sessionConfig, objectMapperConfig);
        spec.config(configs);

        assertThat(spec.getConfigs().size(), equalTo(2));
    }

    @Test
    public void mergeWithNotEmptyUrlTest() {
        RequestSpecImpl spec1 = new RequestSpecImpl().url("example1.com");
        RequestSpecImpl spec2 = new RequestSpecImpl().url("example2.com");

        spec2.mergeWith(spec1);
        assertThat(spec2.getUrl(), equalTo("example1.com"));
    }

    @Test
    public void mergeWithEmptyUrlTest() {
        RequestSpecImpl spec1 = new RequestSpecImpl().url("");
        RequestSpecImpl spec2 = new RequestSpecImpl().url("example2.com");

        spec2.mergeWith(spec1);
        assertThat(spec2.getUrl(), equalTo(""));
    }

    @Test
    public void mergePortTest() {
        RequestSpecImpl spec1 = new RequestSpecImpl().port(8080);
        RequestSpecImpl spec2 = new RequestSpecImpl().port(8081);

        spec2.mergeWith(spec1);
        assertThat(spec2.getPort(), equalTo(8080));
    }

    @Test
    public void mergeMethodTest() {
        RequestSpecImpl spec1 = new RequestSpecImpl().method(RequestMethod.GET);
        RequestSpecImpl spec2 = new RequestSpecImpl().method(RequestMethod.POST);

        spec2.mergeWith(spec1);
        assertThat(spec2.getMethod(), equalTo(RequestMethod.GET));
    }

    @Test
    public void mergeBodyTest() {
        String body1 = "Some body 1";
        String body2 = "Some body 2";

        RequestSpecImpl spec1 = new RequestSpecImpl();
        spec1.body(body1);
        RequestSpecImpl spec2 = new RequestSpecImpl();
        spec2.body(body2);

        spec2.mergeWith(spec1);
        assertThat(spec2.getBody(), equalTo(body1));
    }

    @Test
    public void mergeParamsTest() {
        Parameter param1 = new Parameter("name1", "value1");
        Parameter param2 = new Parameter("name2", "value2");

        RequestSpecImpl spec1 = new RequestSpecImpl();
        spec1.param(param1);
        RequestSpecImpl spec2 = new RequestSpecImpl();
        spec2.param(param2);

        spec2.mergeWith(spec1);

        assertThat(spec2.getParams().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec2.getParams().getFirst("name1").equals("value1"));
        assertThat("Spec not contain specify handler", spec2.getParams().getFirst("name2").equals("value2"));
    }

    @Test
    public void mergeRouteParamsTest() {
        RequestSpecImpl spec1 = new RequestSpecImpl();
        spec1.routeParam("name1", "value1");
        RequestSpecImpl spec2 = new RequestSpecImpl();
        spec2.routeParam("name2", "value2");

        spec2.mergeWith(spec1);

        assertThat(spec2.getRouteParams().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec2.getRouteParams().getFirst("name1").equals("value1"));
        assertThat("Spec not contain specify handler", spec2.getRouteParams().getFirst("name2").equals("value2"));
    }

    @Test
    public void mergeHeadersTest() {
        Header header1 = new Header("name1", "value1");
        Header header2 = new Header("name2", "value2");

        RequestSpecImpl spec1 = new RequestSpecImpl();
        spec1.header(header1);
        RequestSpecImpl spec2 = new RequestSpecImpl();
        spec2.header(header2);

        spec2.mergeWith(spec1);

        assertThat(spec2.getHeaders().size(), equalTo(2));
        assertThat("Spec not contain specify handler", spec2.getHeaders().getFirst("name1").equals("value1"));
        assertThat("Spec not contain specify handler", spec2.getHeaders().getFirst("name2").equals("value2"));
    }

    @Test
    public void mergeProcessorsTest() {
        Processor processor1 = new TestProcessor();
        Processor processor2 = new TestProcessor();

        RequestSpecImpl spec1 = new RequestSpecImpl();
        spec1.process(processor1, processor2);
        RequestSpecImpl spec2 = new RequestSpecImpl();
        spec2.process(processor1);

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
