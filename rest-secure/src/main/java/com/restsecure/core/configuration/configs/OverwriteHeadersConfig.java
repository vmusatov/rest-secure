package com.restsecure.core.configuration.configs;

import com.restsecure.core.configuration.BaseConfig;

import java.util.ArrayList;
import java.util.List;

public class OverwriteHeadersConfig extends BaseConfig<List<String>> {
    @Override
    public void initDefault() {
        value = new ArrayList<>();
    }
}
