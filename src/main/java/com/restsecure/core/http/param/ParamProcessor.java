package com.restsecure.core.http.param;

import com.restsecure.core.mapping.serialize.SerializeConfig;
import com.restsecure.core.mapping.serialize.SerializeHelper;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.MultiKeyMap;

import static com.restsecure.core.http.OverrideValuesHelper.overrideValue;

@ProcessAll
public class ParamProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        ParamConfig paramConfig = context.getConfig(ParamConfig.class);
        SerializeConfig serializeConfig = context.getConfig(SerializeConfig.class);
        MultiKeyMap<String, Object> params = context.getSpecification().getParameters();

        SerializeHelper.serializeValuesIfNeed(params, serializeConfig);
        overrideValues(params, paramConfig);
    }

    private void overrideValues(MultiKeyMap<String, Object> params, ParamConfig paramConfig) {
        for (String name : paramConfig.getOverrideParams()) {
            overrideValue(name, params);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 100;
    }
}
