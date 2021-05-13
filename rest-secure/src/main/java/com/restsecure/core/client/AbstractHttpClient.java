package com.restsecure.core.client;

import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.RequestFactory;
import com.restsecure.core.request.specification.SpecificationValidator;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.ResponseTransformer;
import com.restsecure.core.response.validation.Validation;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractHttpClient<RequestType, ResponseType> implements Client {

    private RequestFactory<RequestType> requestFactory;
    private ResponseTransformer<ResponseType> responseTransformer;

    public AbstractHttpClient(RequestFactory<RequestType> requestFactory, ResponseTransformer<ResponseType> responseTransformer) {
        this.requestFactory = requestFactory;
        this.responseTransformer = responseTransformer;
    }

    protected abstract ResponseType doRequest(RequestType request);

    @Override
    public abstract void close();

    @Override
    public Response send(RequestContext context) {
        processRequest(context);
        SpecificationValidator.validate(context.getRequestSpec());

        RequestType request = requestFactory.createRequest(context);
        context.setRequestTime(System.currentTimeMillis());

        ResponseType r = doRequest(request);
        Response response = responseTransformer.transform(r, context);

        precessResponse(context, response);
        validateResponse(context, response);

        return response;
    }

    private void processRequest(RequestContext context) {
        List<Processor> processors = context.getProcessors();

        processors.stream()
                .sorted(Comparator.comparingInt(Processor::getRequestProcessOrder))
                .forEach(processor -> processor.processRequest(context));
    }

    private static void precessResponse(RequestContext context, Response response) {
        List<Processor> processors = context.getProcessors();

        processors.stream()
                .sorted(Comparator.comparingInt(Processor::getResponseProcessOrder))
                .forEach(processor -> processor.processResponse(context, response));
    }

    private static void validateResponse(RequestContext context, Response response) {
        List<Validation> validations = context.getRequestSpec().getValidations();

        if (validations == null) {
            return;
        }

        validations.forEach(validation -> validation.validate(context, response));
    }
}
