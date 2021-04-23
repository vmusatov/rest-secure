package com.restsecure;

import com.restsecure.core.client.Client;
import com.restsecure.core.configuration.configs.*;
import com.restsecure.core.http.Host;
import com.restsecure.core.mapping.ObjectMapper;
import com.restsecure.logging.LogInfo;
import com.restsecure.logging.LogWriter;
import com.restsecure.logging.config.LogWriterConfig;
import com.restsecure.logging.config.RequestLogConfig;
import com.restsecure.logging.config.ResponseLogConfig;
import com.restsecure.session.SessionIdNameConfig;
import com.restsecure.validation.config.BaseJsonPathConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Configs {

    /**
     * Create a HttpClientConfig with specified value
     *
     * @param client http client
     * @return HttpClientConfig
     */
    public static HttpClientConfig httpClient(Client client) {
        HttpClientConfig config = new HttpClientConfig();
        config.setValue(client);
        return config;
    }

    /**
     * Create a ProxyConfig with specified value
     *
     * @param host http host
     * @return ProxyConfig
     */
    public static ProxyConfig proxy(Host host) {
        ProxyConfig config = new ProxyConfig();
        config.setValue(Optional.of(host));
        return config;
    }

    /**
     * Create a ProxyConfig with specified value
     *
     * @param host host name
     * @param port host port
     * @return ProxyConfig
     */
    public static ProxyConfig proxy(String host, int port) {
        ProxyConfig config = new ProxyConfig();
        config.setValue(Optional.of(new Host(host, port)));
        return config;
    }

    /**
     * Create a SocketTimeoutConfig with specified value
     *
     * @param timeout socket timeout
     * @return SocketTimeoutConfig
     */
    public static SocketTimeoutConfig socketTimeout(int timeout) {
        SocketTimeoutConfig config = new SocketTimeoutConfig();
        config.setValue(timeout);
        return config;
    }

    /**
     * Create a ConnectionTimeoutConfig with specified value
     *
     * @param timeout connection timeout
     * @return ConnectionTimeoutConfig
     */
    public static ConnectionTimeoutConfig connectionTimeout(int timeout) {
        ConnectionTimeoutConfig config = new ConnectionTimeoutConfig();
        config.setValue(timeout);
        return config;
    }

    /**
     * Create a EnableRedirectsConfig with specified value
     *
     * @param enable is enable redirects
     * @return EnableRedirectsConfig
     */
    public static EnableRedirectsConfig enableRedirects(boolean enable) {
        EnableRedirectsConfig config = new EnableRedirectsConfig();
        config.setValue(enable);
        return config;
    }

    /**
     * Create a MaxRedirectsConfig with specified value
     *
     * @param max max redirects count
     * @return MaxRedirectsConfig
     */
    public static MaxRedirectsConfig maxRedirects(int max) {
        MaxRedirectsConfig config = new MaxRedirectsConfig();
        config.setValue(max);
        return config;
    }

    /**
     * Create a CookieSpecConfig with specified value
     *
     * @param cookieSpec cookie spec
     * @return CookieSpecConfig
     */
    public static CookieSpecConfig cookieSpec(String cookieSpec) {
        CookieSpecConfig config = new CookieSpecConfig();
        config.setValue(cookieSpec);
        return config;
    }

    /**
     * Create a BaseUrlConfig with specified value
     *
     * @param baseUrl base url
     * @return BaseUrlConfig
     */
    public static BaseUrlConfig baseUrl(String baseUrl) {
        BaseUrlConfig config = new BaseUrlConfig();
        config.setValue(Optional.of(baseUrl));
        return config;
    }

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
