package com.restsecure;

import com.restsecure.core.configuration.Config;
import com.restsecure.core.configuration.configs.*;
import com.restsecure.core.mapping.deserialize.Deserializer;
import com.restsecure.core.mapping.serialize.Serializer;
import com.restsecure.logging.LogInfo;
import com.restsecure.logging.config.LogPrintStreamConfig;
import com.restsecure.logging.config.RequestLogConfig;
import com.restsecure.logging.config.ResponseLogConfig;
import com.restsecure.session.SessionIdNameConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class Configs {

    /**
     * Create a SessionIdNameConfig with specified value
     *
     * @param name Session id name
     * @return SessionIdNameConfig
     */
    public static Config<String> sessionIdName(String name) {
        SessionIdNameConfig config = new SessionIdNameConfig();
        config.setValue(name);
        return config;
    }

    /**
     * Create a DeserializerConfig with specified value
     *
     * @param deserializer Deserializer
     * @return DeserializerConfig
     */
    public static Config<Deserializer> deserializer(Deserializer deserializer) {
        DeserializerConfig config = new DeserializerConfig();
        config.setValue(deserializer);
        return config;
    }

    /**
     * Create a SerializerConfig with specified value
     *
     * @param serializer Serializer
     * @return SerializerConfig
     */
    public static Config<Serializer> serializer(Serializer serializer) {
        SerializerConfig config = new SerializerConfig();
        config.setValue(serializer);
        return config;
    }

    /**
     * Create a HttpClientBuilderConfig with specified value
     *
     * @param builder HttpClientBuilder
     * @return HttpClientBuilderConfig
     */
    public static Config<HttpClientBuilder> httpClientBuilder(HttpClientBuilder builder) {
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
    public static Config<HttpClientContext> httpClientContext(HttpClientContext context) {
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
    public static Config<List<String>> overrideHeaders(List<String> headersToOverride) {
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
    public static Config<List<String>> overrideHeaders(String... headersToOverride) {
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
    public static Config<List<String>> overrideParams(List<String> paramsToOverride) {
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
    public static Config<List<String>> overrideParams(String... paramsToOverride) {
        OverrideParamsConfig config = new OverrideParamsConfig();
        config.setValue(Arrays.asList(paramsToOverride));
        return config;
    }

    /**
     * Create a LogPrintStreamConfig with specified value
     *
     * @param printStreams PrintStream
     * @return LogPrintStreamConfig
     */
    public static Config<PrintStream> logPrintStream(PrintStream printStreams) {
        LogPrintStreamConfig config = new LogPrintStreamConfig();
        config.setValue(printStreams);
        return config;
    }

    /**
     * Create a RequestLogConfig with specified value
     *
     * @param logInfo logInfo list
     * @return RequestLogConfig
     */
    public static Config<List<LogInfo>> requestLogInfo(List<LogInfo> logInfo) {
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
    public static Config<List<LogInfo>> requestLogInfo(LogInfo... logInfo) {
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
    public static Config<List<LogInfo>> responseLogInfo(List<LogInfo> logInfo) {
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
    public static Config<List<LogInfo>> responseLogInfo(LogInfo... logInfo) {
        ResponseLogConfig config = new ResponseLogConfig();
        config.setValue(Arrays.asList(logInfo));
        return config;
    }
}
