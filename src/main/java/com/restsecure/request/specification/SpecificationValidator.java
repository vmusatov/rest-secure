package com.restsecure.request.specification;

import com.restsecure.request.exception.RequestConfigurationException;

public class SpecificationValidator {
    public static void validate(RequestSpecification spec) {
        if (spec == null) {
            throw new RequestConfigurationException("Request specification is null");
        }
        if (spec.getMethod() == null) {
            throw new RequestConfigurationException("Request method is null");
        }
        if (spec.getUrl() == null || spec.getUrl().isEmpty()) {
            throw new RequestConfigurationException("Request url is null or empty");
        }
    }
}
