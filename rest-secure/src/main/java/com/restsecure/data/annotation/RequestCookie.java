package com.restsecure.data.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestCookie {
    String name() default "";
    boolean enable() default true;
}
