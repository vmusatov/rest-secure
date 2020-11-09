package com.restsecure.core.http.header;

import com.restsecure.core.configuration.configs.OverrideHeadersConfig;
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
public class HeadersProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        List<String> headersToOverride = context.getConfigValue(OverrideHeadersConfig.class);
        Serializer serializer = context.getConfigValue(SerializerConfig.class);
        MultiKeyMap<String, Object> headers = context.getSpecification().getHeaders();

        SerializeHelper.serializeValuesIfNeed(headers, serializer);
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
