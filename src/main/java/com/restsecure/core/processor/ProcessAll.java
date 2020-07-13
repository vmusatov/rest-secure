package com.restsecure.core.processor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.restsecure.core.processor.ProcessScope.AFTER_ALL;
import static com.restsecure.core.processor.ProcessScope.NONE;

@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessAll {
    ProcessScope scope() default AFTER_ALL;
    ProcessScope preSendScope() default NONE;
    ProcessScope postResponseScope() default NONE;
    boolean disable() default false;
}
