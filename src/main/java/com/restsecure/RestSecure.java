package com.restsecure;

import com.restsecure.http.RequestMethod;
import com.restsecure.request.RequestSender;
import com.restsecure.request.authentication.BasicAuthentication;
import com.restsecure.request.authentication.BearerTokenAuthentication;
import com.restsecure.request.authentication.NoAuthentication;
import com.restsecure.request.authentication.RequestAuthHandler;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.request.specification.RequestSpecificationImpl;
import com.restsecure.request.util.Session;
import com.restsecure.response.Response;
import com.restsecure.response.validation.ResponseOptionsValidation;

import java.util.List;

public class RestSecure {

    /**
     * Creates an empty request specification
     *
     * @return RequestSpecification
     */
    public static RequestSpecification spec() {
        return new RequestSpecificationImpl();
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
     * Creates an empty response validation
     *
     * @return ResponseValidation
     */
    public static ResponseOptionsValidation validation() {
        return new ResponseOptionsValidation();
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
     *    RequestSpecification request = get("url").auth(bearerAuth("YOUR_TOKEN"));
     * </pre>
     *
     * @param token access token
     * @return RequestAuthenticationHandler
     */
    public static RequestAuthHandler bearerToken(String token) {
        return new BearerTokenAuthentication(token);
    }
}
