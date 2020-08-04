package com.restsecure.core.http.param;

import com.restsecure.core.configuration.Config;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParamConfig implements Config {

    @Getter
    private List<String> overrideParams;

    public ParamConfig() {
        this.overrideParams = new ArrayList<>();
    }

    public ParamConfig overrideParams(String paramName, String... additionalParamNames) {
        this.overrideParams.add(paramName);
        this.overrideParams.addAll(Arrays.asList(additionalParamNames));
        return this;
    }

    public ParamConfig overrideParams(List<String> paramNames) {
        this.overrideParams.addAll(paramNames);
        return this;
    }

    @Override
    public void reset() {
        this.overrideParams = new ArrayList<>();
    }
}
