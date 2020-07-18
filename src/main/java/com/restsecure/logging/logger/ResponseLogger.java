package com.restsecure.logging.logger;

import com.restsecure.logging.LogInfo;
import com.restsecure.core.http.cookie.Cookie;
import com.restsecure.core.http.header.Header;
import com.restsecure.core.request.specification.RequestSpecification;
import com.restsecure.core.response.Response;

import java.io.PrintStream;
import java.util.List;

import static com.restsecure.logging.logger.LogHelper.*;

public class ResponseLogger {

    public static void log(PrintStream printStream, Response response, RequestSpecification spec, List<LogInfo> logInfoList) {
        final StringBuilder builder = new StringBuilder();

        builder.append("----------------------------------------")
                .append(lineSeparator());

        if (logInfoList.contains(LogInfo.TIME) || logInfoList.contains(LogInfo.ALL)) {
            addTime(builder);
        }

        builder.append("Response from ")
                .append(spec.getMethod())
                .append(" ")
                .append(spec.getUrl())
                .append(lineSeparator());

        if (needLogAll(logInfoList)) {
            addAll(builder, response);
        } else {
            for (LogInfo logInfo : logInfoList) {
                switch (logInfo) {
                    case STATUS_CODE:
                        addStatusCode(builder, response);
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
        printStream.println(builder.toString());
    }

    private static void addAll(StringBuilder builder, Response response) {
        addStatusCode(builder, response);
        addHeaders(builder, response);
        addCookies(builder, response);
    }

    private static void addStatusCode(StringBuilder builder, Response response) {
        builder.append("Response status code: ")
                .append(response.getStatusCode())
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
