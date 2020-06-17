package com.restsecure.request.specification;

import com.restsecure.authentication.RequestAuthHandler;
import com.restsecure.configuration.Config;
import com.restsecure.http.Header;
import com.restsecure.http.Parameter;
import com.restsecure.http.RequestMethod;
import com.restsecure.request.RequestHandler;
import com.restsecure.response.Response;
import com.restsecure.response.ResponseHandler;
import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.session.Session;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.List;

/**
 * Allows you to configure request
 */
public interface RequestSpecification {

    /**
     * Allows you to specify request url<br><br>
     * <pre>
     *     RequestSpecification spec = spec().url("url");
     * </pre>
     *
     * @param url request url
     * @return RequestSpecification
     */
    RequestSpecification url(String url);

    /**
     * @return request url
     */
    String getUrl();

    /**
     * Allows you to specify request port<br><br>
     * <pre>
     *     RequestSpecification spec = spec().port(8080);
     * </pre>
     *
     * @param port request port
     * @return RequestSpecification
     */
    RequestSpecification port(int port);

    /**
     * @return request port
     */
    int getPort();

    /**
     * Allows you to specify request method<br><br>
     * <pre>
     *     RequestSpecification spec = spec().method(GET);
     * </pre>
     *
     * @param method request method
     * @return RequestSpecification
     */
    RequestSpecification method(RequestMethod method);

    /**
     * @return request method
     */
    RequestMethod getMethod();

    /**
     * Allows you to specify request header<br><br>
     * <pre>
     *     RequestSpecification spec = get("url").header("name", "value");
     * </pre>
     *
     * @param name  header name
     * @param value header value
     * @return RequestSpecification
     */
    RequestSpecification header(String name, String value);

    /**
     * Allows you to specify request header<br><br>
     * <pre>
     *     Header header = new Header("name", "value")
     *     RequestSpecification spec = get("url").header(header);
     * </pre>
     *
     * @param header request header
     * @return RequestSpecification
     */
    RequestSpecification header(Header header);

    /**
     * Allows you to specify request headers<br><br>
     * <pre>
     *     List<Header> headers = Arrays.asList(
     *          new Header("name", "value"),
     *          new Header("name2", "value2")
     *     );
     *
     *     RequestSpecification spec = get("url").headers(headers);
     * </pre>
     *
     * @param headers list of request headers
     * @return RequestSpecification
     */
    RequestSpecification headers(List<Header> headers);

    /**
     * @return request headers
     */
    List<Header> getHeaders();

    /**
     * Allows you to specify request parameters<br>
     * For requests that contain the body, for example POST or PUT, parameters are set as form parameters,<br>
     * for other requests set as query parameters<br><br>
     * <pre>
     *     RequestSpecification spec = get("url").param("name", "value");
     * </pre>
     *
     * @param name  parameter name
     * @param value parameter value
     * @return RequestSpecification
     */
    RequestSpecification param(String name, String value);

    /**
     * Allows you to specify request parameters<br>
     * For requests that contain the body, for example POST or PUT, parameters are set as form parameters,<br>
     * for other requests set as query parameters<br><br>
     * <pre>
     *     Parameter param = new Parameter("name", "value");
     *     RequestSpecification spec = get("url").param(param);
     * </pre>
     *
     * @param parameter request parameter
     * @return RequestSpecification
     */
    RequestSpecification param(Parameter parameter);

    /**
     * Allows you to specify request parameters<br>
     * For requests that contain the body, for example POST or PUT, parameters are set as form parameters,<br>
     * for other requests set as query parameters<br><br>
     * <pre>
     *     List<Header> params = Arrays.asList(
     *          new Parameter("name", "value"),
     *          new Parameter("name2", "value2")
     *     );
     *
     *     RequestSpecification spec = get("url").params(params);
     * </pre>
     *
     * @param parameters request parameters
     * @return RequestSpecification
     */
    RequestSpecification params(List<Parameter> parameters);

    /**
     * @return request parameters
     */
    List<Parameter> getParameters();

    /**
     * Allows you to handle request and get some information about request or change request<br><br>
     * For example, a {@link Session} class is a request handler,<br>
     * which allows it to add a session header to the request
     *
     * @param handler request handler
     * @return RequestSpecification
     */
    RequestSpecification handleRequest(RequestHandler handler, RequestHandler... additionalHandlers);

    /**
     * Allows you to handle request and get some information about request or change request<br><br>
     * For example, a {@link Session} class is a request handler,<br>
     * which allows it to add a session header to the request
     *
     * @param handlers request handlers list
     * @return RequestSpecification
     */
    RequestSpecification handleRequest(List<RequestHandler> handlers);

    /**
     * @return request handlers
     */
    List<RequestHandler> getRequestHandlers();

    /**
     * Allows you to handle response and get some information about response or change response<br><br>
     * For example, a {@link Session} class is a response handler,<br>
     * which allows it to receive session ID from a response
     *
     * @param handler response handler
     * @return RequestSpecification
     */
    RequestSpecification handleResponse(ResponseHandler handler, ResponseHandler... additionalHandlers);

    /**
     * Allows you to handle response and get some information about response or change response<br><br>
     * For example, a {@link Session} class is a response handler,<br>
     * which allows it to receive session ID from a response
     *
     * @param handlers response handlers list
     * @return RequestSpecification
     */
    RequestSpecification handleResponse(List<ResponseHandler> handlers);

    /**
     * @return response handlers
     */
    List<ResponseHandler> getResponseHandlers();

    /**
     * Adds a {@link Session} class to request handlers and response handlers<br>
     * For example:
     * <pre>
     *     Session session = new Session();
     *
     *     get("url")
     *          .session(session)
     *     .send();
     *
     *     get("other_url")
     *          .session(session)
     *     .send();
     * </pre>
     * In the second request the session instance is reused for automatic setting of the session id.<br><br>
     *
     * @param session request session
     * @return RequestSpecification
     */
    RequestSpecification session(Session session);

    /**
     * Allows you to specify request authentication<br>
     * For example:
     * <pre>
     *    RequestSpecification spec = get("url").auth(basicAuth("username", "userpass"))
     * </pre>
     *
     * @param authentication request authentication
     * @return RequestSpecification
     */
    RequestSpecification auth(RequestAuthHandler authentication);

    /**
     * Allows you to add a specification from a data class
     *
     * @param data data class
     * @return RequestSpecification
     */
    RequestSpecification data(Object data);

    /**
     * @return request data
     */
    Object getData();

    /**
     * Allow tou to add configuration<br>
     * For example:
     * <pre>
     *     get("url")
     *          .config(sessionConfig().setSessionIdName("PHPSESSID"))
     *          .session(session)
     *          .send();
     * </pre>
     *
     * @param config            config
     * @param additionalConfigs additional configs
     * @return RequestSpecification
     */
    RequestSpecification config(Config config, Config... additionalConfigs);

    /**
     * Allow tou to add configuration<br>
     * For example:
     * <pre>
     *     List<Config> configs = Arrays.asList(config1, config2, config3);
     *     get("url")
     *          .config(configs)
     *          .send();
     * </pre>
     *
     * @param configs configs list
     * @return RequestSpecification
     */
    RequestSpecification config(List<Config> configs);

    /**
     * @return Configs list
     */
    List<Config> getConfigs();

    /**
     * Allows you to get a specific config
     *
     * @param configClass config class
     * @return Config
     */
    <T extends Config> T getConfig(Class<T> configClass);

    /**
     * @return HttpClientContext
     */
    HttpClientContext getContext();

    /**
     * @return HttpClientBuilder
     */
    HttpClientBuilder getBuilder();

    /**
     * Allows you to merge another specification into the current
     *
     * @param specification RequestSpecification
     * @return RequestSpecification
     */
    RequestSpecification mergeWith(RequestSpecification specification);

    /**
     * Allows you to add a response validation<br>
     * For example:
     * <pre>
     *     get("/getUser")
     *              .param("name", "Sam")
     *          .validate(
     *              statusCode(200),
     *              body("user.login", equalTo("Sam"))
     *          )
     *          .send();
     * </pre>
     * Validation of the response is added to the request, which verifies that status code is 200 and
     * the user.login field is equal to Sam
     *
     * @return ResponseValidation
     */
    RequestSpecification validate(ResponseValidation validation, ResponseValidation... additionalValidation);

    /**
     * Allows you to add a response validation<br>
     *
     * @return ResponseValidation
     */
    RequestSpecification validate(List<ResponseValidation> validations);

    /**
     * @return List<ResponseValidation>
     */
    List<ResponseValidation> getValidations();

    /**
     * Allows you to send request with specified specification
     *
     * @return Response
     */
    Response send();
}
