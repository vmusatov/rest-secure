package com.restsecure.core;

import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Context {

    public static final String PACKAGE = "com.restsecure";

    private final Reflections scanner;
    @Getter
    private final List<Processor> processors;

    public Context() {
        this.scanner = new Reflections(PACKAGE);
        this.processors = new ArrayList<>();

        scanProcessors();
    }

    @SneakyThrows
    private void scanProcessors() {
        Set<Class<? extends Processor>> preSendProcessors = scanner.getSubTypesOf(Processor.class);

        for (Class<? extends Processor> processor : preSendProcessors) {
            if (processor.isAnnotationPresent(ProcessAll.class)) {
                ProcessAll processAllAnnotation = processor.getAnnotation(ProcessAll.class);

                if (processAllAnnotation.disable()) {
                    continue;
                }

                Processor instance = processor.getDeclaredConstructor().newInstance();
                this.processors.add(instance);
            }
        }
    }
}
