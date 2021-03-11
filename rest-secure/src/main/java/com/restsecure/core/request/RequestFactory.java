package com.restsecure.core.request;

import com.restsecure.RestSecure;
import com.restsecure.core.exception.RequestConfigurationException;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.specification.RequestSpec;
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
        RequestSpec spec = context.getRequestSpec();
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
        processors.addAll(context.getRequestSpec().getProcessors());

        processors.stream()
                .sorted(Comparator.comparingInt(Processor::getRequestProcessOrder))
                .forEach(processor -> processor.processRequest(context));
    }

    private static HttpUriRequest createPost(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpPost request = new HttpPost(uri);
        setHeadersToRequest(spec.getHeaders(), request);
        setEntityToRequest(spec, request);

        return request;
    }

    private static HttpUriRequest createGet(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpGet request = new HttpGet(uri);
        setHeadersToRequest(spec.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createPut(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpPut request = new HttpPut(uri);
        setHeadersToRequest(spec.getHeaders(), request);
        setEntityToRequest(spec, request);

        return request;
    }

    private static HttpUriRequest createDelete(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpDelete request = new HttpDelete(uri);
        setHeadersToRequest(spec.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createHead(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpHead request = new HttpHead(uri);
        setHeadersToRequest(spec.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createTrace(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpTrace request = new HttpTrace(uri);
        setHeadersToRequest(spec.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createOptions(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpOptions request = new HttpOptions(uri);
        setHeadersToRequest(spec.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createPatch(RequestSpec spec) {
        URI uri = toURI(spec.getUrl());
        HttpPatch request = new HttpPatch(uri);
        setHeadersToRequest(spec.getHeaders(), request);
        setEntityToRequest(spec, request);

        return request;
    }
}
