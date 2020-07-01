package com.restsecure.components.logging;

import com.restsecure.components.configuration.Config;
import lombok.Getter;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class LogConfig implements Config {

    @Getter
    private PrintStream printStream;
    @Getter
    private LogLevel logLevel;
    @Getter
    private List<LogInfo> logInfoList;

    public LogConfig setPrintSteam(PrintStream printSteam) {
        this.printStream = printSteam;
        return this;
    }

    public LogConfig setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public LogConfig addLogInfo(LogInfo logInfo) {
        if (!this.logInfoList.contains(logInfo)) {
            this.logInfoList.add(logInfo);
        }
        return this;
    }

    @Override
    public void reset() {
        this.printStream = System.out;
        this.logInfoList = new ArrayList<>();
        this.logLevel = LogLevel.NONE;
    }
}
