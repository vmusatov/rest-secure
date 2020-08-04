package com.restsecure.logging;

import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.response.Response;
import com.restsecure.logging.logger.RequestLogger;
import com.restsecure.logging.logger.ResponseLogger;

@ProcessAll
public class LogProcessor implements Processor {

    @Override
    public void processResponse(RequestContext context, Response response) {
        LogConfig logConfig = context.getConfig(LogConfig.class);

        if (!logConfig.getRequestLogInfoList().isEmpty()) {
            ResponseLogger.log(logConfig.getPrintStream(), response, context.getSpecification(), logConfig.getResponseLogInfoList());
        }
    }

    @Override
    public void processRequest(RequestContext context) {
        LogConfig logConfig = context.getConfig(LogConfig.class);

        if (!logConfig.getResponseLogInfoList().isEmpty()) {
            RequestLogger.log(logConfig.getPrintStream(), context.getSpecification(), logConfig.getRequestLogInfoList());
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
