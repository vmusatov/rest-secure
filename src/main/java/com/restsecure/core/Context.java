package com.restsecure.core;

import com.restsecure.core.processor.PostResponseProcessor;
import com.restsecure.core.processor.PreSendProcessor;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.ProcessScope;
import com.restsecure.core.util.MultiValueMap;
import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Context {

    public static final String PACKAGE = "com.restsecure";

    private final Reflections scanner;
    @Getter
    private final MultiValueMap<ProcessScope, PostResponseProcessor> postResponseProcessors;
    @Getter
    private final MultiValueMap<ProcessScope, PreSendProcessor> preSendProcessors;

    public Context() {
        this.scanner = new Reflections(PACKAGE);
        this.preSendProcessors = new MultiValueMap<>();
        this.postResponseProcessors = new MultiValueMap<>();

        initPreSendProcessors();
        initPostResponseProcessors();
    }

    private void initPreSendProcessors() {
        Map<PreSendProcessor, ProcessAll> processors = getProcessors(PreSendProcessor.class);

        processors.forEach((processor, processAllAnnotation) -> {
            ProcessScope scope = getPreSendScope(processAllAnnotation);
            this.preSendProcessors.put(scope, processor);
        });
    }

    private ProcessScope getPreSendScope(ProcessAll annotation) {
        ProcessScope scope = annotation.scope();
        ProcessScope preSendScope = annotation.preSendScope();

        if (scope != preSendScope && preSendScope != ProcessScope.NONE) {
            return preSendScope;
        } else {
            return scope;
        }
    }

    private void initPostResponseProcessors() {
        Map<PostResponseProcessor, ProcessAll> processors = getProcessors(PostResponseProcessor.class);

        processors.forEach((processor, processAllAnnotation) -> {
            ProcessScope scope = getPostResponseScope(processAllAnnotation);
            this.postResponseProcessors.put(scope, processor);
        });
    }

    private ProcessScope getPostResponseScope(ProcessAll annotation) {
        ProcessScope scope = annotation.scope();
        ProcessScope postResponseScope = annotation.postResponseScope();

        if (scope != postResponseScope && postResponseScope != ProcessScope.NONE) {
            return postResponseScope;
        } else {
            return scope;
        }
    }

    @SneakyThrows
    private <T> Map<T, ProcessAll> getProcessors(Class<T> processorClass) {
        Set<Class<? extends T>> preSendProcessors = scanner.getSubTypesOf(processorClass);
        Map<T, ProcessAll> processors = new HashMap<>();

        for (Class<? extends T> processor : preSendProcessors) {
            if (processor.isAnnotationPresent(ProcessAll.class)) {
                ProcessAll processAllAnnotation = processor.getAnnotation(ProcessAll.class);

                if (processAllAnnotation.disable()) {
                    continue;
                }

                T instance = processor.getDeclaredConstructor().newInstance();
                processors.put(instance, processAllAnnotation);
            }
        }

        return processors;
    }
}
