package com.restsecure.request.specification;

import com.restsecure.configuration.Config;
import com.restsecure.configuration.ConfigFactory;
import com.restsecure.http.Header;
import com.restsecure.http.Parameter;
import com.restsecure.http.RequestMethod;
import com.restsecure.request.RequestHandler;
import com.restsecure.request.RequestSender;
import com.restsecure.request.authentication.RequestAuthHandler;
import com.restsecure.request.util.Session;
import com.restsecure.response.Response;
import com.restsecure.response.ResponseHandler;
import com.restsecure.response.validation.ResponseValidation;
import lombok.Getter;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.*;

@Getter
public class RequestSpecificationImpl implements RequestSpecification {

    private String url;
    private int port;
    private RequestMethod method;
    private Object data;
    private final List<Header> headers;
    private final List<Parameter> parameters;
    private final List<RequestHandler> requestHandlers;
    private final List<ResponseHandler> responseHandlers;
    private final List<ResponseValidation> responseValidations;
    private final List<Config> configs;
    private final HttpClientContext httpClientContext;
    private final HttpClientBuilder httpClientBuilder;

    public RequestSpecificationImpl() {
        this.url = "";
        this.data = null;
        this.headers = new ArrayList<>();
        this.parameters = new ArrayList<>();
        this.requestHandlers = new ArrayList<>();
        this.responseHandlers = new ArrayList<>();
        this.responseValidations = new ArrayList<>();
        this.configs = new ArrayList<>();
        this.httpClientContext = HttpClientContext.create();
        this.httpClientBuilder = HttpClientBuilder.create();
    }

    @Override
    public RequestSpecificationImpl url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public RequestSpecification port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public RequestSpecification method(RequestMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public RequestSpecification header(String name, String value) {
        return header(new Header(name, value));
    }

    @Override
    public RequestSpecification header(Header header) {
        headers.add(header);
        return this;
    }

    @Override
    public RequestSpecification headers(List<Header> headers) {
        this.headers.addAll(headers);
        return this;
    }

    @Override
    public RequestSpecification param(String name, String value) {
        return param(new Parameter(name, value));
    }

    @Override
    public RequestSpecification param(Parameter parameter) {
        this.parameters.add(parameter);
        return this;
    }

    @Override
    public RequestSpecification params(List<Parameter> parameters) {
        this.parameters.addAll(parameters);
        return this;
    }

    @Override
    public RequestSpecification handleRequest(RequestHandler handler, RequestHandler... additionalHandlers) {
        List<RequestHandler> handlers = new ArrayList<>();
        handlers.add(handler);
        handlers.addAll(Arrays.asList(additionalHandlers));

        return handleRequest(handlers);
    }

    @Override
    public RequestSpecification handleRequest(List<RequestHandler> handlers) {
        for (RequestHandler requestHandler : handlers) {
            if (requestHandler instanceof RequestAuthHandler) {
                requestHandlers.removeIf(item -> item instanceof RequestAuthHandler);
            }
            this.requestHandlers.add(requestHandler);
        }
        return this;
    }

    @Override
    public RequestSpecification handleResponse(ResponseHandler handler, ResponseHandler... additionalHandlers) {
        this.responseHandlers.add(handler);
        this.responseHandlers.addAll(Arrays.asList(additionalHandlers));
        return this;
    }

    @Override
    public RequestSpecification handleResponse(List<ResponseHandler> handlers) {
        this.responseHandlers.addAll(handlers);
        return this;
    }

    @Override
    public RequestSpecification session(Session session) {
        handleRequest(session);
        handleResponse(session);
        return this;
    }

    @Override
    public RequestSpecification auth(RequestAuthHandler authentication) {
        return handleRequest(authentication);
    }

    @Override
    public RequestSpecification data(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public RequestSpecification config(Config config, Config... additionalConfigs) {
        List<Config> configs = new ArrayList<>();
        configs.add(config);
        configs.addAll(Arrays.asList(additionalConfigs));

        return config(configs);
    }

    @Override
    public RequestSpecification config(List<Config> configs) {
        for(Config config : configs) {
            if(getConfig(config.getClass()) != null) {
                this.configs.removeIf(config.getClass()::isInstance);
            }

            this.configs.add(config);
        }

        return this;
    }

    @Override
    public List<Config> getConfigs() {
        return this.configs;
    }

    @Override
    public <T extends Config> T getConfig(Class<T> configClass) {
        return ConfigFactory.getConfig(this.configs, configClass);
    }

    @Override
    public HttpClientContext getContext() {
        return this.httpClientContext;
    }

    @Override
    public HttpClientBuilder getBuilder() {
        return this.httpClientBuilder;
    }

    @Override
    public RequestSpecification mergeWith(RequestSpecification with) {
        SpecificationMerger.merge(with, this);
        return this;
    }

    @Override
    public RequestSpecification validate(ResponseValidation validation) {
        this.responseValidations.add(validation);
        return this;
    }

    @Override
    public RequestSpecification validate(List<ResponseValidation> validations) {
        this.responseValidations.addAll(validations);
        return this;
    }

    @Override
    public List<ResponseValidation> getValidations() {
        return this.responseValidations;
    }

    @Override
    public Response send() {
        return RequestSender.send(this);
    }
}
