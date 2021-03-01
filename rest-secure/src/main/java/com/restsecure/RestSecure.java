package com.restsecure;

import com.restsecure.authentication.BasicAuthentication;
import com.restsecure.authentication.BearerAuthentication;
import com.restsecure.core.Context;
import com.restsecure.core.configuration.configs.*;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.http.Header;
import com.restsecure.core.http.HeaderNames;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestSender;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.RequestSpecificationImpl;
import com.restsecure.core.response.Response;
import com.restsecure.logging.config.LogWriterConfig;
import com.restsecure.logging.config.RequestLogConfig;
import com.restsecure.logging.config.ResponseLogConfig;
import com.restsecure.session.SessionIdNameConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.restsecure.core.configuration.ConfigFactory.createDefaultConfig;

public class RestSecure {

    private static final String DEFAULT_URL = "http://localhost";

    /**
     * A base url will be added to each requests if its url does not have a domain name
     */
    @Getter
    @Setter
    private static String baseUrl = DEFAULT_URL;

    /**
     * A global specification will be added to each request. 
     */
    @Getter
    private static RequestSpecification globalSpecification = getDefaultSpecification();

    @Getter
    private static final Context context = new Context();

    public static void resetGlobalSpec() {
        globalSpecification = getDefaultSpecification();
    }

    private static RequestSpecification getDefaultSpecification() {
        return request().config(
                createDefaultConfig(HttpClientContextConfig.class),
                createDefaultConfig(HttpClientBuilderConfig.class),
                createDefaultConfig(ObjectMapperConfig.class),
                createDefaultConfig(SessionIdNameConfig.class),
                createDefaultConfig(LogWriterConfig.class),
                createDefaultConfig(RequestLogConfig.class),
                createDefaultConfig(ResponseLogConfig.class),
                createDefaultConfig(OverwriteParamsConfig.class),
                createDefaultConfig(OverwriteAllParamsConfig.class),
                createDefaultConfig(OverwriteHeadersConfig.class),
                createDefaultConfig(OverwriteAllHeadersConfig.class)
        );
    }

    /**
     * Creates an empty request specification
     *
     * @return RequestSpecification
     */
    public static RequestSpecification request() {
        return new RequestSpecificationImpl();
    }

    /**
     * Creates a request specification with the get method and the specified URL
     *
     * @param url request url
     * @return RequestSpecification
     */
    public static RequestSpecification get(String url) {
        return request()
                .url(url)
                .method(RequestMethod.GET);
    }

    /**
     * Creates a request specification with the post method and the specified URL
     *
     * @param url request url
     * @return RequestSpecification
     */
    public static RequestSpecification post(String url) {
        return request()
                .url(url)
                .method(RequestMethod.POST);
    }

    /**
     * Creates a request specification with the put method and the specified URL
     *
     * @param url request url
     * @return RequestSpecification
     */
    public static RequestSpecification put(String url) {
        return request()
                .url(url)
                .method(RequestMethod.PUT);
    }

    /**
     * Creates a request specification with the delete method and the specified URL
     *
     * @param url request url
     * @return RequestSpecification
     */
    public static RequestSpecification delete(String url) {
        return request()
                .url(url)
                .method(RequestMethod.DELETE);
    }

    /**
     * Creates a request specification with the head method and the specified URL
     *
     * @param url request url
     * @return RequestSpecification
     */
    public static RequestSpecification head(String url) {
        return request()
                .url(url)
                .method(RequestMethod.HEAD);
    }

    /**
     * Creates a request specification with the trace method and the specified URL
     *
     * @param url request url
     * @return RequestSpecification
     */
    public static RequestSpecification trace(String url) {
        return request()
                .url(url)
                .method(RequestMethod.TRACE);
    }

    /**
     * Creates a request specification with the options method and the specified URL
     *
     * @param url request url
     * @return RequestSpecification
     */
    public static RequestSpecification options(String url) {
        return request()
                .url(url)
                .method(RequestMethod.OPTIONS);
    }

    /**
     * Creates a request specification with the patch method and the specified URL
     *
     * @param url request url
     * @return RequestSpecification
     */
    public static RequestSpecification patch(String url) {
        return request()
                .url(url)
                .method(RequestMethod.PATCH);
    }

    /**
     * Allows you to send multiple requests at once<br>
     * For example:
     * <pre>
     *     send(
     *          get("url),
     *          get("other_url)
     *     );
     * </pre>
     *
     * @param spec            RequestSpecification
     * @param additionalSpecs RequestSpecifications list
     * @return last request response
     */
    public static Response send(RequestSpecification spec, RequestSpecification... additionalSpecs) {
        return RequestSender.send(spec, additionalSpecs);
    }

    /**
     * Allows you to send multiple requests at once<br>
     * For example:
     * <pre>
     *     List<RequestSpecification> requests = Arrays.asList(
     *          get("url),
     *          get("other_url)
     *     );
     *
     *     send(requests);
     * </pre>
     *
     * @param specs RequestSpecifications list
     * @return last request response
     */
    public static Response send(List<RequestSpecification> specs) {
        return RequestSender.send(specs);
    }

    /**
     * Allows you to send multiple requests at once<br>
     * For example:
     * <pre>
     *     Session session = new Session();
     *     RequestSpecification one = get("url);
     *     RequestSpecification two = get("other_url);
     *
     *     send(session, one, two);
     * </pre>
     *
     * @param processor       processor
     * @param spec            RequestSpecification
     * @param additionalSpecs RequestSpecifications list
     * @return last request response
     */
    public static Response send(Processor processor, RequestSpecification spec, RequestSpecification... additionalSpecs) {
        return RequestSender.send(processor, spec, additionalSpecs);
    }

    /**
     * Allows you to send multiple requests at once<br>
     * For example:
     * <pre>
     *     List<Processor> processors = Arrays.asList(bearerAuth("token"), new Session());
     *     RequestSpecification one = get("url);
     *     RequestSpecification two = get("other_url);
     *
     *     send(processors, one, two);
     * </pre>
     *
     * @param processors      Processors list
     * @param spec            RequestSpecification
     * @param additionalSpecs RequestSpecifications list
     * @return last request response
     */
    public static Response send(List<Processor> processors, RequestSpecification spec, RequestSpecification... additionalSpecs) {
        return RequestSender.send(processors, spec, additionalSpecs);
    }

    /**
     * Allows you to send multiple requests at once<br>
     * For example:
     * <pre>
     *     Session session = new Session();
     *     List<RequestSpecification> requests = Arrays.asList(
     *          get("url),
     *          get("other_url)
     *     );
     *
     *     send(session, requests);
     * </pre>
     *
     * @param processor processor
     * @param specs     RequestSpecifications list
     * @return last request response
     */
    public static Response send(Processor processor, List<RequestSpecification> specs) {
        return RequestSender.send(processor, specs);
    }

    /**
     * Allows you to send multiple requests at once<br>
     * For example:
     * <pre>
     *     List<Processor> processors = Arrays.asList(bearerAuth("token"), new Session());
     *     List<RequestSpecification> requests = Arrays.asList(
     *          get("url),
     *          get("other_url)
     *     );
     *
     *     send(processors, requests);
     * </pre>
     *
     * @param processors Processors list
     * @param specs      RequestSpecifications list
     * @return last request response
     */
    public static Response send(List<Processor> processors, List<RequestSpecification> specs) {
        return RequestSender.send(processors, specs);
    }

    /**
     * Creates a basic request authentication<br>
     * For example:
     * <pre>
     *    RequestSpecification request = get("url").process(basicAuth("username", "userpass"));
     * </pre>
     *
     * @param name     username
     * @param password user password
     * @return RequestAuthenticationHandler
     */
    public static Processor basicAuth(String name, String password) {
        return new BasicAuthentication(name, password);
    }

    /**
     * Creates a bearer token request authentication<br>
     * For example:
     * <pre>
     *    RequestSpecification request = get("url").process(bearerAuth("YOUR_TOKEN"));
     * </pre>
     *
     * @param token access token
     * @return RequestAuthenticationHandler
     */
    public static Processor bearerAuth(String token) {
        return new BearerAuthentication(token);
    }

    /**
     * Create Accept header with specify value
     *
     * @param value header value
     * @return Header
     */
    public static Header accept(String value) {
        return new Header(HeaderNames.ACCEPT, value);
    }
}
