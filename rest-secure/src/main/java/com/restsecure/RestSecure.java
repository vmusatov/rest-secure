package com.restsecure;

import com.restsecure.authentication.BasicAuthentication;
import com.restsecure.authentication.BearerAuthentication;
import com.restsecure.core.Context;
import com.restsecure.core.configuration.configs.*;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestSender;
import com.restsecure.core.request.specification.RequestSpec;
import com.restsecure.core.request.specification.RequestSpecImpl;
import com.restsecure.core.response.Response;
import com.restsecure.logging.config.LogWriterConfig;
import com.restsecure.logging.config.RequestLogConfig;
import com.restsecure.logging.config.ResponseLogConfig;
import com.restsecure.session.SessionIdNameConfig;
import lombok.Getter;

import java.util.List;

import static com.restsecure.core.configuration.ConfigFactory.createDefaultConfig;

public class RestSecure {
    /**
     * Default HttpClientConfig for all requests
     */
    private static HttpClientConfig clientConfig = createDefaultConfig(HttpClientConfig.class);

    /**
     * A global specification will be added to each request. 
     */
    @Getter
    private static RequestSpec globalRequestSpec = getDefaultRequestSpec();

    @Getter
    private static final Context context = new Context();

    public static void resetGlobalSpec() {
        globalRequestSpec = getDefaultRequestSpec();
    }

    private static RequestSpec getDefaultRequestSpec() {
        return request().config(
                clientConfig,
                createDefaultConfig(ProxyConfig.class),
                createDefaultConfig(SocketTimeoutConfig.class),
                createDefaultConfig(ConnectionTimeoutConfig.class),
                createDefaultConfig(EnableRedirectsConfig.class),
                createDefaultConfig(MaxRedirectsConfig.class),
                createDefaultConfig(CookieSpecConfig.class),
                createDefaultConfig(ObjectMapperConfig.class),
                createDefaultConfig(BaseUrlConfig.class),
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

    public static void shutdown() {
        clientConfig.getValue().close();
    }

    /**
     * Creates an empty request specification
     *
     * @return RequestSpec
     */
    public static RequestSpec request() {
        return new RequestSpecImpl();
    }

    /**
     * Creates a request specification with the get method and the specified URL
     *
     * @param url request url
     * @return RequestSpec
     */
    public static RequestSpec get(String url) {
        return request()
                .url(url)
                .method(RequestMethod.GET);
    }

    /**
     * Creates a request specification with the post method and the specified URL
     *
     * @param url request url
     * @return RequestSpec
     */
    public static RequestSpec post(String url) {
        return request()
                .url(url)
                .method(RequestMethod.POST);
    }

    /**
     * Creates a request specification with the put method and the specified URL
     *
     * @param url request url
     * @return RequestSpec
     */
    public static RequestSpec put(String url) {
        return request()
                .url(url)
                .method(RequestMethod.PUT);
    }

    /**
     * Creates a request specification with the delete method and the specified URL
     *
     * @param url request url
     * @return RequestSpec
     */
    public static RequestSpec delete(String url) {
        return request()
                .url(url)
                .method(RequestMethod.DELETE);
    }

    /**
     * Creates a request specification with the head method and the specified URL
     *
     * @param url request url
     * @return RequestSpec
     */
    public static RequestSpec head(String url) {
        return request()
                .url(url)
                .method(RequestMethod.HEAD);
    }

    /**
     * Creates a request specification with the trace method and the specified URL
     *
     * @param url request url
     * @return RequestSpec
     */
    public static RequestSpec trace(String url) {
        return request()
                .url(url)
                .method(RequestMethod.TRACE);
    }

    /**
     * Creates a request specification with the options method and the specified URL
     *
     * @param url request url
     * @return RequestSpec
     */
    public static RequestSpec options(String url) {
        return request()
                .url(url)
                .method(RequestMethod.OPTIONS);
    }

    /**
     * Creates a request specification with the patch method and the specified URL
     *
     * @param url request url
     * @return RequestSpec
     */
    public static RequestSpec patch(String url) {
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
     * @param spec            RequestSpec
     * @param additionalSpecs RequestSpec list
     * @return responses list
     */
    public static List<Response> send(RequestSpec spec, RequestSpec... additionalSpecs) {
        return RequestSender.send(spec, additionalSpecs);
    }

    /**
     * Allows you to send multiple requests at once<br>
     * For example:
     * <pre>
     *     List<RequestSpec> requests = Arrays.asList(
     *          get("url),
     *          get("other_url)
     *     );
     *
     *     send(requests);
     * </pre>
     *
     * @param specs RequestSpecs list
     * @return responses list
     */
    public static List<Response> send(List<RequestSpec> specs) {
        return RequestSender.send(specs);
    }

    /**
     * Allows you to send multiple requests at once<br>
     * For example:
     * <pre>
     *     Session session = new Session();
     *     RequestSpec one = get("url);
     *     RequestSpec two = get("other_url);
     *
     *     send(session, one, two);
     * </pre>
     *
     * @param processor       processor
     * @param spec            RequestSpec
     * @param additionalSpecs RequestSpecs list
     * @return responses list
     */
    public static List<Response> send(Processor processor, RequestSpec spec, RequestSpec... additionalSpecs) {
        return RequestSender.send(processor, spec, additionalSpecs);
    }

    /**
     * Allows you to send multiple requests at once<br>
     * For example:
     * <pre>
     *     List<Processor> processors = Arrays.asList(bearerAuth("token"), new Session());
     *     RequestSpec one = get("url);
     *     RequestSpec two = get("other_url);
     *
     *     send(processors, one, two);
     * </pre>
     *
     * @param processors      Processors list
     * @param spec            RequestSpec
     * @param additionalSpecs RequestSpec list
     * @return responses list
     */
    public static List<Response> send(List<Processor> processors, RequestSpec spec, RequestSpec... additionalSpecs) {
        return RequestSender.send(processors, spec, additionalSpecs);
    }

    /**
     * Allows you to send multiple requests at once<br>
     * For example:
     * <pre>
     *     Session session = new Session();
     *     List<RequestSpec> requests = Arrays.asList(
     *          get("url),
     *          get("other_url)
     *     );
     *
     *     send(session, requests);
     * </pre>
     *
     * @param processor processor
     * @param specs     RequestSpec list
     * @return responses list
     */
    public static List<Response> send(Processor processor, List<RequestSpec> specs) {
        return RequestSender.send(processor, specs);
    }

    /**
     * Allows you to send multiple requests at once<br>
     * For example:
     * <pre>
     *     List<Processor> processors = Arrays.asList(bearerAuth("token"), new Session());
     *     List<RequestSpec> requests = Arrays.asList(
     *          get("url),
     *          get("other_url)
     *     );
     *
     *     send(processors, requests);
     * </pre>
     *
     * @param processors Processors list
     * @param specs      RequestSpec list
     * @return responses list
     */
    public static List<Response> send(List<Processor> processors, List<RequestSpec> specs) {
        return RequestSender.send(processors, specs);
    }

    /**
     * Creates a basic request authentication<br>
     * For example:
     * <pre>
     *    RequestSpec request = get("url").process(basicAuth("username", "userpass"));
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
     *    RequestSpec request = get("url").process(bearerAuth("YOUR_TOKEN"));
     * </pre>
     *
     * @param token access token
     * @return RequestAuthenticationHandler
     */
    public static Processor bearerAuth(String token) {
        return new BearerAuthentication(token);
    }
}
