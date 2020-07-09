package com.restsecure.core.request.specification;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.http.Header;
import com.restsecure.core.http.Parameter;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.processor.BiProcessor;
import com.restsecure.core.processor.PostResponseProcessor;
import com.restsecure.core.processor.PostResponseValidationProcessor;
import com.restsecure.core.processor.PreSendProcessor;
import com.restsecure.core.response.Response;
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
     * Allows you to process request and get some information about request or change request<br><br>
     *
     * @param processor            request processor
     * @param additionalProcessors additional request processors
     * @return RequestSpecification
     */
    RequestSpecification processRequest(PreSendProcessor processor, PreSendProcessor... additionalProcessors);

    /**
     * Allows you to process request and get some information about request or change request
     *
     * @param processors request processors list
     * @return RequestSpecification
     */
    RequestSpecification processRequest(List<PreSendProcessor> processors);

    /**
     * @return request processors
     */
    List<PreSendProcessor> getPreSendProcessors();

    /**
     * Allows you to process response and get some information about response or change response
     *
     * @param processor            response processor
     * @param additionalProcessors additional response processors
     * @return RequestSpecification
     */
    RequestSpecification processResponse(PostResponseProcessor processor, PostResponseProcessor... additionalProcessors);

    /**
     * Allows you to process response and get some information about response or change response
     *
     * @param processors response processors list
     * @return RequestSpecification
     */
    RequestSpecification processResponse(List<PostResponseProcessor> processors);

    /**
     * @return response processors
     */
    List<PostResponseProcessor> getPostResponseProcessors();

    /**
     * Allow you to specify BiProcessor to process request and response
     * For example, a {@link Session} class is a BiProcessor,<br>
     * which allows it to receive session ID from a response
     *
     * @param processor            processor
     * @param additionalProcessors additional processors
     * @return RequestSpecification
     */
    RequestSpecification process(BiProcessor processor, BiProcessor... additionalProcessors);

    /**
     * Allow you to specify BiProcessor to process request and response
     * For example, a {@link Session} class is a BiProcessor,<br>
     * which allows it to receive session ID from a response
     *
     * @param processors processors
     * @return RequestSpecification
     */
    RequestSpecification process(List<BiProcessor> processors);

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
     * @return ResponseValidation
     */
    RequestSpecification validate(PostResponseValidationProcessor validation, PostResponseValidationProcessor... additionalValidation);

    /**
     * Allows you to add a response validation<br>
     *
     * @return ResponseValidation
     */
    RequestSpecification validate(List<PostResponseValidationProcessor> validations);

    /**
     * @return List<ResponseValidation>
     */
    List<PostResponseValidationProcessor> getValidations();

    /**
     * Allows you to send request with specified specification
     *
     * @return Response
     */
    Response send();
}
