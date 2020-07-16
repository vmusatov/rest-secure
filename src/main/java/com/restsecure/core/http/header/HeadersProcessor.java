package com.restsecure.core.http.header;

import com.restsecure.core.mapping.serialize.SerializeConfig;
import com.restsecure.core.mapping.serialize.SerializeHelper;
import com.restsecure.core.processor.PreSendProcessor;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.ProcessScope;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.MultiKeyMap;

import static com.restsecure.core.http.OverrideValuesHelper.overrideValue;

@ProcessAll(preSendScope = ProcessScope.BEFORE_ALL)
public class HeadersProcessor implements PreSendProcessor {

    @Override
    public void preSendProcess(RequestContext context) {
        HeadersConfig headersConfig = context.getConfig(HeadersConfig.class);
        SerializeConfig serializeConfig = context.getConfig(SerializeConfig.class);
        MultiKeyMap<String, Object> headers = context.getSpecification().getHeaders();

        SerializeHelper.serializeValuesIfNeed(headers, serializeConfig);
        overrideValues(headers, headersConfig);
    }

    private void overrideValues(MultiKeyMap<String, Object> headers, HeadersConfig headersConfig) {
        for (String name : headersConfig.getOverrideHeaders()) {
            overrideValue(name, headers);
        }
    }
}