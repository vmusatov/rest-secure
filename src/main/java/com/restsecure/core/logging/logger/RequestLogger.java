package com.restsecure.core.logging.logger;

import com.restsecure.core.logging.LogInfo;
import com.restsecure.core.http.Header;
import com.restsecure.core.http.Parameter;
import com.restsecure.core.request.specification.RequestSpecification;

import java.io.PrintStream;
import java.util.List;

import static com.restsecure.core.logging.logger.LogHelper.*;

public class RequestLogger {

    public static void log(PrintStream printStream, RequestSpecification spec, List<LogInfo> logInfoList) {
        final StringBuilder builder = new StringBuilder();

        builder.append("----------------------------------------")
                .append(lineSeparator());

        if (logInfoList.contains(LogInfo.TIME)) {
            addTime(builder);
        }

        if (needLogAll(logInfoList)) {
            addAll(builder, spec);
        } else {
            for (LogInfo logInfo : logInfoList) {
                switch (logInfo) {
                    case ALL:
                        addAll(builder, spec);
                        break;
                    case URL:
                        addUrl(builder, spec);
                        break;
                    case METHOD:
                        addMethod(builder, spec);
                        break;
                    case PARAMS:
                        addParams(builder, spec);
                        break;
                    case HEADERS:
                        addHeaders(builder, spec);
                        break;
                }
            }
        }

        builder.append("----------------------------------------");
        builder.append(lineSeparator());
        printStream.println(builder.toString());
    }

    private static void addAll(StringBuilder builder, RequestSpecification spec) {
        addUrl(builder, spec);
        addMethod(builder, spec);
        addParams(builder, spec);
        addHeaders(builder, spec);
    }

    private static void addUrl(StringBuilder builder, RequestSpecification spec) {
        builder.append("Request url: ")
                .append(spec.getUrl())
                .append(lineSeparator());
    }

    private static void addMethod(StringBuilder builder, RequestSpecification spec) {
        builder.append("Request method: ")
                .append(spec.getMethod())
                .append(lineSeparator());
    }

    private static void addParams(StringBuilder builder, RequestSpecification spec) {
        builder.append("Request parameters: ");

        if (spec.getParameters().isEmpty()) {
            builder.append(NONE).append(lineSeparator());
            return;
        }

        builder.append(lineSeparator());

        for (Parameter parameter : spec.getParameters()) {
            addNameAndValue(builder, parameter);
        }
    }

    private static void addHeaders(StringBuilder builder, RequestSpecification spec) {
        builder.append("Request headers: ");

        if (spec.getHeaders().isEmpty()) {
            builder.append(NONE).append(lineSeparator());
            return;
        }

        builder.append(lineSeparator());

        for (Header header : spec.getHeaders()) {
            addNameAndValue(builder, header);
        }
    }
}
