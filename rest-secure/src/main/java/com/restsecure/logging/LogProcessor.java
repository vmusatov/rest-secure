package com.restsecure.logging;

import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.MutableResponse;
import com.restsecure.logging.config.LogWriterConfig;
import com.restsecure.logging.config.RequestLogConfig;
import com.restsecure.logging.config.ResponseLogConfig;
import com.restsecure.logging.logger.RequestLogger;
import com.restsecure.logging.logger.ResponseLogger;

import java.util.List;

@ProcessAll
public class LogProcessor implements Processor {

    @Override
    public void processResponse(RequestContext context, MutableResponse response) {
        LogWriter writer = context.getConfigValue(LogWriterConfig.class);
        List<LogInfo> logInfo = context.getConfigValue(ResponseLogConfig.class);

        if (!logInfo.isEmpty()) {
            ResponseLogger.log(writer, response, context.getRequestSpec(), logInfo);
        }
    }

    @Override
    public void processRequest(RequestContext context) {
        LogWriter writer = context.getConfigValue(LogWriterConfig.class);
        List<LogInfo> logInfo = context.getConfigValue(RequestLogConfig.class);

        if (!logInfo.isEmpty()) {
            RequestLogger.log(writer, context.getRequestSpec(), logInfo);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER;
    }

    @Override
    public int getResponseProcessOrder() {
        return Processor.MAX_ORDER;
    }
}
