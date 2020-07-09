package com.restsecure.core.logging.logger;

import com.restsecure.core.logging.LogInfo;
import com.restsecure.core.http.NameAndValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LogHelper {

    public static String NONE = "[none]";

    public static String tabs(int count) {
        StringBuilder tabs = new StringBuilder();

        for (int i = 0; i < count; i++) {
            tabs.append("\t");
        }

        return tabs.toString();
    }

    public static String lineSeparator() {
        return "\n";
    }

    public static void addNameAndValue(StringBuilder builder, NameAndValue nameAndValue) {
        builder.append(tabs(1))
                .append(nameAndValue.getName())
                .append(": ")
                .append(nameAndValue.getValue())
                .append(lineSeparator());
    }

    public static void addTime(StringBuilder builder) {
        builder.append(currentTime())
                .append(lineSeparator());
    }

    public static String currentTime() {
        long millis = System.currentTimeMillis();

        Date date = new Date(millis);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        return "[" + formatter.format(date) + "]";
    }

    public static boolean needLogAll(List<LogInfo> logInfoList) {
        return logInfoList.contains(LogInfo.ALL);
    }
}
