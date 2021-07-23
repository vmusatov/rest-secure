package com.restsecure.core.processor;

import com.restsecure.core.configuration.configs.ObjectMapperConfig;
import com.restsecure.core.configuration.configs.OverwriteAllParamsConfig;
import com.restsecure.core.configuration.configs.OverwriteParamsConfig;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.MultiKeyMap;
import com.restsecure.core.util.SerializeHelper;

import java.util.List;

import static com.restsecure.core.http.OverwriteValuesHelper.overwriteAllValues;
import static com.restsecure.core.http.OverwriteValuesHelper.overwriteValues;

@ProcessAll
public class ParamProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        MultiKeyMap<String, Object> params = context.getRequestSpec().getParams();

        context.getConfigValue(ObjectMapperConfig.class)
                .ifPresent(mapper -> SerializeHelper.serializeValuesIfNeed(params, mapper));

        Boolean needOverwriteAll = context.getConfigValue(OverwriteAllParamsConfig.class);
        if (needOverwriteAll) {
            overwriteAllValues(params);
        } else {
            List<String> paramsToOverwrite = context.getConfigValue(OverwriteParamsConfig.class);
            overwriteValues(params, paramsToOverwrite);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 100;
    }
}
