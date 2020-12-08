package com.restsecure.logging.config;

import com.restsecure.core.configuration.BaseConfig;
import com.restsecure.logging.LogWriter;

public class LogWriterConfig extends BaseConfig<LogWriter> {
    @Override
    public void initDefault() {
        value = System.out::println;
    }
}
