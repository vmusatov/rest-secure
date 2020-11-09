package com.restsecure.logging;

import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.logging.config.LogPrintStreamConfig;
import com.restsecure.logging.config.RequestLogConfig;
import com.restsecure.logging.config.ResponseLogConfig;
import com.restsecure.logging.logger.RequestLogger;
import com.restsecure.logging.logger.ResponseLogger;

import java.io.PrintStream;
import java.util.List;

@ProcessAll
public class LogProcessor implements Processor {

    @Override
    public void processResponse(RequestContext context, Response response) {
        PrintStream ps = context.getConfigValue(LogPrintStreamConfig.class);
        List<LogInfo> logInfo = context.getConfigValue(ResponseLogConfig.class);

        if (!logInfo.isEmpty()) {
            ResponseLogger.log(ps, response, context.getSpecification(), logInfo);
        }
    }

    @Override
    public void processRequest(RequestContext context) {
        PrintStream ps = context.getConfigValue(LogPrintStreamConfig.class);
        List<LogInfo> logInfo = context.getConfigValue(RequestLogConfig.class);

        if (!logInfo.isEmpty()) {
            RequestLogger.log(ps, context.getSpecification(), logInfo);
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
