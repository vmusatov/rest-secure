package com.restsecure.logging.config;

import com.restsecure.core.configuration.BaseConfig;

import java.io.PrintStream;

public class LogPrintStreamConfig extends BaseConfig<PrintStream> {
    @Override
    public void initDefault() {
        value = System.out;
    }
}
