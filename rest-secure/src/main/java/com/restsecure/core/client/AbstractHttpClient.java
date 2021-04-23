package com.restsecure.core.client;

import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.request.specification.SpecificationValidator;
import com.restsecure.core.response.Response;
import com.restsecure.core.response.validation.Validation;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractHttpClient implements Client {

    protected abstract Response doRequest(RequestContext context);

    @Override
    public abstract void close();

    @Override
    public Response send(RequestContext context) {
        processRequest(context);
        SpecificationValidator.validate(context.getRequestSpec());

        context.setRequestTime(System.currentTimeMillis());
        Response response = doRequest(context);

        precessResponse(response);
        validateResponse(response);

        return response;
    }

    private void processRequest(RequestContext context) {
        List<Processor> processors = context.getProcessors();

        processors.stream()
                .sorted(Comparator.comparingInt(Processor::getRequestProcessOrder))
                .forEach(processor -> processor.processRequest(context));
    }

    private static void precessResponse(Response response) {
        List<Processor> processors = response.getContext().getProcessors();

        processors.stream()
                .sorted(Comparator.comparingInt(Processor::getResponseProcessOrder))
                .forEach(processor -> processor.processResponse(response));
    }

    private static void validateResponse(Response response) {
        RequestContext context = response.getContext();
        List<Validation> validations = context.getRequestSpec().getValidations();

        if (validations == null) {
            return;
        }

        validations.forEach(validation -> validation.validate(response));
    }
}
