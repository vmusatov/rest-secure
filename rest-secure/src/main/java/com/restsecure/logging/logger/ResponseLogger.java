package com.restsecure.logging.logger;

import com.restsecure.core.http.Cookie;
import com.restsecure.core.http.Header;
import com.restsecure.core.request.specification.RequestSpec;
import com.restsecure.core.response.Response;
import com.restsecure.logging.LogInfo;
import com.restsecure.logging.LogWriter;

import java.util.List;

import static com.restsecure.logging.logger.LogHelper.*;

public class ResponseLogger {

    public static void log(LogWriter writer, Response response, RequestSpec spec, List<LogInfo> logInfoList) {
        final StringBuilder builder = new StringBuilder();

        builder.append("----------------------------------------")
                .append(lineSeparator());

        if (logInfoList.contains(LogInfo.TIME) || logInfoList.contains(LogInfo.ALL)) {
            addTime(builder);
        }

        if (logInfoList.contains(LogInfo.URL) || logInfoList.contains(LogInfo.METHOD) || logInfoList.contains(LogInfo.ALL)) {
            builder.append("Response from");
            if (logInfoList.contains(LogInfo.METHOD) || logInfoList.contains(LogInfo.ALL)) {
                builder.append(" ").append(spec.getMethod());
            }
            if (logInfoList.contains(LogInfo.URL) || logInfoList.contains(LogInfo.ALL)) {
                builder.append(" ").append(spec.getUrl());
            }
            builder.append(lineSeparator());
        }

        if (needLogAll(logInfoList)) {
            addAll(builder, response);
        } else {
            for (LogInfo logInfo : logInfoList) {
                switch (logInfo) {
                    case STATUS_CODE:
                        addStatusCode(builder, response);
                        break;
                    case BODY:
                        addBody(builder, response);
                        break;
                    case HEADERS:
                        addHeaders(builder, response);
                        break;
                    case COOKIES:
                        addCookies(builder, response);
                        break;
                }
            }
        }

        builder.append("----------------------------------------");
        builder.append(lineSeparator());
        writer.writeLog(builder.toString());
    }

    private static void addAll(StringBuilder builder, Response response) {
        addStatusCode(builder, response);
        addBody(builder, response);
        addHeaders(builder, response);
        addCookies(builder, response);
    }

    private static void addUrl(StringBuilder builder, Response response) {

    }

    private static void addStatusCode(StringBuilder builder, Response response) {
        builder.append("Response status code: ")
                .append(response.getStatusCode())
                .append(lineSeparator());
    }

    public static void addBody(StringBuilder builder, Response response) {
        builder.append("Response body: ")
                .append(lineSeparator())
                .append(tabs(1))
                .append(response.getBody().asString())
                .append(lineSeparator());
    }

    private static void addHeaders(StringBuilder builder, Response response) {
        builder.append("Response headers: ");

        if (response.getHeaders().isEmpty()) {
            builder.append(NONE).append(lineSeparator());
            return;
        }

        builder.append(lineSeparator());

        for (Header header : response.getHeaders()) {
            addNameAndValue(builder, header);
        }
    }

    private static void addCookies(StringBuilder builder, Response response) {
        builder.append("Response cookies: ");

        if (response.getCookies().isEmpty()) {
            builder.append(NONE).append(lineSeparator());
            return;
        }

        builder.append(lineSeparator());

        for (Cookie header : response.getCookies()) {
            addNameAndValue(builder, header);
        }
    }
}
