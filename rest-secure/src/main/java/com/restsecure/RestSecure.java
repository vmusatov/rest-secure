package com.restsecure;

import com.restsecure.authentication.BasicAuthentication;
import com.restsecure.authentication.BearerAuthentication;
import com.restsecure.core.Context;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.http.header.HeaderNames;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestSender;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.RequestSpecificationImpl;
import com.restsecure.core.response.Response;
import lombok.Getter;

import java.util.List;

public class RestSecure {

    public static final String DEFAULT_URL = "http://localhost";

    /**
     * A base url will be added to each requests if its url does not have a domain name
     */
    public static String baseUrl = DEFAULT_URL;

    /**
     * A global specification will be added to each request. 
     */
    public static RequestSpecification globalSpecification = new RequestSpecificationImpl();

    @Getter
    private static final Context context = new Context();

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
