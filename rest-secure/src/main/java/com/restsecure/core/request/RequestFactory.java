package com.restsecure.core.request;

import com.restsecure.RestSecure;
import com.restsecure.core.exception.RequestConfigurationException;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.SpecificationValidator;
import org.apache.http.client.methods.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.restsecure.core.http.HttpHelper.*;

public class RequestFactory {

    public static HttpUriRequest createRequest(RequestContext context) {
        processRequest(context);
        RequestSpecification spec = context.getSpecification();
        SpecificationValidator.validate(spec);

        switch (spec.getMethod()) {
            case GET:
                return createGet(spec);
            case DELETE:
                return createDelete(spec);
            case PUT:
                return createPut(spec);
            case POST:
                return createPost(spec);
            case HEAD:
                return createHead(spec);
            case TRACE:
                return createTrace(spec);
            case OPTIONS:
                return createOptions(spec);
            case PATCH:
                return createPatch(spec);
            default:
                throw new RequestConfigurationException("Unsupported request method " + spec.getMethod());
        }
    }

    private static void processRequest(RequestContext context) {
        List<Processor> processors = new ArrayList<>();
        processors.addAll(RestSecure.getContext().getProcessors());
        processors.addAll(context.getSpecification().getProcessors());

        processors.stream()
                .sorted(Comparator.comparingInt(Processor::getRequestProcessOrder))
                .forEach(processor -> processor.processRequest(context));
    }

    private static HttpUriRequest createPost(RequestSpecification specification) {
        URI uri = toURI(specification.getUrl());
        HttpPost request = new HttpPost(uri);
        setHeadersToRequest(specification.getHeaders(), request);
        setEntityToRequest(specification, request);

        return request;
    }

    private static HttpUriRequest createGet(RequestSpecification specification) {
        URI uri = toURI(specification.getUrl());
        HttpGet request = new HttpGet(uri);
        setHeadersToRequest(specification.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createPut(RequestSpecification specification) {
        URI uri = toURI(specification.getUrl());
        HttpPut request = new HttpPut(uri);
        setHeadersToRequest(specification.getHeaders(), request);
        setEntityToRequest(specification, request);

        return request;
    }

    private static HttpUriRequest createDelete(RequestSpecification specification) {
        URI uri = toURI(specification.getUrl());
        HttpDelete request = new HttpDelete(uri);
        setHeadersToRequest(specification.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createHead(RequestSpecification specification) {
        URI uri = toURI(specification.getUrl());
        HttpHead request = new HttpHead(uri);
        setHeadersToRequest(specification.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createTrace(RequestSpecification specification) {
        URI uri = toURI(specification.getUrl());
        HttpTrace request = new HttpTrace(uri);
        setHeadersToRequest(specification.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createOptions(RequestSpecification specification) {
        URI uri = toURI(specification.getUrl());
        HttpOptions request = new HttpOptions(uri);
        setHeadersToRequest(specification.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createPatch(RequestSpecification specification) {
        URI uri = toURI(specification.getUrl());
        HttpPatch request = new HttpPatch(uri);
        setHeadersToRequest(specification.getHeaders(), request);
        setEntityToRequest(specification, request);

        return request;
    }
}
