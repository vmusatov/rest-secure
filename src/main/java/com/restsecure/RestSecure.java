package com.restsecure;

import com.restsecure.authentication.BasicAuthentication;
import com.restsecure.authentication.BearerTokenAuthentication;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.processor.BiProcessor;
import com.restsecure.core.processor.PreSendProcessor;
import com.restsecure.core.request.RequestSender;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.RequestSpecificationImpl;
import com.restsecure.core.response.Response;
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
     * A global request processors will be added to each request. 
     */
    private static final List<PreSendProcessor> globalPreSendProcessors = new ArrayList<>();

    /**
     * Adding global request processors that will be added to each request
     *
     * @param processor            request processor
     * @param additionalProcessors request handlers list
     */
    public static void preSendProcess(PreSendProcessor processor, PreSendProcessor... additionalProcessors) {
        globalPreSendProcessors.add(processor);
        globalPreSendProcessors.addAll(Arrays.asList(additionalProcessors));
    }

    /**
     * Adding global request processors that will be added to each request
     *
     * @param processors request processors list
     */
    public static void preSendProcess(List<PreSendProcessor> processors) {
        globalPreSendProcessors.addAll(processors);
    }

    /**
     * @return PreSendProcessor list
     */
    public static List<PreSendProcessor> getGlobalPreSendProcessors() {
        return globalPreSendProcessors;
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
    public static Response send(BiProcessor session, RequestSpecification spec, RequestSpecification... additionalSpecs) {
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
    public static Response send(BiProcessor session, List<RequestSpecification> specs) {
        return RequestSender.send(session, specs);
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
    public static PreSendProcessor basicAuth(String name, String password) {
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
    public static PreSendProcessor bearerToken(String token) {
        return new BearerTokenAuthentication(token);
    }
}
