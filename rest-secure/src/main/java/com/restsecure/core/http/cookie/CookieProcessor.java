package com.restsecure.core.http.cookie;

import com.restsecure.core.configuration.configs.SerializerConfig;
import com.restsecure.core.mapping.serialize.SerializeHelper;
import com.restsecure.core.mapping.serialize.Serializer;
import com.restsecure.core.processor.ProcessAll;
import com.restsecure.core.processor.Processor;
import com.restsecure.core.request.RequestContext;
import com.restsecure.core.util.MultiKeyMap;

@ProcessAll
public class CookieProcessor implements Processor {

    @Override
    public void processRequest(RequestContext context) {
        Serializer serializer = context.getConfigValue(SerializerConfig.class);
        MultiKeyMap<String, Object> cookiesWithValueToSerialize = context.getSpecification().getCookiesWithValueToSerialize();
        MultiKeyMap<String, Object> headers = context.getSpecification().getHeaders();

        SerializeHelper.serializeValuesIfNeed(cookiesWithValueToSerialize, serializer);

        cookiesWithValueToSerialize.forEach(cookie -> {
            headers.put("Cookie", cookie.getKey() + "=" + cookie.getValue().toString());
        });
    }

    @Override
    public int getRequestProcessOrder() {
        return Processor.MAX_ORDER - 99;
    }
}
