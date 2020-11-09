package com.restsecure.core.http.param;

import com.restsecure.core.configuration.configs.OverrideParamsConfig;
import com.restsecure.core.configuration.configs.SerializerConfig;
import com.restsecure.core.mapping.serialize.SerializeHelper;
import com.restsecure.core.mapping.serialize.Serializer;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.MultiKeyMap;

import java.util.List;

import static com.restsecure.core.http.OverrideValuesHelper.overrideValue;

@ProcessAll
public class ParamProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        List<String> paramsToOverride = context.getConfigValue(OverrideParamsConfig.class);
        Serializer serializer = context.getConfigValue(SerializerConfig.class);
        MultiKeyMap<String, Object> params = context.getSpecification().getParameters();

        SerializeHelper.serializeValuesIfNeed(params, serializer);
        overrideValues(params, paramsToOverride);
    }

    private void overrideValues(MultiKeyMap<String, Object> params, List<String> paramsToOverride) {
        for (String name : paramsToOverride) {
            overrideValue(name, params);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 100;
    }
}
