package com.restsecure.core.request.specification;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.util.MultiKeyMap;

import java.util.List;

public interface MutableRequestSpec extends RequestSpec {
    /**
     * @return request url
     */
    String getUrl();

    /**
     * @return request port
     */
    int getPort();

    /**
     * @return request method
     */
    RequestMethod getMethod();

    /**
     * @return reqeust body
     */
    Object getBody();

    /**
     * @return request headers
     */
    MultiKeyMap<String, Object> getHeaders();

    /**
     * Clear all headers
     *
     * @return MutableRequestSpec
     */
    MutableRequestSpec clearHeaders();

    /**
     * Remove all headers with specified name
     *
     * @param name The header name
     * @return MutableRequestSpec
     */
    MutableRequestSpec removeHeader(String name);

    /**
     * Replace all headers with specified name
     *
     * @param name     The header name
     * @param newValue New header value
     * @return MutableRequestSpec
     */
    MutableRequestSpec replaceHeader(String name, Object newValue);

    /**
     * @return request parameters
     */
    MultiKeyMap<String, Object> getParams();

    /**
     * Clear all parameters
     *
     * @return MutableRequestSpec
     */
    MutableRequestSpec clearParams();

    /**
     * Remove all parameters with specified name
     *
     * @param name The parameter name
     * @return MutableRequestSpec
     */
    MutableRequestSpec removeParam(String name);

    /**
     * Replace all parameters with specified name
     *
     * @param name     The parameter name
     * @param newValue New parameter value
     * @return MutableRequestSpec
     */
    MutableRequestSpec replaceParam(String name, Object newValue);

    /**
     * @return RequestSpec
     */
    MultiKeyMap<String, Object> getQueryParams();

    /**
     * Clear all query parameters
     *
     * @return MutableRequestSpec
     */
    MutableRequestSpec clearQueryParams();

    /**
     * Remove all query parameters with specified name
     *
     * @param name The query parameter name
     * @return MutableRequestSpec
     */
    MutableRequestSpec removeQueryParam(String name);

    /**
     * Replace all query parameters with specified name
     *
     * @param name     The query parameter name
     * @param newValue New query parameter value
     * @return MutableRequestSpec
     */
    MutableRequestSpec replaceQueryParam(String name, Object newValue);

    /**
     * @return route params
     */
    MultiKeyMap<String, Object> getRouteParams();

    /**
     * Clear all route parameters
     *
     * @return MutableRequestSpec
     */
    MutableRequestSpec clearRouteParams();

    /**
     * Remove all route parameters with specified name
     *
     * @param name The route parameter name
     * @return MutableRequestSpec
     */
    MutableRequestSpec removeRouteParam(String name);

    /**
     * Replace all route parameters with specified name
     *
     * @param name     The route parameter name
     * @param newValue New route parameter value
     * @return MutableRequestSpec
     */
    MutableRequestSpec replaceRouteParam(String name, Object newValue);

    /**
     * @return cookies with Object value
     */
    MultiKeyMap<String, Object> getCookiesWithValueToSerialize();

    /**
     * @return Processors list
     */
    List<Processor> getProcessors();

    /**
     * Remove all processors by specified class
     *
     * @param processor Processor class
     * @return MutableRequestSpec
     */
    MutableRequestSpec removeProcessor(Class<? extends Processor> processor);

    /**
     * @return Configs list
     */
    List<Config<?>> getConfigs();

    /**
     * Remove all configs by specified class
     *
     * @param config Config class
     * @return MutableRequestSpec
     */
    MutableRequestSpec removeConfig(Class<? extends Config<?>> config);

    /**
     * @return List<ResponseValidation>
     */
    List<Validation> getValidations();
}