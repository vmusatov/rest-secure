package com.restsecure.core.request.specification;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.http.RequestMethod;
import com.restsecure.core.http.Cookie;
import com.restsecure.core.http.Header;
import com.restsecure.core.http.Parameter;
import com.restsecure.core.http.Proxy;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestSender;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;
import com.restsecure.core.util.MultiKeyMap;
import lombok.Getter;

import java.util.*;

@Getter
public class RequestSpecImpl implements RequestSpec {

    private String url;
    private int port;
    private RequestMethod method;

    private Object body;

    private final MultiKeyMap<String, Object> headers;
    private final MultiKeyMap<String, Object> parameters;
    private final MultiKeyMap<String, Object> queryParams;
    private final MultiKeyMap<String, Object> routeParams;
    private final MultiKeyMap<String, Object> cookiesWithValueToSerialize;

    private Proxy proxy;

    private final List<Processor> processors;
    private final List<Validation> validations;
    private final List<Config<?>> configs;
    private final List<Object> data;

    public RequestSpecImpl() {
        this.url = "";

        this.headers = new MultiKeyMap<>();
        this.parameters = new MultiKeyMap<>();
        this.queryParams = new MultiKeyMap<>();
        this.routeParams = new MultiKeyMap<>();
        this.cookiesWithValueToSerialize = new MultiKeyMap<>();

        this.proxy = null;

        this.processors = new ArrayList<>();
        this.validations = new ArrayList<>();

        this.configs = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    @Override
    public RequestSpecImpl url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public RequestSpec port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public RequestSpec method(RequestMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public RequestSpec body(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public RequestSpec header(String name, Object value, Object... additionalValues) {
        this.headers.put(name, value);
        if (additionalValues != null && additionalValues.length > 0) {
            for (Object additionalValue : additionalValues) {
                this.headers.put(name, additionalValue);
            }
        }
        return this;
    }

    @Override
    public RequestSpec header(Header header, Header... additionalHeaders) {
        headers.put(header.getName(), header.getValue());
        if (additionalHeaders != null && additionalHeaders.length > 0) {
            for (Header h : additionalHeaders) {
                headers.put(h.getName(), h.getValue());
            }
        }
        return this;
    }

    @Override
    public RequestSpec headers(List<Header> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (Header header : headers) {
                header(header);
            }
        }
        return this;
    }

    @Override
    public RequestSpec headers(Map<String, ?> headers) {
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
    public RequestSpec param(String name, Object value, Object... additionalValues) {
        this.parameters.put(name, value);
        if (additionalValues != null && additionalValues.length > 0) {
            for (Object additionalValue : additionalValues) {
                this.parameters.put(name, additionalValue);
            }
        }
        return this;
    }

    @Override
    public RequestSpec param(Parameter parameter) {
        param(parameter.getName(), parameter.getValue());
        return this;
    }

    @Override
    public RequestSpec params(List<Parameter> parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            for (Parameter param : parameters) {
                param(param);
            }
        }
        return this;
    }

    @Override
    public RequestSpec params(Map<String, ?> parameters) {
        for (Map.Entry<String, ?> param : parameters.entrySet()) {
            param(param.getKey(), param.getValue());
        }
        return this;
    }

    @Override
    public MultiKeyMap<String, Object> getParameters() {
        return this.parameters;
    }

    @Override
    public RequestSpec queryParam(String name, Object... value) {
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
    public RequestSpec queryParam(Parameter param) {
        queryParam(param.getName(), param.getValue());
        return this;
    }

    @Override
    public RequestSpec queryParams(List<Parameter> parameters) {
        if (parameters != null && !parameters.isEmpty()) {
            for (Parameter param : parameters) {
                queryParam(param);
            }
        }
        return this;
    }

    @Override
    public RequestSpec queryParams(Map<String, ?> parameters) {
        for (Map.Entry<String, ?> param : parameters.entrySet()) {
            queryParam(param.getKey(), param.getValue());
        }
        return this;
    }

    @Override
    public RequestSpec routeParam(String name, Object value) {
        this.routeParams.deleteAllWithKey(name);
        this.routeParams.put(name, value);
        return this;
    }

    @Override
    public MultiKeyMap<String, Object> getRouteParams() {
        return this.routeParams;
    }

    @Override
    public RequestSpec cookie(String name, Object value, Object... additionalValues) {
        this.cookiesWithValueToSerialize.put(name, value);
        if (additionalValues != null && additionalValues.length > 0) {
            for (Object additionalValue : additionalValues) {
                this.cookiesWithValueToSerialize.put(name, additionalValue);
            }
        }
        return this;
    }

    @Override
    public RequestSpec cookie(Cookie cookie) {
        header("Cookie", cookie.toString());
        return this;
    }

    @Override
    public RequestSpec cookies(List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            cookie(cookie);
        }
        return this;
    }

    @Override
    public RequestSpec cookies(Map<String, ?> cookies) {
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
    public RequestSpec proxy(String host, int port) {
        this.proxy = new Proxy(host, port);
        return this;
    }

    @Override
    public RequestSpec proxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    @Override
    public RequestSpec proxy(String host, int port, String username, String password) {
        this.proxy = new Proxy(host, port, username, password);
        return this;
    }

    @Override
    public RequestSpec process(Processor processor, Processor... additionalProcessors) {
        this.processors.add(processor);
        this.processors.addAll(Arrays.asList(additionalProcessors));

        return this;
    }

    @Override
    public RequestSpec process(List<Processor> processors) {
        this.processors.addAll(processors);
        return this;
    }

    @Override
    public RequestSpec data(Object data, Object... additionalData) {
        this.data.add(data);
        if (additionalData != null && additionalData.length > 0) {
            this.data.addAll(Arrays.asList(additionalData));
        }
        return this;
    }

    @Override
    public RequestSpec data(List<Object> data) {
        this.data.addAll(data);
        return this;
    }

    @Override
    public RequestSpec config(Config<?> config, Config<?>... additionalConfigs) {
        addConfig(config);
        for (Config<?> cfg : additionalConfigs) {
            addConfig(cfg);
        }
        return this;
    }

    @Override
    public RequestSpec config(List<Config<?>> configs) {
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
    public RequestSpec mergeWith(RequestSpec with) {
        SpecificationMerger.merge(with, this);
        return this;
    }

    @Override
    public RequestSpec expect(Validation validation, Validation... additionalValidation) {
        this.validations.add(validation);
        this.validations.addAll(Arrays.asList(additionalValidation));
        return this;
    }

    @Override
    public RequestSpec expect(List<Validation> validations) {
        this.validations.addAll(validations);
        return this;
    }

    @Override
    public List<Validation> getValidations() {
        return this.validations;
    }

    @Override
    public RequestSpec and() {
        return this;
    }

    @Override
    public Response send() {
        return RequestSender.send(this);
    }
}
