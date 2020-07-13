package com.restsecure.logging;

import com.restsecure.core.processor.BiProcessor;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.request.RequestContext;
import com.restsecure.logging.logger.RequestLogger;
import com.restsecure.logging.logger.ResponseLogger;

import static com.restsecure.core.processor.ProcessScope.AFTER_ALL;
import static com.restsecure.core.processor.ProcessScope.BEFORE_ALL;

@ProcessAll(preSendScope = AFTER_ALL, postResponseScope = BEFORE_ALL)
public class LogProcessor implements BiProcessor {

    @Override
    public void postResponseProcess(RequestContext context) {
        LogConfig logConfig = context.getConfig(LogConfig.class);
        LogLevel logLevel = logConfig.getLogLevel();

        if (logLevel.equals(LogLevel.RESPONSE) || logLevel.equals(LogLevel.ALL)) {
            ResponseLogger.log(logConfig.getPrintStream(), context.getResponse(), context.getSpecification(), logConfig.getLogInfoList());
        }
    }

    @Override
    public void preSendProcess(RequestContext context) {
        LogConfig logConfig = context.getConfig(LogConfig.class);
        LogLevel logLevel = logConfig.getLogLevel();

        if (logLevel.equals(LogLevel.REQUEST) || logLevel.equals(LogLevel.ALL)) {
            RequestLogger.log(logConfig.getPrintStream(), context.getSpecification(), logConfig.getLogInfoList());
        }
    }
}
