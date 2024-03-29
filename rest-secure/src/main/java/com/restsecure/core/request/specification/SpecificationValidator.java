package com.restsecure.core.request.specification;

import com.restsecure.core.exception.RequestConfigurationException;

public class SpecificationValidator {
    public static void validate(MutableRequestSpec spec) {
        if (spec == null) {
            throw new RequestConfigurationException("Request spec is null");
        }
        if (spec.getMethod() == null) {
            throw new RequestConfigurationException("Request method is null");
        }
        if (spec.getUrl() == null || spec.getUrl().isEmpty()) {
            throw new RequestConfigurationException("Request url is null or empty");
        }
    }
}
