package com.restsecure;

import com.restsecure.authentication.BasicAuthentication;
import com.restsecure.authentication.BearerTokenAuthentication;
import com.restsecure.authentication.NoAuthentication;
import com.restsecure.authentication.RequestAuthHandler;
import com.restsecure.http.RequestMethod;
import com.restsecure.request.RequestHandler;
import com.restsecure.request.RequestSender;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.request.specification.RequestSpecificationImpl;
import com.restsecure.response.Response;
import com.restsecure.session.Session;

import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * A global request handlers will be added to each request. 
     */
    private static final List<RequestHandler> globalRequestHandlers = new ArrayList<>();

    /**
     * Adding global request handlers that will be added to each request
     *
     * @param handler            request handler
     * @param additionalHandlers request handlers list
     */
    public static void handleRequest(RequestHandler handler, RequestHandler... additionalHandlers) {
        globalRequestHandlers.add(handler);
        globalRequestHandlers.addAll(Arrays.asList(additionalHandlers));
    }

    /**
     * Adding global request handlers that will be added to each request
     *
     * @param handlers request handlers list
     */
    public static void handleRequest(List<RequestHandler> handlers) {
        globalRequestHandlers.addAll(handlers);
    }

    /**
     * @return request handlers list
     */
    public static List<RequestHandler> getGlobalRequestHandlers() {
        return globalRequestHandlers;
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
     * Creates a request specification with specified data class
     *
     * @param data data class
     * @return RequestSpecification
     */
    public static RequestSpecification request(Object data) {
        return new RequestSpecificationImpl().data(data);
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
     * Allows you to send multiple requests in one session at once<br>
     * For example:
     * <pre>
     *     Session session = new Session();
     *     Request one = get("url);
     *     Request two = get("other_url);
     *
     *     send(session, one, two);
     * </pre>
     *
     * @param session         request {@link Session}
     * @param spec            RequestSpecification
     * @param additionalSpecs RequestSpecifications list
     * @return last request response
     */
    public static Response send(Session session, RequestSpecification spec, RequestSpecification... additionalSpecs) {
        return RequestSender.send(session, spec, additionalSpecs);
    }

    /**
     * Allows you to send multiple requests in one session at once<br>
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
     * @param session request {@link Session}
     * @param specs   RequestSpecifications list
     * @return last request response
     */
    public static Response send(Session session, List<RequestSpecification> specs) {
        return RequestSender.send(session, specs);
    }

    /**
     * Creates a RequestAuthenticationHandler that removes all authentications from the request<br>
     * For example:
     * <pre>
     *    RequestSpecification request = get("url").auth(noAuth());
     * </pre>
     *
     * @return RequestAuthenticationHandler
     */
    public static RequestAuthHandler noAuth() {
        return new NoAuthentication();
    }

    /**
     * Creates a basic request authentication<br>
     * For example:
     * <pre>
     *    RequestSpecification request = get("url").auth(basicAuth("username", "userpass"));
     * </pre>
     *
     * @param name     username
     * @param password user password
     * @return RequestAuthenticationHandler
     */
    public static RequestAuthHandler basicAuth(String name, String password) {
        return new BasicAuthentication(name, password);
    }

    /**
     * Creates a  bearer token request authentication<br>
     * For example:
     * <pre>
     *    RequestSpecification request = get("url").auth(bearerToken("YOUR_TOKEN"));
     * </pre>
     *
     * @param token access token
     * @return RequestAuthenticationHandler
     */
    public static RequestAuthHandler bearerToken(String token) {
        return new BearerTokenAuthentication(token);
    }
}
