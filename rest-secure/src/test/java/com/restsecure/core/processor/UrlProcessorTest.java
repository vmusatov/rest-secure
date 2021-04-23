package com.restsecure.core.processor;

import com.restsecure.RestSecure;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.RequestSpec;
import org.testng.annotations.Test;

import static com.restsecure.Configs.baseUrl;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UrlProcessorTest {

    @Test
    public void setBaseUrlTest() {
        RequestSpec spec = RestSecure.get("/user").config(baseUrl("http://localhost"));
        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user"));
    }

    @Test
    public void notSetBaseUrlTest() {
        RequestSpec spec = RestSecure.get("http://otherhost/user").config(baseUrl("http://localhost"));
        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://otherhost/user"));
    }

    @Test
    public void applyRouteParamTest() {
        RequestSpec spec = RestSecure.get("http://localhost/user/{user_id}").routeParam("user_id", 10);
        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user/10"));
    }

    @Test
    public void applyRouteParamInBaseUrlTest() {

        RequestSpec spec = RestSecure.get("/user/{user_id}")
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
        RequestSpec spec = RestSecure.get("http://localhost/user/{user_id}/{user_id}").routeParam("user_id", 10);
        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user/10/10"));
    }

    @Test
    public void applyFewRouteParamsInBaseUrlTest() {

        RequestSpec spec = RestSecure.get("/user/{user_id}")
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
        RequestSpec spec = RestSecure.get("http://localhost/user").port(8080);
        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();

        processor.processRequest(context);
        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost:8080/user"));
    }

    @Test
    public void applyParamsTest() {
        RequestSpec spec = RestSecure.get("http://localhost/user")
                .param("param1", "value1")
                .param("param2", "value2");

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();
        processor.processRequest(context);

        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user?param1=value1&param2=value2"));
    }

    @Test
    public void notApplyParamsWhenRequestHasBodyTest() {
        RequestSpec spec = RestSecure.post("http://localhost/user")
                .param("param1", "value1")
                .param("param2", "value2");

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();
        processor.processRequest(context);

        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user"));
    }

    @Test
    public void applyQueryParamsTest() {
        RequestSpec spec = RestSecure.get("http://localhost/user")
                .queryParam("param1", "value1")
                .queryParam("param2", "value2");

        RequestContext context = new RequestContext(spec);
        UrlProcessor processor = new UrlProcessor();
        processor.processRequest(context);

        assertThat(context.getRequestSpec().getUrl(), equalTo("http://localhost/user?param1=value1&param2=value2"));
    }
}
