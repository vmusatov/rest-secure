package com.restsecure.core.logging.logger;

import com.restsecure.core.logging.LogConfig;
import com.restsecure.core.logging.LogLevel;
import com.restsecure.core.request.RequestContext;

public class Logger {

    public static void logRequest(RequestContext context) {
        LogConfig logConfig = context.getConfig(LogConfig.class);
        LogLevel logLevel = logConfig.getLogLevel();

        if (logLevel.equals(LogLevel.REQUEST) || logLevel.equals(LogLevel.ALL)) {
            RequestLogger.log(logConfig.getPrintStream(), context.getSpecification(), logConfig.getLogInfoList());
        }
    }

    public static void logResponse(RequestContext context) {
        LogConfig logConfig = context.getConfig(LogConfig.class);
        LogLevel logLevel = logConfig.getLogLevel();

        if (logLevel.equals(LogLevel.RESPONSE) || logLevel.equals(LogLevel.ALL)) {
            ResponseLogger.log(logConfig.getPrintStream(), context.getResponse(), context.getSpecification(), logConfig.getLogInfoList());
        }
    }
}
