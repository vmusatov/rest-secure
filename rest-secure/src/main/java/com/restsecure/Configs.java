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
     * Create a OverwriteAllHeadersConfig with specified value
     *
     * @param isOverwrite is overwrite all headers
     * @return OverwriteAllHeadersConfig
     */
    public static OverwriteAllHeadersConfig overwriteAllHeaders(boolean isOverwrite) {
        OverwriteAllHeadersConfig config = new OverwriteAllHeadersConfig();
        config.setValue(isOverwrite);
        return config;
    }

    /**
     * Create a OverwriteHeadersConfig with specified value
     *
     * @param headersToOverwrite headers to overwrite
     * @return OverwriteHeadersConfig
     */
    public static OverwriteHeadersConfig overwriteHeaders(List<String> headersToOverwrite) {
        OverwriteHeadersConfig config = new OverwriteHeadersConfig();
        config.setValue(headersToOverwrite);
        return config;
    }

    /**
     * Create a OverwriteHeadersConfig with specified value
     *
     * @param headersToOverwrite headers to overwrite
     * @return OverwriteHeadersConfig
     */
    public static OverwriteHeadersConfig overwriteHeaders(String... headersToOverwrite) {
        OverwriteHeadersConfig config = new OverwriteHeadersConfig();
        config.setValue(Arrays.asList(headersToOverwrite));
        return config;
    }

    /**
     * Create a OverwriteAllParamsConfig with specified value
     *
     * @param isOverwrite is overwrite all params
     * @return OverwriteAllParamsConfig
     */
    public static OverwriteAllParamsConfig overwriteAllParams(boolean isOverwrite) {
        OverwriteAllParamsConfig config = new OverwriteAllParamsConfig();
        config.setValue(isOverwrite);
        return config;
    }

    /**
     * Create a OverwriteParamsConfig with specified value
     *
     * @param paramsToOverwrite params to overwrite
     * @return OverwriteParamsConfig
     */
    public static OverwriteParamsConfig overwriteParams(List<String> paramsToOverwrite) {
        OverwriteParamsConfig config = new OverwriteParamsConfig();
        config.setValue(paramsToOverwrite);
        return config;
    }

    /**
     * Create a OverwriteParamsConfig with specified value
     *
     * @param paramsToOverwrite params to overwrite
     * @return OverwriteParamsConfig
     */
    public static OverwriteParamsConfig overwriteParams(String... paramsToOverwrite) {
        OverwriteParamsConfig config = new OverwriteParamsConfig();
        config.setValue(Arrays.asList(paramsToOverwrite));
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
