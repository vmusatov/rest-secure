package com.restsecure.core.request;

import com.restsecure.core.mapping.serialize.SerializeConfig;
import com.restsecure.core.request.exception.RequestConfigurationException;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.request.specification.SpecificationValidator;
import org.apache.http.client.methods.*;

import java.net.URI;

import static com.restsecure.core.http.HttpHelper.*;

public class RequestFactory {

    public static HttpUriRequest createRequest(RequestContext context) {

        RequestConfigurator.configure(context);
        SpecificationValidator.validate(context.getSpecification());

        switch (context.getSpecification().getMethod()) {
            case GET:
                return createGet(context.getSpecification());
            case DELETE:
                return createDelete(context.getSpecification());
            case PUT:
                return createPut(context);
            case POST:
                return createPost(context);
            default:
                throw new RequestConfigurationException("Unsupported request method " + context.getSpecification().getMethod());
        }
    }

    private static HttpUriRequest createPost(RequestContext context) {
        RequestSpecification spec = context.getSpecification();
        SerializeConfig serializeConfig = context.getConfig(SerializeConfig.class);

        URI uri = buildUri(spec);
        HttpPost request = new HttpPost(uri);
        setHeadersToRequest(spec.getHeaders(), request);
        setEntityToRequest(spec, serializeConfig, request);

        return request;
    }

    private static HttpUriRequest createGet(RequestSpecification specification) {
        URI uri = buildUri(specification);
        HttpGet request = new HttpGet(uri);
        setHeadersToRequest(specification.getHeaders(), request);

        return request;
    }

    private static HttpUriRequest createPut(RequestContext context) {
        RequestSpecification spec = context.getSpecification();
        SerializeConfig serializeConfig = context.getConfig(SerializeConfig.class);

        URI uri = buildUri(spec);
        HttpPut request = new HttpPut(uri);
        setHeadersToRequest(spec.getHeaders(), request);
        setEntityToRequest(spec, serializeConfig, request);

        return request;
    }

    private static HttpUriRequest createDelete(RequestSpecification specification) {
        URI uri = buildUri(specification);
        HttpDelete request = new HttpDelete(uri);
        setHeadersToRequest(specification.getHeaders(), request);

        return request;
    }
}
