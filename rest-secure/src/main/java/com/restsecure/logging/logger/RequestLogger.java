package com.restsecure.logging.logger;

import com.restsecure.core.http.Header;
import com.restsecure.core.http.Parameter;
import com.restsecure.core.request.specification.MutableRequestSpec;
import com.restsecure.logging.LogInfo;
import com.restsecure.logging.LogWriter;

import java.util.List;

import static com.restsecure.logging.logger.LogHelper.*;

public class RequestLogger {

    public static void log(LogWriter writer, MutableRequestSpec spec, List<LogInfo> logInfoList) {
        final StringBuilder builder = new StringBuilder();

        builder.append("----------------------------------------")
                .append(lineSeparator());

        if (logInfoList.contains(LogInfo.TIME) || logInfoList.contains(LogInfo.ALL)) {
            addTime(builder);
        }

        if (needLogAll(logInfoList)) {
            addAll(builder, spec);
        } else {
            for (LogInfo logInfo : logInfoList) {
                switch (logInfo) {
                    case URL:
                        addUrl(builder, spec);
                        break;
                    case METHOD:
                        addMethod(builder, spec);
                        break;
                    case BODY:
                        addBody(builder, spec);
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
        writer.writeLog(builder.toString());
    }

    private static void addAll(StringBuilder builder, MutableRequestSpec spec) {
        addUrl(builder, spec);
        addMethod(builder, spec);
        addBody(builder, spec);
        addParams(builder, spec);
        addHeaders(builder, spec);
    }

    private static void addUrl(StringBuilder builder, MutableRequestSpec spec) {
        builder.append("Url: ")
                .append(spec.getUrl())
                .append(lineSeparator());
    }

    private static void addMethod(StringBuilder builder, MutableRequestSpec spec) {
        builder.append("Method: ")
                .append(spec.getMethod())
                .append(lineSeparator());
    }

    private static void addBody(StringBuilder builder, MutableRequestSpec spec) {
        builder.append("Request body: ");

        if (spec.getBody() == null) {
            builder.append(NONE).append(lineSeparator());
            return;
        }

        builder.append(lineSeparator())
                .append(tabs(1))
                .append(spec.getBody().toString())
                .append(lineSeparator());
    }

    private static void addParams(StringBuilder builder, MutableRequestSpec spec) {
        builder.append("Request parameters: ");

        if (spec.getParams().isEmpty()) {
            builder.append(NONE).append(lineSeparator());
            return;
        }

        builder.append(lineSeparator());

        spec.getParams().forEach(param -> addNameAndValue(builder, new Parameter(param.getKey(), param.getValue().toString())));
    }

    private static void addHeaders(StringBuilder builder, MutableRequestSpec spec) {
        builder.append("Request headers: ");

        if (spec.getHeaders().isEmpty()) {
            builder.append(NONE).append(lineSeparator());
            return;
        }

        builder.append(lineSeparator());

        spec.getHeaders().forEach(header -> {
            addNameAndValue(builder, new Header(header.getKey(), header.getValue().toString()));
        });
    }
}
