package com.restsecure.validation.conditional;

import com.restsecure.core.request.RequestContext;

@FunctionalInterface
public interface ContextCondition {
    boolean isTrue(RequestContext context);
}
