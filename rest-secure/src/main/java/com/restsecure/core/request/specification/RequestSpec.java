package com.restsecure.core.request.specification;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.http.Cookie;
import com.restsecure.core.http.Header;
import com.restsecure.core.http.Parameter;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.util.MultiKeyMap;
import com.restsecure.session.Session;

import java.util.List;
import java.util.Map;

/**
 * Allows you to configure request
 */
public interface RequestSpec {

    /**
     * Allows you to specify request url<br><br>
     * <pre>
     *     RequestSpec spec = spec().url("url");
     * </pre>
     *
     * @param url request url
     * @return RequestSpec
     */
    RequestSpec url(String url);

    /**
     * Allows you to specify request port<br><br>
     * <pre>
     *     RequestSpec spec = spec().port(8080);
     * </pre>
     *
     * @param port request port
     * @return RequestSpec
     */
    RequestSpec port(int port);

    /**
     * Allows you to specify request method<br><br>
     * <pre>
     *     RequestSpec spec = spec().method(GET);
     * </pre>
     *
     * @param method request method
     * @return RequestSpec
     */
    RequestSpec method(RequestMethod method);

    /**
     * Allow you to set request body
     *
     * @param body request body
     * @return RequestSpec
     */
    RequestSpec body(Object body);

    /**
     * Allows you to specify request header<br><br>
     * <pre>
     *     RequestSpec spec = get("url").header("name", "value");
     * </pre>
     *
     * @param name             header name
     * @param value            header value
     * @param additionalValues additional header values
     * @return RequestSpec
     */
    RequestSpec header(String name, Object value, Object... additionalValues);

    /**
     * Allows you to specify request header<br><br>
     * <pre>
     *     Header header = new Header("name", "value")
     *     RequestSpec spec = get("url").header(header);
     * </pre>
     *
     * @param header request header
     * @return RequestSpec
     */
    RequestSpec header(Header header, Header... additionalHeaders);

    /**
     * Allows you to specify request headers<br><br>
     * <pre>
     *     List<Header> headers = Arrays.asList(
     *          new Header("name", "value"),
     *          new Header("name2", "value2")
     *     );
     *
     *     RequestSpec spec = get("url").headers(headers);
     * </pre>
     *
     * @param headers list of request headers
     * @return RequestSpec
     */
    RequestSpec headers(List<Header> headers);

    /**
     * Allows you to specify request headers
     *
     * @param headers request headers
     * @return RequestSpec
     */
    RequestSpec headers(Map<String, ?> headers);

    /**
     * Allows you to specify request parameters<br>
     * For requests that contain the body, for example POST or PUT, parameters are set as form parameters,<br>
     * for other requests set as query parameters<br><br>
     * <pre>
     *     RequestSpec spec = get("url").param("name", "value");
     * </pre>
     *
     * @param name             parameter name
     * @param value            parameter value
     * @param additionalValues additional param values
     * @return RequestSpec
     */
    RequestSpec param(String name, Object value, Object... additionalValues);

    /**
     * Allows you to specify request parameters<br>
     * For requests that contain the body, for example POST or PUT, parameters are set as form parameters,<br>
     * for other requests set as query parameters<br><br>
     * <pre>
     *     Parameter param = new Parameter("name", "value");
     *     RequestSpec spec = get("url").param(param);
     * </pre>
     *
     * @param parameter request parameter
     * @return RequestSpec
     */
    RequestSpec param(Parameter parameter);

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
     *     RequestSpec spec = get("url").params(params);
     * </pre>
     *
     * @param parameters request parameters
     * @return RequestSpec
     */
    RequestSpec params(List<Parameter> parameters);

    /**
     * Allows you to specify request parameters
     *
     * @param parameters request parameters
     * @return RequestSpec
     */
    RequestSpec params(Map<String, ?> parameters);

    /**
     * Allows you to specify query parameters
     *
     * @return RequestSpec
     */
    RequestSpec queryParam(String name, Object... value);

    /**
     * Allows you to specify query parameters
     *
     * @return
     */
    RequestSpec queryParam(Parameter param);

    /**
     * Allows you to specify query parameters
     *
     * @return RequestSpec
     */
    RequestSpec queryParams(List<Parameter> params);

    /**
     * Allows you to specify query parameters
     *
     * @return RequestSpec
     */
    RequestSpec queryParams(Map<String, ?> params);

    /**
     * Allow you to specify route param, for example:
     * <pre>
     *     get("http://localhost/users/{user_id}")
     *          .routeParam("user_id", 1)
     * </pre>
     *
     * @param name  param name
     * @param value param value
     * @return RequestSpec
     */
    RequestSpec routeParam(String name, Object value);

    /**
     * Allow you to specify request cookies
     * <pre>
     *     RequestSpec spec = get("url").cookie("name", "value");
     * </pre>
     *
     * @param name             cookie name
     * @param value            cookie value
     * @param additionalValues additional values
     * @return RequestSpec
     */
    RequestSpec cookie(String name, Object value, Object... additionalValues);

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
     *     RequestSpec spec = get("url").cookie(cookie);
     * </pre>
     *
     * @param cookie Cookie
     * @return RequestSpec
     */
    RequestSpec cookie(Cookie cookie);

    /**
     * Allow you to specify detailed request cookies
     * <pre>
     *     List<Cookie> cookies = Arrays.asList(
     *          new Cookie("name", "value"),
     *          new Cookie("name2", "value2")
     *     );
     *
     *     RequestSpec spec = get("url").cookies(cookies);
     * </pre>
     *
     * @param cookies Cookie list
     * @return RequestSpec
     */
    RequestSpec cookies(List<Cookie> cookies);

    /**
     * Allow you to specify request cookies
     *
     * @param cookies request cookies
     * @return RequestSpec
     */
    RequestSpec cookies(Map<String, ?> cookies);

    /**
     * Allow you to specify Processor to process request and response
     * For example, a {@link Session} class is a Processor,<br>
     * which allows it to receive session ID from a response
     *
     * @param processor            processor
     * @param additionalProcessors additional processors
     * @return RequestSpec
     */
    RequestSpec process(Processor processor, Processor... additionalProcessors);

    /**
     * Allow you to specify Processor to process request and response
     * For example, a {@link Session} class is a Processor,<br>
     * which allows it to receive session ID from a response
     *
     * @param processors processors
     * @return RequestSpec
     */
    RequestSpec process(List<Processor> processors);

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
     * @return RequestSpec
     */
    RequestSpec config(Config<?> config, Config<?>... additionalConfigs);

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
     * @return RequestSpec
     */
    RequestSpec config(List<Config<?>> configs);

    /**
     * Allows you to merge another specification into the current
     *
     * @param spec RequestSpec
     * @return RequestSpec
     */
    RequestSpec mergeWith(RequestSpec spec);

    /**
     * Allows you to add a response validation<br>
     * For example:
     * <pre>
     *     get("/getUser")
     *          .param("name", "Sam")
     *          .expect(
     *              statusCode(200),
     *              body("user.login", equalTo("Sam"))
     *          )
     *          .send();
     * </pre>
     * Validation of the response is added to the request, which verifies that status code is 200 and
     * the user.login field is equal to Sam
     *
     * @return RequestSpec
     */
    RequestSpec expect(Validation validation, Validation... additionalValidation);

    /**
     * Allows you to add a response validation<br>
     *
     * @return RequestSpec
     */
    RequestSpec expect(List<Validation> validations);

    /**
     * Syntactic sugar
     *
     * @return RequestSpec
     */
    RequestSpec and();

    /**
     * Allows you to send request with specified request spec
     *
     * @return Response
     */
    Response send();
}
