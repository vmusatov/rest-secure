package com.restsecure.core.http.param;

import com.restsecure.core.mapping.serialize.SerializeConfig;
import com.restsecure.core.mapping.serialize.SerializeHelper;
import com.restsecure.core.processor.PreSendProcessor;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.ProcessScope;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.MultiKeyMap;

import static com.restsecure.core.http.OverrideValuesHelper.overrideValue;

@ProcessAll(preSendScope = ProcessScope.BEFORE_ALL)
public class ParamProcessor implements PreSendProcessor {

    @Override
    public void preSendProcess(RequestContext context) {
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
}
