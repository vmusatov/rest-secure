package com.restsecure.logging;

import com.restsecure.core.configuration.Config;
import lombok.Getter;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogConfig implements Config {

    @Getter
    private PrintStream printStream;
    @Getter
    private List<LogInfo> requestLogInfoList;
    @Getter
    private List<LogInfo> responseLogInfoList;

    public LogConfig setPrintSteam(PrintStream printSteam) {
        this.printStream = printSteam;
        return this;
    }

    public LogConfig request(LogInfo logInfo, LogInfo... additionalLogInfo) {
        this.requestLogInfoList.add(logInfo);
        this.requestLogInfoList.addAll(Arrays.asList(additionalLogInfo));

        return this;
    }

    public LogConfig response(LogInfo logInfo, LogInfo... additionalLogInfo) {
        this.responseLogInfoList.add(logInfo);
        this.responseLogInfoList.addAll(Arrays.asList(additionalLogInfo));

        return this;
    }

    public LogConfig logAll() {
        this.requestLogInfoList.add(LogInfo.ALL);
        this.responseLogInfoList.add(LogInfo.ALL);

        return this;
    }

    @Override
    public void reset() {
        this.printStream = System.out;
        this.requestLogInfoList = new ArrayList<>();
        this.responseLogInfoList = new ArrayList<>();
    }
}
