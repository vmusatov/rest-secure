package com.restsecure.response.validation.conditional;

@FunctionalInterface
public interface Condition {
    boolean isTrue();
}
