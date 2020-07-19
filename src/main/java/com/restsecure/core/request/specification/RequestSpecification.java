package com.restsecure.core.request.specification;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.http.cookie.Cookie;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.http.param.Parameter;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.util.MultiKeyMap;
import com.restsecure.session.Session;

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
     * @param name             header name
     * @param value            header value
     * @param additionalValues additional header values
     * @return RequestSpecification
     */
    RequestSpecification header(String name, Object value, Object... additionalValues);

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
    MultiKeyMap<String, Object> getHeaders();

    /**
     * Allows you to specify request parameters<br>
     * For requests that contain the body, for example POST or PUT, parameters are set as form parameters,<br>
     * for other requests set as query parameters<br><br>
     * <pre>
     *     RequestSpecification spec = get("url").param("name", "value");
     * </pre>
     *
     * @param name             parameter name
     * @param value            parameter value
     * @param additionalValues additional param values
     * @return RequestSpecification
     */
    RequestSpecification param(String name, Object value, Object... additionalValues);

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
    MultiKeyMap<String, Object> getParameters();

    /**
     * Allow you to specify route param, for example:
     * <pre>
     *     get("http://localhost/users/{user_id}")
     *          .routeParam("user_id", 1)
     * </pre>
     * @param name param name
     * @param value param value
     * @return RequestSpecification
     */
    RequestSpecification routeParam(String name, Object value);

    /**
     * @return route params
     */
    MultiKeyMap<String, Object> getRouteParams();

    /**
     * Allow you to specify request cookies
     * <pre>
     *     RequestSpecification spec = get("url").cookie("name", "value");
     * </pre>
     *
     * @param name             cookie name
     * @param value            cookie value
     * @param additionalValues additional values
     * @return RequestSpecification
     */
    RequestSpecification cookie(String name, Object value, Object... additionalValues);

    /**
     * Allow you to specify detailed request cookie
     * <pre>
     *      Cookie cookie = Cookie.builder()
     *          .name("name")
     *          .value("value")
     *          .comment("comment")
     *          .secure(true)
     *          .build();
     *
     *     RequestSpecification spec = get("url").cookie(cookie);
     * </pre>
     *
     * @param cookie Cookie
     * @return RequestSpecification
     */
    RequestSpecification cookie(Cookie cookie);

    /**
     * Allow you to specify detailed request cookies
     * <pre>
     *     List<Cookie> cookies = Arrays.asList(
     *          new Cookie("name", "value"),
     *          new Cookie("name2", "value2")
     *     );
     *
     *     RequestSpecification spec = get("url").cookies(cookies);
     * </pre>
     *
     * @param cookies Cookie list
     * @return RequestSpecification
     */
    RequestSpecification cookies(List<Cookie> cookies);

    /**
     * @return cookies with Object value
     */
    MultiKeyMap<String, Object> getCookiesWithValueToSerialize();

    /**
     * Allow you to specify Processor to process request and response
     * For example, a {@link Session} class is a Processor,<br>
     * which allows it to receive session ID from a response
     *
     * @param processor            processor
     * @param additionalProcessors additional processors
     * @return RequestSpecification
     */
    RequestSpecification process(Processor processor, Processor... additionalProcessors);

    /**
     * Allow you to specify Processor to process request and response
     * For example, a {@link Session} class is a Processor,<br>
     * which allows it to receive session ID from a response
     *
     * @param processors processors
     * @return RequestSpecification
     */
    RequestSpecification process(List<Processor> processors);

    /**
     * @return Processors list
     */
    List<Processor> getProcessors();

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
     * @return RequestSpecification
     */
    RequestSpecification validate(Validation validation, Validation... additionalValidation);

    /**
     * Allows you to add a response validation<br>
     *
     * @return RequestSpecification
     */
    RequestSpecification validate(List<Validation> validations);

    /**
     * @return List<ResponseValidation>
     */
    List<Validation> getValidations();

    /**
     * Allows you to send request with specified specification
     *
     * @return Response
     */
    Response send();
}
