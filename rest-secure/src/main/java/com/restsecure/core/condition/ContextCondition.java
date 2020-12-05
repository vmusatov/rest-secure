package com.restsecure.core.condition;

import com.restsecure.core.request.RequestContext;

@FunctionalInterface
public interface ContextCondition {
    boolean isTrue(RequestContext context);
}
