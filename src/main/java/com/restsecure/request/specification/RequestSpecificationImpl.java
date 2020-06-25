package com.restsecure.request.specification;

import com.restsecure.authentication.RequestAuthHandler;
import com.restsecure.configuration.Config;
import com.restsecure.configuration.ConfigsStorage;
import com.restsecure.http.*;
import com.restsecure.request.RequestSender;
import com.restsecure.request.handler.RequestHandler;
import com.restsecure.request.handler.RequestHandlersStorage;
import com.restsecure.response.Response;
import com.restsecure.response.handler.ResponseHandler;
import com.restsecure.response.handler.ResponseHandlersStorage;
import com.restsecure.response.validation.ResponseValidation;
import com.restsecure.response.validation.ResponseValidationsStorage;
import com.restsecure.session.Session;
import com.restsecure.storage.Storage;
import lombok.Getter;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.Arrays;
import java.util.List;

@Getter
public class RequestSpecificationImpl implements RequestSpecification {

    private String url;
    private int port;
    private RequestMethod method;
    private Object data;

    private final HttpClientContext httpClientContext;
    private final HttpClientBuilder httpClientBuilder;

    private final Storage<Header> headersStorage;
    private final Storage<Parameter> parametersStorage;

    private final Storage<RequestHandler> requestHandlerStorage;
    private final Storage<ResponseHandler> responseHandlerStorage;

    private final Storage<ResponseValidation> responseValidationsStorage;

    private final ConfigsStorage configsStorage;


    public RequestSpecificationImpl() {
        this.url = "";
        this.data = null;

        this.headersStorage = new HeadersStorage();
        this.parametersStorage = new ParametersStorage();

        this.httpClientContext = HttpClientContext.create();
        this.httpClientBuilder = HttpClientBuilder.create();

        this.requestHandlerStorage = new RequestHandlersStorage();
        this.responseHandlerStorage = new ResponseHandlersStorage();

        this.responseValidationsStorage = new ResponseValidationsStorage();

        this.configsStorage = new ConfigsStorage();

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
        headersStorage.update(header);
        return this;
    }

    @Override
    public RequestSpecification headers(List<Header> headers) {
        this.headersStorage.update(headers);
        return this;
    }

    @Override
    public List<Header> getHeaders() {
        return this.headersStorage.getAll();
    }

    @Override
    public RequestSpecification param(String name, String value) {
        return param(new Parameter(name, value));
    }

    @Override
    public RequestSpecification param(Parameter parameter) {
        this.parametersStorage.update(parameter);
        return this;
    }

    @Override
    public RequestSpecification params(List<Parameter> parameters) {
        this.parametersStorage.update(parameters);
        return this;
    }

    @Override
    public List<Parameter> getParameters() {
        return this.parametersStorage.getAll();
    }

    @Override
    public RequestSpecification handleRequest(RequestHandler handler, RequestHandler... additionalHandlers) {
        requestHandlerStorage.update(handler);
        requestHandlerStorage.update(Arrays.asList(additionalHandlers));

        return this;
    }

    @Override
    public RequestSpecification handleRequest(List<RequestHandler> handlers) {
        requestHandlerStorage.update(handlers);
        return this;
    }

    @Override
    public List<RequestHandler> getRequestHandlers() {
        return this.requestHandlerStorage.getAll();
    }

    @Override
    public RequestSpecification handleResponse(ResponseHandler handler, ResponseHandler... additionalHandlers) {
        this.responseHandlerStorage.update(handler);
        this.responseHandlerStorage.update(Arrays.asList(additionalHandlers));
        return this;
    }

    @Override
    public RequestSpecification handleResponse(List<ResponseHandler> handlers) {
        this.responseHandlerStorage.update(handlers);
        return this;
    }

    @Override
    public List<ResponseHandler> getResponseHandlers() {
        return this.responseHandlerStorage.getAll();
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
        this.configsStorage.update(config);
        this.configsStorage.update(Arrays.asList(additionalConfigs));
        return this;
    }

    @Override
    public RequestSpecification config(List<Config> configs) {
        this.configsStorage.update(configs);
        return this;
    }

    @Override
    public List<Config> getConfigs() {
        return this.configsStorage.getAll();
    }

    @Override
    public <T extends Config> T getConfig(Class<T> configClass) {
        return this.configsStorage.get(configClass);
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
    public RequestSpecification validate(ResponseValidation validation, ResponseValidation... additionalValidation) {
        this.responseValidationsStorage.update(validation);
        this.responseValidationsStorage.update(Arrays.asList(additionalValidation));
        return this;
    }

    @Override
    public RequestSpecification validate(List<ResponseValidation> validations) {
        this.responseValidationsStorage.update(validations);
        return this;
    }

    @Override
    public List<ResponseValidation> getValidations() {
        return this.responseValidationsStorage.getAll();
    }

    @Override
    public Response send() {
        return RequestSender.send(this);
    }
}
