package com.restsecure.components.logging.handler;

import com.restsecure.components.configuration.ConfigFactory;
import com.restsecure.components.logging.LogConfig;
import com.restsecure.components.logging.LogLevel;
import com.restsecure.components.logging.logger.RequestLogger;
import com.restsecure.request.handler.RequestHandler;
import com.restsecure.request.specification.RequestSpecification;

public class RequestLogHandler implements RequestHandler {

    @Override
    public void handleRequest(RequestSpecification spec) {
        LogConfig logConfig = ConfigFactory.getConfigOrCreateDefault(spec.getConfigs(), LogConfig.class);
        LogLevel logLevel = logConfig.getLogLevel();

        if (logLevel.equals(LogLevel.REQUEST) || logLevel.equals(LogLevel.ALL)) {
            RequestLogger.log(logConfig.getPrintStream(), spec, logConfig.getLogInfoList());
        }
    }
}
