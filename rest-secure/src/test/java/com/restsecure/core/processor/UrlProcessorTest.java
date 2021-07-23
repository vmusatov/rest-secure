package com.restsecure.core.processor;

import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpecImpl;
import org.testng.annotations.Test;

import static com.restsecure.Configs.baseUrl;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UrlProcessorTest {

    @Test
    public void setBaseUrlTest() {
        RequestSpecImpl spec = new RequestSpecImpl();
        spec.url("/user")
                .method(RequestMethod.GET)
                .config(baseUrl("http://localhost"));

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user"));
    }

    @Test
    public void notSetBaseUrlTest() {
        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url("http://otherhost/user")
                .method(RequestMethod.GET)
                .config(baseUrl("http://localhost"));

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://otherhost/user"));
    }

    @Test
    public void applyRouteParamTest() {
        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url("http://localhost/user/{user_id}")
                .method(RequestMethod.GET)
                .routeParam("user_id", 10);

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user/10"));
    }

    @Test
    public void applyRouteParamInBaseUrlTest() {

        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url("/user/{user_id}")
                .method(RequestMethod.GET)
                .config(baseUrl("http://{host}"))
                .routeParam("host", "localhost")
                .routeParam("user_id", 10);

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user/10"));
    }

    @Test
    public void applyFewEqualRouteParamsTest() {
        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url("http://localhost/user/{user_id}/{user_id}")
                .method(RequestMethod.GET)
                .routeParam("user_id", 10);

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user/10/10"));
    }

    @Test
    public void applyFewRouteParamsInBaseUrlTest() {

        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url("/user/{user_id}")
                .method(RequestMethod.GET)
                .config(baseUrl("http://{host}:{port}"))
                .routeParam("port", 8080)
                .routeParam("host", "localhost")
                .routeParam("user_id", 10);

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost:8080/user/10"));
    }

    @Test
    public void applyPortTest() {
        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url("http://localhost/user")
                .method(RequestMethod.GET)
                .port(8080);

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost:8080/user"));
    }

    @Test
    public void applyParamsTest() {
        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url("http://localhost/user")
                .method(RequestMethod.GET)
                .param("param1", "value1")
                .param("param2", "value2");

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();
        processor.processRequest(context);

        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user?param1=value1&param2=value2"));
    }

    @Test
    public void notApplyParamsWhenRequestHasBodyTest() {
        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url("http://localhost/user")
                .method(RequestMethod.POST)
                .param("param1", "value1")
                .param("param2", "value2");

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();
        processor.processRequest(context);

        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user"));
    }

    @Test
    public void applyQueryParamsTest() {
        RequestSpecImpl spec = new RequestSpecImpl();

        spec.url("http://localhost/user")
                .method(RequestMethod.GET)
                .queryParam("param1", "value1")
                .queryParam("param2", "value2");

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();
        processor.processRequest(context);

        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user?param1=value1&param2=value2"));
    }
}
