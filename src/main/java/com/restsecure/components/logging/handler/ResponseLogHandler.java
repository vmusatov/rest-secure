package com.restsecure.components.logging.handler;

import com.restsecure.components.configuration.ConfigFactory;
import com.restsecure.components.logging.LogConfig;
import com.restsecure.components.logging.LogLevel;
import com.restsecure.components.logging.logger.ResponseLogger;
import com.restsecure.request.specification.RequestSpecification;
import com.restsecure.response.Response;
import com.restsecure.response.handler.ResponseHandler;

public class ResponseLogHandler implements ResponseHandler {

    @Override
    public void handleResponse(Response response, RequestSpecification spec) {
        LogConfig logConfig = ConfigFactory.getConfigOrCreateDefault(spec.getConfigs(), LogConfig.class);
        LogLevel logLevel = logConfig.getLogLevel();

        if (logLevel.equals(LogLevel.RESPONSE) || logLevel.equals(LogLevel.ALL)) {
            ResponseLogger.log(logConfig.getPrintStream(), response, spec, logConfig.getLogInfoList());
        }
    }
}
