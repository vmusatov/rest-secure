package com.restsecure.logging.config;

import com.restsecure.core.configuration.BaseConfig;
import com.restsecure.logging.LogInfo;

import java.util.ArrayList;
import java.util.List;

public class ResponseLogConfig extends BaseConfig<List<LogInfo>> {
    @Override
    public void initDefault() {
        value = new ArrayList<>();
    }
}
