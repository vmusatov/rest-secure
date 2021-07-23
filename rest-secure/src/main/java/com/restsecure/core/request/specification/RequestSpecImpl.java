package com.restsecure.core.request.specification;

import com.restsecure.core.client.Client;
import com.restsecure.core.configuration.Config;
import com.restsecure.core.configuration.configs.HttpClientConfig;
import com.restsecure.core.http.*;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.util.MultiKeyMap;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
public class RequestSpecImpl implements MutableRequestSpec {

    private String url;
    private int port;
    private RequestMethod method;

    private Object body;

    private final MultiKeyMap<String, Object> headers;
    private final MultiKeyMap<String, Object> parameters;
    private final MultiKeyMap<String, Object> queryParams;
    private final MultiKeyMap<String, Object> routeParams;
    private final MultiKeyMap<String, Object> cookiesWithValueToSerialize;

    private final List<Processor> processors;
    private final List<Validation> validations;
    private final List<Config<?>> configs;

    public RequestSpecImpl() {
        this.url = "";

        this.headers = new MultiKeyMap<>();
        this.parameters = new MultiKeyMap<>();
        this.queryParams = new MultiKeyMap<>();
        this.routeParams = new MultiKeyMap<>();
        this.cookiesWithValueToSerialize = new MultiKeyMap<>();

        this.processors = new ArrayList<>();
        this.validations = new ArrayList<>();

        this.configs = new ArrayList<>();
    }

    @Override
    public RequestSpecImpl url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public RequestSpecImpl port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public RequestSpecImpl method(RequestMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public RequestSpecImpl body(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public RequestSpecImpl header(String name, Object value, Object... additionalValues) {
        this.headers.put(name, value);
        if (additionalValues != null && additionalValues.length > 0) {
            for (Object additionalValue : additionalValues) {
                this.headers.put(name, additionalValue);
            }
        }
        return this;
    }

    @Override
    public RequestSpecImpl header(Header header, Header... additionalHeaders) {
        headers.put(header.getName(), header.getValue());
        if (additionalHeaders != null && additionalHeaders.length > 0) {
            for (Header h : additionalHeaders) {
                headers.put(h.getName(), h.getValue());
            }
        }
        return this;
    }

    @Override
    public RequestSpecImpl headers(List<Header> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (Header header : headers) {
                header(header);
            }
        }
        return this;
    }

    @Override
    public RequestSpecImpl headers(Map<String, ?> headers) {
        for (Map.Entry<String, ?> header : headers.entrySet()) {
            header(header.getKey(), header.getValue());
        }
        return this;
    }

    @Override
    public MultiKeyMap<String, Object> getHeaders() {
        return this.headers;
    }

    @Override
    public RequestSpecImpl clearHeaders() {
        this.headers.clear();
        return this;
    }

    @Override
    public RequestSpecImpl removeHeader(String name) {
        this.headers.deleteAllWithKey(name);
        return this;
    }

    @Override
    public RequestSpecImpl replaceHeader(String name, Object newValue) {
        this.headers.forEach(h -> {
            if (h.getKey().equals(name)) {
                h.setValue(newValue);
            }
        });
        return this;
    }

    @Override
    public RequestSpecImpl param(String name, Object value, Object... additionalValues) {
        this.parameters.put(name, value);
        if (additionalValues != null && additionalValues.length > 0) {
            for (Object additionalValue : additionalValues) {
                this.parameters.put(name, additionalValue);
            }
        }
        return this;
    }

    @Override
    public RequestSpecImpl param(Parameter parameter) {
        param(parameter.getName(), parameter.getValue());
        return this;
    }

    @Override
    public RequestSpecImpl params(List<Parameter> parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            for (Parameter param : parameters) {
                param(param);
            }
        }
        return this;
    }

    @Override
    public RequestSpecImpl params(Map<String, ?> parameters) {
        for (Map.Entry<String, ?> param : parameters.entrySet()) {
            param(param.getKey(), param.getValue());
        }
        return this;
    }

    @Override
    public MultiKeyMap<String, Object> getParams() {
        return this.parameters;
    }

    @Override
    public RequestSpecImpl clearParams() {
        this.parameters.clear();
        return this;
    }

    @Override
    public RequestSpecImpl removeParam(String name) {
        this.parameters.deleteAllWithKey(name);
        return this;
    }

    @Override
    public RequestSpecImpl replaceParam(String name, Object newValue) {
        this.parameters.forEach(p -> {
            if (p.getKey().equals(name)) {
                p.setValue(newValue);
            }
        });
        return this;
    }

    @Override
    public RequestSpecImpl clearQueryParams() {
        this.queryParams.clear();
        return this;
    }

    @Override
    public RequestSpecImpl removeQueryParam(String name) {
        this.queryParams.deleteAllWithKey(name);
        return this;
    }

    @Override
    public RequestSpecImpl replaceQueryParam(String name, Object newValue) {
        this.queryParams.forEach(qp -> {
            if (qp.getKey().equals(name)) {
                qp.setValue(newValue);
            }
        });
        return this;
    }

    @Override
    public RequestSpecImpl queryParam(String name, Object... value) {
        if (value.length > 0) {
            for (Object val : value) {
                this.queryParams.put(name, val);
            }
        } else {
            this.queryParams.put(name, "");
        }
        return this;
    }

    @Override
    public RequestSpecImpl queryParam(Parameter param) {
        queryParam(param.getName(), param.getValue());
        return this;
    }

    @Override
    public RequestSpecImpl queryParams(List<Parameter> parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            for (Parameter param : parameters) {
                queryParam(param);
            }
        }
        return this;
    }

    @Override
    public RequestSpecImpl queryParams(Map<String, ?> parameters) {
        for (Map.Entry<String, ?> param : parameters.entrySet()) {
            queryParam(param.getKey(), param.getValue());
        }
        return this;
    }

    @Override
    public RequestSpecImpl routeParam(String name, Object value) {
        this.routeParams.deleteAllWithKey(name);
        this.routeParams.put(name, value);
        return this;
    }

    @Override
    public MultiKeyMap<String, Object> getRouteParams() {
        return this.routeParams;
    }

    @Override
    public RequestSpecImpl clearRouteParams() {
        this.routeParams.clear();
        return this;
    }

    @Override
    public RequestSpecImpl removeRouteParam(String name) {
        this.routeParams.deleteAllWithKey(name);
        return this;
    }

    @Override
    public RequestSpecImpl replaceRouteParam(String name, Object newValue) {
        this.routeParams.forEach(rp -> {
            if (rp.getKey().equals(name)) {
                rp.setValue(newValue);
            }
        });
        return this;
    }

    @Override
    public RequestSpecImpl cookie(String name, Object value, Object... additionalValues) {
        this.cookiesWithValueToSerialize.put(name, value);
        if (additionalValues != null && additionalValues.length > 0) {
            for (Object additionalValue : additionalValues) {
                this.cookiesWithValueToSerialize.put(name, additionalValue);
            }
        }
        return this;
    }

    @Override
    public RequestSpecImpl cookie(Cookie cookie) {
        header("Cookie", cookie.toString());
        return this;
    }

    @Override
    public RequestSpecImpl cookies(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            cookie(cookie);
        }
        return this;
    }

    @Override
    public RequestSpecImpl cookies(Map<String, ?> cookies) {
        for (Map.Entry<String, ?> cookie : cookies.entrySet()) {
            cookie(cookie.getKey(), cookie.getValue());
        }
        return this;
    }

    @Override
    public MultiKeyMap<String, Object> getCookiesWithValueToSerialize() {
        return this.cookiesWithValueToSerialize;
    }

    @Override
    public RequestSpecImpl removeProcessor(Class<? extends Processor> processor) {
        this.processors.removeIf(p -> p.getClass().equals(processor));
        return this;
    }

    @Override
    public RequestSpecImpl process(Processor processor, Processor... additionalProcessors) {
        this.processors.add(processor);
        this.processors.addAll(Arrays.asList(additionalProcessors));

        return this;
    }

    @Override
    public RequestSpecImpl process(List<Processor> processors) {
        this.processors.addAll(processors);
        return this;
    }

    @Override
    public RequestSpecImpl config(Config<?> config, Config<?>... additionalConfigs) {
        addConfig(config);
        for (Config<?> cfg : additionalConfigs) {
            addConfig(cfg);
        }
        return this;
    }

    @Override
    public RequestSpecImpl config(List<Config<?>> configs) {
        for (Config<?> config : configs) {
            addConfig(config);
        }
        return this;
    }

    private void addConfig(Config<?> config) {
        this.configs.removeIf(c -> c.getClass() == config.getClass());
        this.configs.add(config);
    }

    @Override
    public List<Config<?>> getConfigs() {
        return this.configs;
    }

    @Override
    public RequestSpecImpl removeConfig(Class<? extends Config<?>> config) {
        this.configs.removeIf(c -> c.getClass().equals(config));
        return this;
    }

    @Override
    public RequestSpecImpl mergeWith(RequestSpec with) {
        SpecificationMerger.merge((MutableRequestSpec) with, this);
        return this;
    }

    @Override
    public RequestSpecImpl expect(Validation validation, Validation... additionalValidation) {
        this.validations.add(validation);
        this.validations.addAll(Arrays.asList(additionalValidation));
        return this;
    }

    @Override
    public RequestSpecImpl expect(List<Validation> validations) {
        this.validations.addAll(validations);
        return this;
    }

    @Override
    public RequestSpecImpl and() {
        return this;
    }

    @Override
    public Response send() {
        RequestContext context = new RequestContext(this);
        Client client = context.getConfigValue(HttpClientConfig.class);
        return client.send(context);
    }
}
