package com.restsecure.core.http.header;

import com.restsecure.core.configuration.configs.ObjectMapperConfig;
import com.restsecure.core.configuration.configs.OverrideHeadersConfig;
import com.restsecure.core.util.SerializeHelper;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.MultiKeyMap;

import java.util.List;

import static com.restsecure.core.http.OverrideValuesHelper.overrideValue;

@ProcessAll
public class HeadersProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        List<String> headersToOverride = context.getConfigValue(OverrideHeadersConfig.class);
        MultiKeyMap<String, Object> headers = context.getSpecification().getHeaders();

        context.getConfigValue(ObjectMapperConfig.class)
                .ifPresent(mapper -> SerializeHelper.serializeValuesIfNeed(headers, mapper));

        overrideValues(headers, headersToOverride);
    }

    private void overrideValues(MultiKeyMap<String, Object> headers, List<String> headersToOverride) {
        for (String name : headersToOverride) {
            overrideValue(name, headers);
        }
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 100;
    }
}
