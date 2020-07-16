package com.restsecure.core.request.specification;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.http.param.Parameter;
import com.restsecure.core.processor.BiProcessor;
import com.restsecure.core.processor.PostResponseProcessor;
import com.restsecure.core.processor.PostResponseValidationProcessor;
import com.restsecure.core.processor.PreSendProcessor;
import com.restsecure.core.request.RequestSender;
import com.restsecure.core.response.Response;
import com.restsecure.core.util.MultiKeyMap;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class RequestSpecificationImpl implements RequestSpecification {

    private String url;
    private int port;
    private RequestMethod method;
    private Object data;

    private final MultiKeyMap<String, Object> headers;
    private final MultiKeyMap<String, Object> parameters;

    private final List<PreSendProcessor> preSendProcessors;
    private final List<PostResponseProcessor> postResponseProcessors;

    private final List<PostResponseValidationProcessor> validationProcessors;

    private final List<Config> configs;

    public RequestSpecificationImpl() {
        this.url = "";
        this.data = null;

        this.headers = new MultiKeyMap<>();
        this.parameters = new MultiKeyMap<>();

        this.preSendProcessors = new ArrayList<>();
        this.postResponseProcessors = new ArrayList<>();
        this.validationProcessors = new ArrayList<>();

        this.configs = new ArrayList<>();
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
    public RequestSpecification header(String name, Object value, Object... additionalValues) {
        this.headers.put(name, value);
        if (additionalValues != null && additionalValues.length > 0) {
            for (Object additionalValue : additionalValues) {
                this.headers.put(name, additionalValue);
            }
        }

        return this;
    }

    @Override
    public RequestSpecification header(Header header) {
        headers.put(header.getName(), header.getValue());
        return this;
    }

    @Override
    public RequestSpecification headers(List<Header> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (Header header : headers) {
                header(header);
            }
        }
        return this;
    }

    @Override
    public MultiKeyMap<String, Object> getHeaders() {
        return this.headers;
    }

    @Override
    public RequestSpecification param(String name, Object value, Object... additionalValues) {
        this.parameters.put(name, value);
        if (additionalValues != null && additionalValues.length > 0) {
            for (Object additionalValue : additionalValues) {
                this.parameters.put(name, additionalValue);
            }
        }

        return this;
    }

    @Override
    public RequestSpecification param(Parameter parameter) {
        param(parameter.getName(), parameter.getValue());
        return this;
    }

    @Override
    public RequestSpecification params(List<Parameter> parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            for (Parameter param : parameters) {
                param(param);
            }
        }
        return this;
    }

    @Override
    public MultiKeyMap<String, Object> getParameters() {
        return this.parameters;
    }

    @Override
    public RequestSpecification processRequest(PreSendProcessor processor, PreSendProcessor... additionalProcessors) {
        preSendProcessors.add(processor);
        preSendProcessors.addAll(Arrays.asList(additionalProcessors));

        return this;
    }

    public RequestSpecification processRequest(List<PreSendProcessor> handlers) {
        preSendProcessors.addAll(handlers);
        return this;
    }

    @Override
    public List<PreSendProcessor> getPreSendProcessors() {
        return this.preSendProcessors;
    }

    @Override
    public RequestSpecification processResponse(PostResponseProcessor processor, PostResponseProcessor... additionalProcessors) {
        this.postResponseProcessors.add(processor);
        this.postResponseProcessors.addAll(Arrays.asList(additionalProcessors));
        return this;
    }

    @Override
    public RequestSpecification processResponse(List<PostResponseProcessor> processors) {
        this.postResponseProcessors.addAll(processors);
        return this;
    }


    @Override
    public List<PostResponseProcessor> getPostResponseProcessors() {
        return this.postResponseProcessors;
    }

    @Override
    public RequestSpecification process(BiProcessor processor, BiProcessor... additionalProcessors) {
        processRequest(processor, additionalProcessors);
        processResponse(processor, additionalProcessors);

        return this;
    }

    @Override
    public RequestSpecification process(List<BiProcessor> processors) {
        processors.forEach(processor -> {
            processRequest(processor);
            processResponse(processor);
        });

        return this;
    }

    @Override
    public RequestSpecification data(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public RequestSpecification config(Config config, Config... additionalConfigs) {
        this.configs.add(config);
        this.configs.addAll(Arrays.asList(additionalConfigs));
        return this;
    }

    @Override
    public RequestSpecification config(List<Config> configs) {
        this.configs.addAll(configs);
        return this;
    }

    @Override
    public List<Config> getConfigs() {
        return this.configs;
    }

    @Override
    public RequestSpecification mergeWith(RequestSpecification with) {
        SpecificationMerger.merge(with, this);
        return this;
    }

    @Override
    public RequestSpecification validate(PostResponseValidationProcessor validation, PostResponseValidationProcessor... additionalValidation) {
        this.validationProcessors.add(validation);
        this.validationProcessors.addAll(Arrays.asList(additionalValidation));
        return this;
    }

    @Override
    public RequestSpecification validate(List<PostResponseValidationProcessor> validations) {
        this.validationProcessors.addAll(validations);
        return this;
    }

    @Override
    public List<PostResponseValidationProcessor> getValidations() {
        return this.validationProcessors;
    }

    @Override
    public Response send() {
        return RequestSender.send(this);
    }
}
