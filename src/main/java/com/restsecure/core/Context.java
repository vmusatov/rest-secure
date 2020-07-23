package com.restsecure.core;

import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import lombok.Getter;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
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

    private void scanProcessors() {
        Set<Class<? extends Processor>> processors = scanner.getSubTypesOf(Processor.class);

        for (Class<? extends Processor> processor : processors) {
            if (processor.isAnnotationPresent(ProcessAll.class)) {
                ProcessAll processAllAnnotation = processor.getAnnotation(ProcessAll.class);

                if (processAllAnnotation.disable()) {
                    continue;
                }

                Processor instance = createProcessor(processor);
                this.processors.add(instance);
            }
        }
    }

    private <T extends Processor> T createProcessor(Class<T> processor) {
        try {
            return processor.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
