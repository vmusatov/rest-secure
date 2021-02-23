package com.restsecure;

import com.restsecure.core.configuration.configs.*;
import com.restsecure.core.mapping.ObjectMapper;
import com.restsecure.logging.LogInfo;
import com.restsecure.logging.LogWriter;
import com.restsecure.logging.config.LogWriterConfig;
import com.restsecure.logging.config.RequestLogConfig;
import com.restsecure.logging.config.ResponseLogConfig;
import com.restsecure.session.SessionIdNameConfig;
import com.restsecure.validation.config.BaseJsonPathConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Configs {

    /**
     * Create a SessionIdNameConfig with specified value
     *
     * @param name Session id name
     * @return SessionIdNameConfig
     */
    public static SessionIdNameConfig sessionIdName(String name) {
        SessionIdNameConfig config = new SessionIdNameConfig();
        config.setValue(name);
        return config;
    }

    /**
     * Create a ObjectMapperConfig with specified value
     *
     * @param objectMapper object mapper
     * @return ObjectMapperConfig
     */
    public static ObjectMapperConfig objectMapper(ObjectMapper objectMapper) {
        ObjectMapperConfig config = new ObjectMapperConfig();
        config.setValue(Optional.of(objectMapper));
        return config;
    }

    /**
     * Create a HttpClientBuilderConfig with specified value
     *
     * @param builder HttpClientBuilder
     * @return HttpClientBuilderConfig
     */
    public static HttpClientBuilderConfig httpClientBuilder(HttpClientBuilder builder) {
        HttpClientBuilderConfig config = new HttpClientBuilderConfig();
        config.setValue(builder);
        return config;
    }

    /**
     * Create a HttpClientContextConfig with specified value
     *
     * @param context HttpClientContext
     * @return HttpClientContextConfig
     */
    public static HttpClientContextConfig httpClientContext(HttpClientContext context) {
        HttpClientContextConfig config = new HttpClientContextConfig();
        config.setValue(context);
        return config;
    }

    /**
     * Create a OverrideHeadersConfig with specified value
     *
     * @param headersToOverride Headers to override
     * @return OverrideHeadersConfig
     */
    public static OverrideHeadersConfig overrideHeaders(List<String> headersToOverride) {
        OverrideHeadersConfig config = new OverrideHeadersConfig();
        config.setValue(headersToOverride);
        return config;
    }

    /**
     * Create a OverrideHeadersConfig with specified value
     *
     * @param headersToOverride Headers to override
     * @return OverrideHeadersConfig
     */
    public static OverrideHeadersConfig overrideHeaders(String... headersToOverride) {
        OverrideHeadersConfig config = new OverrideHeadersConfig();
        config.setValue(Arrays.asList(headersToOverride));
        return config;
    }

    /**
     * Create a OverrideParamsConfig with specified value
     *
     * @param paramsToOverride Params to override
     * @return OverrideParamsConfig
     */
    public static OverrideParamsConfig overrideParams(List<String> paramsToOverride) {
        OverrideParamsConfig config = new OverrideParamsConfig();
        config.setValue(paramsToOverride);
        return config;
    }

    /**
     * Create a OverrideParamsConfig with specified value
     *
     * @param paramsToOverride Params to override
     * @return OverrideParamsConfig
     */
    public static OverrideParamsConfig overrideParams(String... paramsToOverride) {
        OverrideParamsConfig config = new OverrideParamsConfig();
        config.setValue(Arrays.asList(paramsToOverride));
        return config;
    }

    /**
     * Create a LogWriterConfig with specified value
     *
     * @param writer LogWriter
     * @return LogWriterConfig
     */
    public static LogWriterConfig logWriter(LogWriter writer) {
        LogWriterConfig config = new LogWriterConfig();
        config.setValue(writer);
        return config;
    }

    /**
     * Create a RequestLogConfig with specified value
     *
     * @param logInfo logInfo list
     * @return RequestLogConfig
     */
    public static RequestLogConfig requestLogInfo(List<LogInfo> logInfo) {
        RequestLogConfig config = new RequestLogConfig();
        config.setValue(logInfo);
        return config;
    }

    /**
     * Create a RequestLogConfig with specified value
     *
     * @param logInfo logInfo list
     * @return RequestLogConfig
     */
    public static RequestLogConfig requestLogInfo(LogInfo... logInfo) {
        RequestLogConfig config = new RequestLogConfig();
        config.setValue(Arrays.asList(logInfo));
        return config;
    }

    /**
     * Create a ResponseLogConfig with specified value
     *
     * @param logInfo logInfo list
     * @return ResponseLogConfig
     */
    public static ResponseLogConfig responseLogInfo(List<LogInfo> logInfo) {
        ResponseLogConfig config = new ResponseLogConfig();
        config.setValue(logInfo);
        return config;
    }

    /**
     * Create a ResponseLogConfig with specified value
     *
     * @param logInfo logInfo list
     * @return ResponseLogConfig
     */
    public static ResponseLogConfig responseLogInfo(LogInfo... logInfo) {
        ResponseLogConfig config = new ResponseLogConfig();
        config.setValue(Arrays.asList(logInfo));
        return config;
    }


    /**
     * Create a BaseJsonPathConfig with specified value
     *
     * @param path base json path
     * @return BaseJsonPathConfig
     */
    public static BaseJsonPathConfig baseJsonPath(String path) {
        BaseJsonPathConfig config = new BaseJsonPathConfig();
        config.setValue(path);
        return config;
    }
}
